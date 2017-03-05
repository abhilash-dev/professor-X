package ai.profX.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;

import ai.profX.model.Character;
import ai.profX.model.Question;
import ai.profX.service.CharacterService;
import ai.profX.service.Game;
import ai.profX.service.QuestionService;
import ai.profX.util.Util;

public class GameImpl implements Game {

	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private CharacterService characterService;

	@Override
	public List<Question> getInitialQuestions() {
		List<Question> initialQuestions = new ArrayList<>();
		initialQuestions.add(questionService.getQuestionById(1)); 

		Random random = new Random(System.nanoTime());
		long potentialQuestionId;
		Question question = null;
		int count = 0;
		while(count != 2) {
			potentialQuestionId = random.nextInt((int)questionService.size()) + 1;
			if(potentialQuestionId > 2){
				question = questionService.getQuestionById(potentialQuestionId);
				if(question!=null){
					initialQuestions.add(question);
					count++;
				}
			}
		}
		initialQuestions.add(questionService.getQuestionById(2));

		return initialQuestions;
	}

	@Override
	public LinkedHashMap<Long, Integer> initCharacterValues() {
		List<Character> characterList = characterService.getAllCharacters();
		LinkedHashMap<Long, Integer> characterValues = new LinkedHashMap<>();
		
		Iterator<Character> characterIterator = characterList.iterator();
		while(characterIterator.hasNext()){
			characterValues.put(characterIterator.next().getCharId(), 0);
		}
		return characterValues;
	}

	@Override
	public LinkedHashMap<Long, Integer> sortCharacterValues(LinkedHashMap<Long, Integer> characterValues) {
		LinkedHashMap<Long, Integer> sortedCharacterValues = new LinkedHashMap<>();
		sortedCharacterValues = Util.sortByValueDesc(characterValues);
		return sortedCharacterValues;
	}

	@Override
	public List<Character> getNearByCharacters(LinkedHashMap<Long, Integer> characterValues, int count) {
		LinkedHashMap<Long, Integer> sortedCharacterValues = sortCharacterValues(characterValues);
		if(count > sortedCharacterValues.size())
			count = sortedCharacterValues.size();
		
		List<Character> nearByCharacters = new ArrayList<>();
		Iterator<Long> charIdKeyIterator = sortedCharacterValues.keySet().iterator();
		for(int i=0;i<count;i++){
			Character character = characterService.getCharacterById(charIdKeyIterator.next());
			if(character!=null)
				nearByCharacters.add(character);
		}
		
		return nearByCharacters;
	}

	@Override
	public LinkedHashMap<Long, Integer> getNearbyCharacterValues(LinkedHashMap<Long, Integer> characterValues,
			int count) {
		LinkedHashMap<Long, Integer> sortedCharacterValues = sortCharacterValues(characterValues);
		if(count > sortedCharacterValues.size())
			count = sortedCharacterValues.size();
		
		LinkedHashMap<Long, Integer> nearByCharacterValues = new LinkedHashMap<>();
		Iterator<Long> charIdKeyIterator = sortedCharacterValues.keySet().iterator();
		for(int i=0;i<count;i++){
			nearByCharacterValues.put(charIdKeyIterator.next(), sortedCharacterValues.get(charIdKeyIterator.next()));
		}
		nearByCharacterValues = sortCharacterValues(nearByCharacterValues);
		return nearByCharacterValues;
	}

	@Override
	public Double getId3Entropy(List<Character> characterList, Question question) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String chooseQuestion(List<Question> initialQuestions, LinkedHashMap<Long, Integer> characterValues,
			List<Question> askedQuestions, int count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateLocalKnowledge(LinkedHashMap<Long, Integer> characterValues, List<Question> askedQuestions,
			long questionId, int answer) {
		// TODO Auto-generated method stub

	}

	@Override
	public Character guess(LinkedHashMap<Long, Integer> characterValues) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long learnNewCharacter(List<Question> askedQuestions, String text) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void learn(List<Question> askedQuestions, long charId) {
		// TODO Auto-generated method stub

	}

}
