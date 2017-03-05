package ai.profX.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import ai.profX.common.Constants;
import ai.profX.model.Confidence;
import ai.profX.model.Question;
import ai.profX.model.Character;
import ai.profX.model.repo.ConfidenceRepo;
import ai.profX.service.CharacterService;
import ai.profX.service.ConfidenceService;
import ai.profX.service.QuestionService;

public class ConfidenceServiceImpl implements ConfidenceService {

	@Autowired
	private ConfidenceRepo confidenceRepo;

	@Autowired
	private CharacterService characterService;

	@Autowired
	private QuestionService questionService;

	@Override
	public void initConfidence(long charId, long questionId) {
		Confidence confidence = new Confidence(charId, questionId);
		confidenceRepo.save(confidence);
	}

	@Override
	public void updateConfidence(long charId, long questionId, int value) {
		Confidence confidence = confidenceRepo.findByCharacterIdAndQuestionId(charId, questionId);
		if (confidence != null) {
			confidence.setValue(value);
		} else {
			Confidence newConfidence = new Confidence(charId, questionId, value);
			confidenceRepo.save(newConfidence);
		}
	}

	@Override
	public List<Confidence> getAllConfidenceObjects() {
		List<Confidence> confidenceObjectList = new ArrayList<>();
		confidenceObjectList = confidenceRepo.findAll();
		if (confidenceObjectList.size() > 0) {
			return confidenceObjectList;
		} else {
			return null;
		}
	}

	@Override
	public int getConfidenceValue(long charId, long questionId) {
		int confidenceValue = Constants.NO_CONFIDENCE_VALUE;
		Confidence confidence = confidenceRepo.findByCharacterIdAndQuestionId(charId, questionId);
		if (confidence != null) {
			confidenceValue = confidence.getValue();
		}
		return confidenceValue;
	}

	@Override
	public List<Confidence> getConfidenceByQuestionId(long questionId) {
		List<Confidence> confidenceObjectList = new ArrayList<>();
		List<Character> allCharacterList = characterService.getAllCharacters();

		Iterator<Character> it = allCharacterList.iterator();
		Confidence confidence;
		while (it.hasNext()) {
			confidence = confidenceRepo.findByCharacterIdAndQuestionId(it.next().getCharId(), questionId);
			if (confidence != null) {
				confidenceObjectList.add(confidence);
			}
		}

		if (confidenceObjectList.size() > 0) {
			return confidenceObjectList;
		} else {
			return null;
		}
	}

	@Override
	public List<Confidence> getConfidenceByCharacterId(long charId) {
		List<Question> questionList = questionService.getAllQuestions();
		List<Confidence> confidenceObjectList = new ArrayList<>();

		Iterator<Question> it = questionList.iterator();
		Confidence confidence;
		while (it.hasNext()) {
			confidence = confidenceRepo.findByCharacterIdAndQuestionId(charId, it.next().getQuestionId());
			if (confidence != null) {
				confidenceObjectList.add(confidence);
			}
		}

		if (confidenceObjectList.size() > 0) {
			return confidenceObjectList;
		} else {
			return null;
		}
	}

	@Override
	public Table<Long, Long, Integer> getAllConfidenceValues() {
		Table<Long, Long, Integer> table = HashBasedTable.create();

		List<Character> allCharacterList = characterService.getAllCharacters();
		List<Question> questionList = questionService.getAllQuestions();

		Iterator<Character> characterIterator = allCharacterList.iterator();
		Iterator<Question> questionIterator = questionList.iterator();

		Confidence confidence;
		long charId;
		long questionId;

		while (characterIterator.hasNext()) {
			charId = characterIterator.next().getCharId();
			while (questionIterator.hasNext()) {
				questionId = questionIterator.next().getQuestionId();
				confidence = confidenceRepo.findByCharacterIdAndQuestionId(charId, questionId);
				if (confidence != null)
					table.put(charId, questionId, confidence.getValue());
			}
		}

		return table;
	}
}
