package ai.profX.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.math.DoubleMath;

import ai.profX.common.Constants;
import ai.profX.model.Character;
import ai.profX.model.Confidence;
import ai.profX.model.Question;
import ai.profX.service.CharacterService;
import ai.profX.service.ConfidenceService;
import ai.profX.service.Game;
import ai.profX.service.GameLogService;
import ai.profX.service.QuestionService;
import ai.profX.util.Util;

@Service
public class GameImpl implements Game {

	@Autowired
	private QuestionService questionService;

	@Autowired
	private CharacterService characterService;
	
	@Autowired
	private ConfidenceService confidenceService;
	
	@Autowired
	private GameLogService gameLogService;

	@Override
	public List<Question> getInitialQuestions(List<Question> initialQuestions) {
		Question question1 = questionService.getQuestionById(1);
		initialQuestions.add(question1);

		Random random = new Random(System.nanoTime());
		long potentialQuestionId;
		Question question = null;
		int count = 0;
		while (count != 2) {
			potentialQuestionId = random.nextInt((int) questionService.getTotalQuestionCount()) + 1;
			if (potentialQuestionId > 2) {
				question = questionService.getQuestionById(potentialQuestionId);
				if (question != null) {
					initialQuestions.add(question);
					count++;
				}
			}
		}
		initialQuestions.add(questionService.getQuestionById(2));

		return initialQuestions;
	}

