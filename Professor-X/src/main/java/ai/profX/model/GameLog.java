package ai.profX.model;

import java.util.HashMap;

import org.springframework.data.annotation.Id;

public class GameLog {
	@Id
	private long characterId;
	private HashMap<Long,Integer> log;
	private Boolean result;
	
	public GameLog(){}
	
	public GameLog(long characterId, HashMap<Long, Integer> log, Boolean result) {
		this.characterId = characterId;
		this.log = log;
		this.result = result;
	}

	public long getCharacterId() {
		return characterId;
	}

	public void setCharacterId(long characterId) {
		this.characterId = characterId;
	}

	public HashMap<Long, Integer> getLog() {
		return log;
	}

	public void setLog(HashMap<Long, Integer> log) {
		this.log = log;
	}

	public Boolean getResult() {
		return result;
	}

	public void setResult(Boolean result) {
		this.result = result;
	}
}
