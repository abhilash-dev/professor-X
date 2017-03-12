package ai.profX.model.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import ai.profX.model.CustomSequences;

public interface CustomSequencesRepo extends MongoRepository<CustomSequences, String> {

}
