package ai.profX.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import ai.profX.model.Character;
import ai.profX.model.Confidence;
import ai.profX.model.Question;
import ai.profX.model.repo.CharacterRepo;
import ai.profX.model.repo.ConfidenceRepo;
import ai.profX.service.CharacterService;
import ai.profX.service.ConfidenceService;
import ai.profX.service.NextSequenceService;
import ai.profX.service.QuestionService;

@Service
public class CharacterServiceImpl implements CharacterService {

	@Autowired
	private CharacterRepo characterRepo;

	@Autowired
	private ConfidenceRepo confidenceRepo;

	@Autowired
	private QuestionService questionService;

	@Autowired
	private ConfidenceService confidenceService;

	@Autowired
	private NextSequenceService nextSequenceService;
	
	@Override
	public long addNewCharacter(String name) {
		Character character = new Character(name);
		character.setCharId(nextSequenceService.getNextSequence("character"));
		characterRepo.insert(character);

		long charId = character.getCharId();
		List<Question> questionList = questionService.getAllQuestions();
		if(questionList!=null){
			Iterator<Question> questionIterator = questionList.iterator();

			while (questionIterator.hasNext()) {
				confidenceService.initConfidence(charId, questionIterator.next().getQuestionId());
			}
		}
		return charId;
	}

	@Override
	public List<Character> getAllCharacters() {
		List<Character> charList = new ArrayList<>();
		charList = characterRepo.findAll();
		if (charList.size() > 0)
			return charList;
		else
			return null;
	}

	@Override
	public Character getCharacterByName(String name) {
		Character character = characterRepo.findByName(name.trim().toLowerCase());
		if (character != null)
			return character;
		else
			return null;
	}

	@Override
	public Character getCharacterById(long charId) {
		Character character = characterRepo.findByCharId(charId);
		if (character != null)
			return character;
		else
			return null;
	}

	@Override
	public int getNoOfUnknownCharactersForQuestionId(List<Character> characters, long questionId) {
		int value = 0;
		int count = 0;

		Iterator<Character> it = characters.iterator();
		Confidence confidence = null;
		while (it.hasNext()) {
			confidence = confidenceRepo.findByCharacterIdAndQuestionIdAndValue(it.next().getCharId(), questionId,
					value);
			if (confidence != null)
				count++;
		}
		return count;
	}

	@Override
	public int getNoOfCharactersWithPositiveConfidenceForQuestionId(List<Character> characters, long questionId) {
		int count = 0;
		Iterator<Character> it = characters.iterator();
		Confidence confidence;

		while (it.hasNext()) {
			Character character = it.next();
			confidence = confidenceRepo.findByCharacterIdAndQuestionId(character.getCharId(), questionId);
			if (confidence.getValue() > 0) {
				count++;
			}
		}
		return count;
	}

	@Override
	public int getNoOfCharactersWithNegativeConfidenceForQuestionId(List<Character> characters, long questionId) {
		int count = 0;
		Iterator<Character> it = characters.iterator();
		Confidence confidence;

		while (it.hasNext()) {
			Character character = it.next();
			confidence = confidenceRepo.findByCharacterIdAndQuestionId(character.getCharId(), questionId);
			if (confidence.getValue() < 0) {
				count++;
			}
		}
		return count;
	}

	@Override
	public void removeCharacter(long charId) {
		Character character = characterRepo.findByCharId(charId);
		if (character != null) {
			characterRepo.delete(character);

			List<Question> questionList = questionService.getAllQuestions();
			Iterator<Question> questionIterator = questionList.iterator();

			while (questionIterator.hasNext()) {
				confidenceRepo.delete(
						confidenceRepo.findByCharacterIdAndQuestionId(charId, questionIterator.next().getQuestionId()));
			}
		}
	}

	@Override
	public void updateNoOfTimesPlayed(long charId) {
		Character character = characterRepo.findByCharId(charId);
		if (character != null) {
			character.setNoOfTimesPlayed(character.getNoOfTimesPlayed() + 1);
		}
	}

	@Override
	public long getTotalCharacterCount() {
		return characterRepo.count();
	}
}
