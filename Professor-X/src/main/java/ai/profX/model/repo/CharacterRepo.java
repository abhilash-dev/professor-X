package ai.profX.model.repo;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.repository.MongoRepository;

import ai.profX.model.Character;

public interface CharacterRepo extends MongoRepository<Character, Long> {
	@Cacheable(value="characterCache")
	public Character findByCharId(long charId);
	@Cacheable(value="characterCache")
	public Character findByName(String name);
	@Cacheable("characterCache")
	public List<Character>findAll();
	
}
