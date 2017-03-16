package ai.profX.service.impl;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.profX.model.Character;
import ai.profX.model.Question;
import ai.profX.model.repo.ConfidenceRepo;
import ai.profX.model.repo.QuestionRepo;
import ai.profX.service.CharacterService;
import ai.profX.service.ConfidenceService;
import ai.profX.service.NextSequenceService;
import ai.profX.service.QuestionService;

@Service
public class QuestionServiceImpl implements QuestionService {

	@Autowired
	private QuestionRepo questionRepo;

	@Autowired
	private CharacterService characterService;

	@Autowired
	private ConfidenceService confidenceService;

	@Autowired
	private ConfidenceRepo confidenceRepo;
	
	@Autowired
	private NextSequenceService nextSequenceService;

	@Override
	public List<Question> getAllQuestions() {
		List<Question> questionList = questionRepo.findAll();
		if (questionList.size() > 0)
			return questionList;
		else
			return null;
	}

	@Override
	public Question getQuestionById(long questionId) {
		Question question = questionRepo.findByQuestionId(questionId);
		if (question != null)
			return question;
		else
			return null;
	}

	@Override
	public Question getQuestionByText(String text) {
		Question question = questionRepo.findByText(text);
		if (question != null)
			return question;
		else
			return null;
	}

	@Override
	public void removeQuestion(long questionId) {
		Question question = questionRepo.findByQuestionId(questionId);
		if (question != null) {
			questionRepo.delete(question);

			List<Character> characterList = characterService.getAllCharacters();
			Iterator<Character> characterIterator = characterList.iterator();

			while (characterIterator.hasNext()) {
				confidenceRepo.delete(confidenceRepo
						.findByCharacterIdAndQuestionId(characterIterator.next().getCharId(), questionId));
			}
		}
	}

	@Override
	public long addQuestion(String text) {
		Question question = new Question(text);
		question.setQuestionId(nextSequenceService.getNextSequence("question"));
		questionRepo.insert(question);

		long questionId = question.getQuestionId();
		List<Character> characterList = characterService.getAllCharacters();
		if(characterList!=null){
			Iterator<Character> characterIterator = characterList.iterator();

			while (characterIterator.hasNext()) {
			confidenceService.initConfidence(characterIterator.next().getCharId(), questionId);
			}
		}
		return questionId;
	}

	@Override
	public long getTotalQuestionCount() {
		return questionRepo.count();
	}

}