	@Override
	public LinkedHashMap<Long, Integer> initCharacterValues(LinkedHashMap<Long, Integer> characterValues) {
		List<Character> characterList = characterService.getAllCharacters();

		Iterator<Character> characterIterator = characterList.iterator();
		while (characterIterator.hasNext()) {
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
		if (count > sortedCharacterValues.size())
			count = sortedCharacterValues.size();

		List<Character> nearByCharacters = new ArrayList<>();
		Iterator<Long> charIdKeyIterator = sortedCharacterValues.keySet().iterator();
		for (int i = 0; i < count; i++) {
			Character character = characterService.getCharacterById(charIdKeyIterator.next());
			if (character != null)
				nearByCharacters.add(character);
		}

		return nearByCharacters;
	}

	@Override
	public LinkedHashMap<Long, Integer> getNearbyCharacterValues(LinkedHashMap<Long, Integer> characterValues,
			int count) {
		LinkedHashMap<Long, Integer> sortedCharacterValues = sortCharacterValues(characterValues);
		if (count > sortedCharacterValues.size())
			count = sortedCharacterValues.size();

		LinkedHashMap<Long, Integer> nearByCharacterValues = new LinkedHashMap<>();
		Iterator<Long> charIdKeyIterator = sortedCharacterValues.keySet().iterator();
		for (int i = 0; i < count; i++) {
			nearByCharacterValues.put(charIdKeyIterator.next(), sortedCharacterValues.get(charIdKeyIterator.next()));
		}
		nearByCharacterValues = sortCharacterValues(nearByCharacterValues);
		return nearByCharacterValues;
	}

	public long getDummyEntropy(List<Character> characterList, Question question) {
		long positives = characterService.getNoOfCharactersWithPositiveConfidenceForQuestionId(characterList,
				question.getQuestionId());
		long negatives = characterService.getNoOfCharactersWithNegativeConfidenceForQuestionId(characterList,
				question.getQuestionId());
		long unknowns = characterService.getNoOfUnknownCharactersForQuestionId(characterList, question.getQuestionId());

		long questionEntropy = 0;

		questionEntropy += positives * 1;
		questionEntropy -= negatives * 1;
		questionEntropy += unknowns * 5;

		return Math.abs(questionEntropy);
	}

	@Override
	public Double getId3Entropy(List<Character> characterList, Question question) {
		long positives = characterService.getNoOfCharactersWithPositiveConfidenceForQuestionId(characterList,
				question.getQuestionId());
		long negatives = characterService.getNoOfCharactersWithNegativeConfidenceForQuestionId(characterList,
				question.getQuestionId());
		long total = characterList.size();

		Double positiveFraction = 0.0, negativeFraction = 0.0, entropy = 0.0;

		if (positives != 0)
			positiveFraction = (-1 * positives) / total * DoubleMath.log2(positives / total);
		if (negatives != 0)
			negativeFraction = (-1 * negatives) / total * DoubleMath.log2(negatives / total);

		entropy = positiveFraction + negativeFraction;
		entropy *= (positives + negatives) / total;

		if (entropy != 0.0)
			entropy = 1 / entropy;
		else
			entropy = Double.POSITIVE_INFINITY;

		return entropy;
	}

	@Override
	public Question chooseQuestion(List<Question> initialQuestions, LinkedHashMap<Long, Integer> characterValues,
			HashMap<Long, Integer> askedQuestions, int count) {
		Question chosenQuestion = null;

		if (initialQuestions.size() > 0) {
			chosenQuestion = initialQuestions.get(initialQuestions.size()-1);
			initialQuestions.remove(chosenQuestion);
		} else {
			LinkedHashMap<Long, Integer> sortedCharacterValues = sortCharacterValues(characterValues);
			if (count > sortedCharacterValues.size()){
				count = sortedCharacterValues.size();
			}
			List<Character> characterList = getObjectsBasedOnCountInSortedOrder(sortedCharacterValues, count);
			List<Question> allQuestionsList = questionService.getAllQuestions();
			
			double bestQuestionEntropy = Double.POSITIVE_INFINITY;
			Question bestQuestion = null;
			
			for(Question question:allQuestionsList){
				if(!Util.isQuestionIdPresent(question.getQuestionId(),askedQuestions)){
					double questionEntropy = getId3Entropy(characterList, question);
					if(questionEntropy<=bestQuestionEntropy){
						bestQuestionEntropy = questionEntropy;
						bestQuestion = question;
					}
				}
			}
			chosenQuestion = bestQuestion;
		}

		return chosenQuestion;
	}
	
	public List<Character> getObjectsBasedOnCountInSortedOrder(LinkedHashMap<Long, Integer> sortedCharacterValues,int count){
		List<Character> characterList = new ArrayList<>();
		Iterator<Long> chracterIdIterator = sortedCharacterValues.keySet().iterator();
		while(chracterIdIterator.hasNext()){
			Character character = characterService.getCharacterById(chracterIdIterator.next());
			if(character!=null)
				characterList.add(character);
		}
		return characterList;
	}

	@Override
	public void updateLocalKnowledge(LinkedHashMap<Long, Integer> characterValues, HashMap<Long, Integer> askedQuestions,
			long questionId, int answer) {
		// This method is the key to handle robustness of the system against intentional wrong answers, fine tune this method to
		// play with the robustness of the system.
		
		if(!Util.isAnswerValid(answer)){
			try {
				throw new Exception("Answer should be either an YES, NO or DON'T KNOW!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			List<Confidence> weights = confidenceService.getConfidenceByQuestionId(questionId);
			int localValue,confidenceValue;
			long key;
			
			for(Confidence weight:weights){
				key = weight.getCharacterId();
				confidenceValue = weight.getValue();
				if(Util.isCharacterIdPresent(key,characterValues)){
					if(confidenceValue > Constants.POS_WEIGHT_CUTOFF)
						localValue = Constants.POS_WEIGHT_CUTOFF;
					else if(confidenceValue < Constants.NEG_WEIGHT_CUTOFF)
						localValue = Constants.NEG_WEIGHT_CUTOFF;
					else if(confidenceValue < Constants.I_DONT_KNOW)
						localValue = confidenceValue/2;
					else
						localValue = confidenceValue;
					
					if((answer == Constants.NO && localValue > 0) || (answer == Constants.YES && localValue < 0))
						localValue *= 5;
					
					
					characterValues.replace(key, characterValues.get(key), localValue);
				}
			}
			askedQuestions.put(questionId, answer);
		}

	}

	@Override
	public Character guess(LinkedHashMap<Long, Integer> characterValues) {
		Character character = getNearByCharacters(characterValues, 1).get(0);
		return character;
	}

	@Override
	public long learnCharacter(HashMap<Long, Integer> askedQuestions, String text) {
		Character character;
		if(text.trim()!=""){
			character = characterService.getCharacterByName(text.trim().toLowerCase());
			if(character!=null){
				long characterId = character.getCharId();
				learn(askedQuestions, characterId);
				
				return characterId;
			
			}else{
				long newCharacterId = characterService.addNewCharacter(text);
				learn(askedQuestions, newCharacterId);
				
				return newCharacterId;
			}
		}
		return 0;
	}

	@Override
	public void learn(HashMap<Long, Integer> askedQuestions, long charId) {
		Iterator<Long> questionIdIterator = askedQuestions.keySet().iterator();
		while(questionIdIterator.hasNext()){
			long questionId = questionIdIterator.next();
			int currentConfidenceValue = confidenceService.getConfidenceValue(charId, questionId);
			if(currentConfidenceValue==Constants.NO_CONFIDENCE_VALUE){
				currentConfidenceValue = 0;
			}
			
			int newConfidenceValue = currentConfidenceValue + askedQuestions.get(questionId);
			confidenceService.updateConfidence(charId, questionId, newConfidenceValue);
		}
		
		characterService.updateNoOfTimesPlayed(charId);
		gameLogService.addGameLog(charId, askedQuestions, true); //TODO to somehow get the answer in this method and update the same

	}

}
