package ai.profX.model.repo;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.repository.MongoRepository;

import ai.profX.model.Question;

public interface QuestionRepo extends MongoRepository<Question, Long> {
	@Cacheable(value="questionCache")
	public Question findByQuestionId(long questionId);
	public Question findByText(String text);
	@Cacheable("questionCache")
	public List<Question> findAll();
}
