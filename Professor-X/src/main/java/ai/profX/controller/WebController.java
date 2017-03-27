package ai.profX.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import ai.profX.common.Constants;
import ai.profX.model.Character;
import ai.profX.model.Question;
import ai.profX.service.ConfidenceService;
import ai.profX.service.Game;
import ai.profX.service.QuestionService;
import ai.profX.util.Util;

@CrossOrigin
@RestController
@RequestMapping("/game/*")
public class WebController {
	
	@Autowired
	private Game game;
	
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private ConfidenceService confidenceService;
	
	Gson gson = new Gson();
	Boolean finalAnswer = false;
	
	@RequestMapping(method = RequestMethod.GET, value = "/init")
	public @ResponseBody void initSession(HttpServletRequest request){
		List<Question> initialQuestions = new ArrayList<>();
		HashMap<Long, Integer> askedQuestions = new HashMap<>();
		LinkedHashMap<Long, Integer> characterValues = new LinkedHashMap<>();
		int questionCount = 1;
		finalAnswer = false;
		
		HttpSession session = request.getSession(true);
		session.setAttribute("questionCount", questionCount);
		session.setAttribute("initialQuestions", initialQuestions);
		session.setAttribute("askedQuestions", askedQuestions);
		session.setAttribute("characterValues", characterValues);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/question")
	public @ResponseBody String showIndex(HttpServletRequest request){
		Map<String,Object> responseMap = new HashMap<>();
		String response = null;
		// This method returns the question and the count
		HttpSession session = request.getSession();
		
		List<Question> initialQuestions = (List<Question>) session.getAttribute("initialQuestions");
		LinkedHashMap<Long, Integer> characterValues = (LinkedHashMap<Long, Integer>) session.getAttribute("characterValues");
		HashMap<Long, Integer> askedQuestions = (HashMap<Long, Integer>) session.getAttribute("askedQuestions");
		int questionCount = (int) session.getAttribute("questionCount");
		
		if(initialQuestions.isEmpty() && characterValues.isEmpty()){
			initialQuestions = game.getInitialQuestions(initialQuestions);
			characterValues = game.initCharacterValues(characterValues);
		}
		
		Question question = game.chooseQuestion(initialQuestions, characterValues, askedQuestions, 10);
		
		//TODO the if portion is to be handled from the front-end
		if(question==null || questionCount > 20){
			return response; //TODO to redirect it to Guess be handled from the front-end
		}else{
			responseMap.put("question", question);
			responseMap.put("questionCount", questionCount);
			response = gson.toJson(responseMap);
		}
		
		return response;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/answer/{questionId}/{answer}")
	public @ResponseBody void answer(@PathVariable("questionId") long questionId,@PathVariable("answer") int answer,HttpServletRequest request){
		HttpSession session = request.getSession();
		
		LinkedHashMap<Long, Integer> characterValues = (LinkedHashMap<Long, Integer>) session.getAttribute("characterValues");
		HashMap<Long, Integer> askedQuestions = (HashMap<Long, Integer>) session.getAttribute("askedQuestions");
		int questionCount = (int) session.getAttribute("questionCount");
		
		if(!Util.isAnswerValid(answer)){
			answer = Constants.I_DONT_KNOW;
		}
		
		if(answer != Constants.I_DONT_KNOW){
			questionCount++;
			session.setAttribute("questionCount", questionCount);
		}
		
		game.updateLocalKnowledge(characterValues, askedQuestions, questionId, answer);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/guess")
	public @ResponseBody String getGuess(HttpServletRequest request){
		HttpSession session = request.getSession();
		LinkedHashMap<Long, Integer> characterValues = (LinkedHashMap<Long, Integer>) session.getAttribute("characterValues");
		
		Character character = game.guess(characterValues);
		return character.getName();
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/guess")
	public @ResponseBody void postGuess(@RequestParam(value = "characterId", required = true) long characterId,@RequestParam(value = "finalAnswer", required = true) Boolean finalAnswer,HttpServletRequest request){
		HttpSession session = request.getSession();
		HashMap<Long, Integer> askedQuestions = (HashMap<Long, Integer>) session.getAttribute("askedQuestions");
		this.finalAnswer = finalAnswer;
		//If we have guessed the character correctly, update the confidences for the chosen character, reset the game and redirect to landing page
		//TODO the redirection in both if and else must be handled by front-end
		if(finalAnswer){
			game.learn(askedQuestions, characterId,finalAnswer);
			resetGame(request);
			//TODO redirect to index page to be handled by front-end
		}else{
			//If we guessed it wrong, then redirect it to /learn ( GET )
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/learn")
	public @ResponseBody String getLearnInfo(HttpServletRequest request){
		HttpSession session = request.getSession();
		LinkedHashMap<Long, Integer> characterValues = (LinkedHashMap<Long, Integer>) session.getAttribute("characterValues");
		
		List<Character> nearByCharacters = game.getNearByCharacters(characterValues, 20);
		
		String response = gson.toJson(nearByCharacters);
		return response;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/learn")
	public @ResponseBody void postLearnInfo(HttpServletRequest request,
			@RequestParam(value = "newCharacter", required = true)Boolean newCharacter,
			@RequestParam(value = "characterName", required = true)String characterName,
			@RequestParam(value = "userQuestionText", required = false)String userQuestionText,
			@RequestParam(value = "userQuestionAnswer", required = false)int userQuestionAnswer){
		HttpSession session = request.getSession();
		HashMap<Long, Integer> askedQuestions = (HashMap<Long, Integer>) session.getAttribute("askedQuestions");
		int answer = Constants.I_DONT_KNOW;
		long newQuestionId = Constants.NON_EXISTENT_VALUE,newCharacterId = Constants.NON_EXISTENT_VALUE;
		Question question;
		
		if(!userQuestionText.trim().isEmpty()){
			if(Util.isAnswerValid(userQuestionAnswer)){
				answer = userQuestionAnswer * Constants.NEW_QUESTION_SCALE;
			}else{
				answer = Constants.I_DONT_KNOW;
			}
		}
		
		if(!userQuestionText.trim().isEmpty() && questionService.getQuestionByText(userQuestionText.trim()) == null){
			newQuestionId = questionService.addQuestion(userQuestionText.trim());
		}else{
			question = questionService.getQuestionByText(userQuestionText.trim());
			newQuestionId = question.getQuestionId();
		}
		
		if(!characterName.trim().isEmpty()){
			newCharacterId = game.learnCharacter(askedQuestions, characterName.trim(),finalAnswer);
		}
		
		if(newQuestionId!=Constants.NON_EXISTENT_VALUE && newCharacterId!=Constants.NON_EXISTENT_VALUE){
			confidenceService.updateConfidence(newCharacterId, newQuestionId, answer);
		}
		
		resetGame(request);
		//TODO redirect to index to be handled by front-end
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/restart")
	public @ResponseBody void restart(HttpServletRequest request){
		resetGame(request);
		//TODO redirect back to index to be handled by front-end
	}
	
	public void resetGame(HttpServletRequest request){
		HttpSession session = request.getSession();
		session.invalidate();
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/predict")
	public @ResponseBody String predict(HttpServletRequest request){
		HttpSession session = request.getSession();
		LinkedHashMap<Long, Integer> characterValues = (LinkedHashMap<Long, Integer>) session.getAttribute("characterValues");
		LinkedHashMap<Long, Integer> responseMap = game.getNearbyCharacterValues(characterValues, 10); 
		return gson.toJson(responseMap);
	}
}
