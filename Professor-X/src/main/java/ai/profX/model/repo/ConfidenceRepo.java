package ai.profX.model.repo;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import ai.profX.model.Confidence;

public interface ConfidenceRepo extends MongoRepository<Confidence, ObjectId> {
	public List<Confidence> findByCharacterIdAndQuestionId(long charId,long questionId);
}
