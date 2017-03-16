package ai.profX.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class Confidence {
	@Id
	private ObjectId id;
	private long characterId;
	private long questionId;
	private int value;
	
	public Confidence(){}
	
	public Confidence(long characterId, long questionId, int value) {
		this.characterId = characterId;
		this.questionId = questionId;
		this.value = value;
	}

	public Confidence(long characterId, long questionId) {
		this.characterId = characterId;
		this.questionId = questionId;
		this.value = 0;
	}

	public long getCharacterId() {
		return characterId;
	}

	public void setCharacterId(long characterId) {
		this.characterId = characterId;
	}

	public long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(long questionId) {
		this.questionId = questionId;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
