package ai.profX.model.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import ai.profX.model.Character;

public interface CharacterRepo extends MongoRepository<Character, Long> {
	public List<Character> findByCharId(long charId);
	public List<Character> findByName(String name);

}
