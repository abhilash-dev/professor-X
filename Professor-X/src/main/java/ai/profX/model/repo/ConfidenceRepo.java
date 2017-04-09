package ai.profX.model.repo;

import org.bson.types.ObjectId;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.repository.MongoRepository;

import ai.profX.model.Confidence;

public interface ConfidenceRepo extends MongoRepository<Confidence, ObjectId> {
	@Cacheable(value="confidenceCache")
	public Confidence findByCharacterIdAndQuestionId(long charId, long questionId);
	@Cacheable(value = "confidenceCache")
	public Confidence findByCharacterIdAndQuestionIdAndValue(long charId, long questionId, int value);
}
