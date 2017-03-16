package ai.profX.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Table;

import ai.profX.model.Character;
import ai.profX.model.Confidence;
import ai.profX.model.Question;
import ai.profX.service.CharacterService;
import ai.profX.service.ConfidenceService;
import ai.profX.service.QuestionService;

@RestController
public class AdminController {
	
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private CharacterService characterService;
	
	@Autowired
	private ConfidenceService confidenceService;
	
	@RequestMapping(method = RequestMethod.GET, value = "/admin")
	public String admin() {
		// display admin page here
		return null;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/dq")
	public @ResponseBody List<Question> getQuestionsToDelete() {
		List<Question> questionList = questionService.getAllQuestions();
		return questionList;
		// Display this list along with check boxes for the admin to select the questions to be deleted
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/dq")
	public @ResponseBody void deleteQuestions(@RequestParam List<Long> questionIdList) {
		if(questionIdList!=null && !questionIdList.isEmpty()){
			Iterator<Long> questionIdIterator = questionIdList.iterator();
			Question question = null;
			while(questionIdIterator.hasNext()){
				question = questionService.getQuestionById(questionIdIterator.next());
				if(question!=null){
					questionService.removeQuestion(questionIdIterator.next());
				}
			}
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/dc")
	public @ResponseBody List<Character> getCharactersToDelete() {
		List<Character> characterList = characterService.getAllCharacters();
		return characterList;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/dc")
	public @ResponseBody void deleteCharacters(@RequestParam List<Long> characterIdList) {
		if(characterIdList!=null && !characterIdList.isEmpty()){
			Iterator<Long> characterIdIterator = characterIdList.iterator();
			
			while(characterIdIterator.hasNext()){
				Character character = characterService.getCharacterById(characterIdIterator.next());
				if(character!=null){
					characterService.removeCharacter(characterIdIterator.next());
				}
			}
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/data")
	public @ResponseBody List<Character> getAllObjectsToTrain() {
		List<Character> characterList = characterService.getAllCharacters();
		return characterList;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/retrain/{characterId}")
	public @ResponseBody Map<String, Object> getCharacterToTrain(@PathVariable ("characterId") long characterId) {
		Map<String,Object> responseMap = new HashMap<>();
		Character character = characterService.getCharacterById(characterId);
		if(character!=null){
			responseMap.put("character", character);
			
			List<Question> questionList = questionService.getAllQuestions();
			responseMap.put("questionList", questionList);
			
			List<Confidence> confidenceList = confidenceService.getConfidenceByCharacterId(characterId);
			responseMap.put("confidenceList",confidenceList);
		}
		
		return responseMap;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/retrain")
	public @ResponseBody String postLearnInfo(@RequestParam ("retrainData") Map<String, Object> retrainMap) {
		//TODO complete this method
		
	}
}
