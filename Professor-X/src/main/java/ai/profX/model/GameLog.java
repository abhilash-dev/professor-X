package ai.profX.model;

import java.util.HashMap;

import org.springframework.data.annotation.Id;

public class GameLog {
	@Id
	private int characterId;
	private HashMap<Integer,Integer> log;
	private int result;
	
	public GameLog(int characterId, HashMap<Integer, Integer> log, int result) {
		this.characterId = characterId;
		this.log = log;
		this.result = result;
	}

	public int getCharacterId() {
		return characterId;
	}

	public void setCharacterId(int characterId) {
		this.characterId = characterId;
	}

	public HashMap<Integer, Integer> getLog() {
		return log;
	}

	public void setLog(HashMap<Integer, Integer> log) {
		this.log = log;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}
	
	
}
