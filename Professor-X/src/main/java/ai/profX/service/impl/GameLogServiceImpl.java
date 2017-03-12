package ai.profX.service.impl;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.profX.model.GameLog;
import ai.profX.model.repo.GameLogRepo;
import ai.profX.service.GameLogService;

@Service
public class GameLogServiceImpl implements GameLogService {

	@Autowired
	private GameLogRepo gameLogRepo;

	@Override
	public void addGameLog(long charId, HashMap<Long, Integer> log, Boolean result) {
		GameLog gameLog = new GameLog(charId, log, result);
		gameLogRepo.save(gameLog);
	}

}
