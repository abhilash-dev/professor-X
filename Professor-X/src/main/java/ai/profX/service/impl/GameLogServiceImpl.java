package ai.profX.service.impl;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;

import ai.profX.model.GameLog;
import ai.profX.model.repo.GameLogRepo;
import ai.profX.service.GameLogService;

public class GameLogServiceImpl implements GameLogService {
	
	@Autowired
	private GameLogRepo gameLogRepo;

	@Override
	public void addGameLog(long charId, HashMap<Integer, Integer> log, Boolean result) {
		GameLog gameLog = new GameLog(charId, log, result);
		gameLogRepo.save(gameLog);
	}

}
