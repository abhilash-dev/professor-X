package ai.profX.service;

import java.util.List;

import ai.profX.model.Question;

public interface QuestionService {
	public long addQuestion(String text);
	public List<Question> getAllQuestions();
	public Question getQuestionById(long questionId);
	public Question getQuestionByText(String text);
	public void removeQuestion(long questionId);
}
