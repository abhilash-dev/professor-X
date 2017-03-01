package ai.profX.service;

import java.util.HashMap;

public interface GameLogService {
	public void addGameLog(long charId, HashMap<Integer, Integer> log,Boolean result);
}
