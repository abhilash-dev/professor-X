package ai.profX.model.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import ai.profX.model.Character;

public interface CharacterRepo extends MongoRepository<Character, Long> {
	public Character findByCharId(long charId);
	public Character findByName(String name);

}
