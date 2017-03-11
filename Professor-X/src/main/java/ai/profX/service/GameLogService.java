package ai.profX.service;

import java.util.HashMap;

public interface GameLogService {
	public void addGameLog(long charId, HashMap<Long, Integer> log,Boolean result);
}
