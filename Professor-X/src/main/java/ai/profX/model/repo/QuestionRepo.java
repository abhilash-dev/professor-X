package ai.profX.model.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import ai.profX.model.Question;

public interface QuestionRepo extends MongoRepository<Question, Long> {
	public List<Question> findByQuestionId(long questionId);
	public List<Question> findByText(String text);
}
