package ai.profX.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ai.profX.common.Constants;
import ai.profX.model.Character;
import ai.profX.model.Question;
import ai.profX.service.ConfidenceService;
import ai.profX.service.Game;
import ai.profX.service.QuestionService;
import ai.profX.util.Util;

@RestController
public class WebController {
	//TODO : To implement respective services for the requests here
	
	@Autowired
	private Game game;
	
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private ConfidenceService confidenceService;
	
	public static List<Question> initialQuestions;
	public static HashMap<Long, Integer> askedQuestions;
	public static LinkedHashMap<Long, Integer> characterValues;
	public static int questionCount;
	
	
	@RequestMapping(method = RequestMethod.GET, value = "/init")
	public @ResponseBody void initSession(HttpServletRequest request){
		initialQuestions = new ArrayList<>();
		askedQuestions = new HashMap<>();
		characterValues = new LinkedHashMap<>();
		questionCount = 1;
		
		HttpSession session = request.getSession(true);
		session.setAttribute("questionCount", questionCount);
		session.setAttribute("initialQuestions", initialQuestions);
		session.setAttribute("askedQuestions", askedQuestions);
		session.setAttribute("characterValues", characterValues);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/")
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
		
		if(question==null || questionCount > 20){
			return response; //TODO to redirect it to Guess
		}else{
			responseMap.put("question", question);
			responseMap.put("questionCount", questionCount);
			try {
				response = new ObjectMapper().writeValueAsString(responseMap);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			return response;
		}
		
		//TODO when this method is hit
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
		//TODO redirect to index
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/guess")
	public @ResponseBody String getGuess(HttpServletRequest request){
		HttpSession session = request.getSession();
		LinkedHashMap<Long, Integer> characterValues = (LinkedHashMap<Long, Integer>) session.getAttribute("characterValues");
		
		Character character = game.guess(characterValues);
		return character.getName();
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/guess")
	public @ResponseBody String postGuess(@RequestParam(value = "characterId", required = true) long characterId,HttpServletRequest request){
		HttpSession session = request.getSession();
		game.learn(askedQuestions, characterId);
		
		resetGame(request);
		//TODO redirect to index page
		return null;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/learn")
	public @ResponseBody String getLearnInfo(HttpServletRequest request){
		HttpSession session = request.getSession();
		LinkedHashMap<Long, Integer> characterValues = (LinkedHashMap<Long, Integer>) session.getAttribute("characterValues");
		
		List<Character> nearByCharacters = game.getNearByCharacters(characterValues, 20);
		//TODO to convert this into proper json
		return null;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/learn")
	public @ResponseBody String postLearnInfo(HttpServletRequest request,
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
			newCharacterId = game.learnCharacter(askedQuestions, characterName.trim());
		}
		
		if(newQuestionId!=Constants.NON_EXISTENT_VALUE && newCharacterId!=Constants.NON_EXISTENT_VALUE){
			confidenceService.updateConfidence(newCharacterId, newQuestionId, answer);
		}
		
		resetGame(request);
		
		//TODO redirect to index
		return null;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/restart")
	public @ResponseBody void restart(HttpServletRequest request){
		resetGame(request);
		//TODO reditect back to index
	}
	
	public void resetGame(HttpServletRequest request){
		//TODO to kill and reset the seesion here
		HttpSession session = request.getSession();
		session.invalidate();
	}
	
	
}
