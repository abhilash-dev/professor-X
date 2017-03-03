package ai.profX.service.impl;

import java.util.List;

import com.google.common.collect.Table;

import ai.profX.model.Confidence;
import ai.profX.service.ConfidenceService;

public class ConfidenceServiceImpl implements ConfidenceService {

	@Override
	public void initConfidence(long charId, long questionId, int value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateConfidence(long charId, long questionId, int value) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Confidence> getAllConfidenceObjects() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getConfidenceValue(long charId, long questionId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Confidence getConfidenceByQuestionId(long questionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Confidence getConfidenceByCharacterId(long charId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Table<Long, Long, Integer> getAllConfidenceValues() {
		// TODO Auto-generated method stub
		return null;
	}

}
