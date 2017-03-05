package ai.profX.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ai.profX.model.Question;
import ai.profX.model.repo.QuestionRepo;
import ai.profX.service.QuestionService;

public class QuestionServiceImpl implements QuestionService {
	
	@Autowired
	private QuestionRepo questionRepo;

	@Override
	public List<Question> getAllQuestions() {
		List<Question> questionList = questionRepo.findAll();
		if(questionList.size() > 0)
			return questionList;
		else
			return null;
	}

	@Override
	public Question getQuestionById(long questionId) {
		Question question = questionRepo.findByQuestionId(questionId);
		if(question!=null)
			return question;
		else
			return null;
	}

	@Override
	public Question getQuestionByText(String text) {
		Question question = questionRepo.findByText(text);
		if(question!=null)
			return question;
		else
			return null;
	}

	@Override
	public void removeQuestion(long questionId) {
		Question question = questionRepo.findByQuestionId(questionId);
		if(question!=null)
			questionRepo.delete(question);
		/*
		 * to implement further functionality
		 * 1. fetch all characters
		 * 2. remove associated confidence from all characters for this question
		 */
	}

	@Override
	public long addQuestion(String text) {
		Question question = new Question(text);
		questionRepo.save(question);
		/*
		 * to implement further functionality
		 * 1. fetch all characters
		 * 2. update or init confidence for this new question with all characters
		 */
		return question.getQuestionId();
	}

}
