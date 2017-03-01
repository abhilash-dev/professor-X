package ai.profX.model.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import ai.profX.model.GameLog;

public interface GameLogRepo extends MongoRepository<GameLog, Long> {
	public List<GameLog> findByCharacterId(long charId);
	public List<GameLog> findByCharacterIdAndResult(long charId,Boolean result);
}
