package ai.profX.service;

import java.util.List;

import com.google.common.collect.Table;

import ai.profX.model.Character;
import ai.profX.model.Confidence;

public interface ConfidenceService {
	public void initConfidence(long charId, long questionId, int value);
	public void updateConfidence(long charId, long questionId, int value);
	public List<Confidence> getAllConfidenceObjects();
	public int getConfidenceValue(long charId, long questionId);
	public Confidence getConfidenceByQuestionId(long questionId);
	public Confidence getConfidenceByCharacterId(long charId);
	public Table<Long, Long, Integer> getAllConfidenceValues();	
}
