package ai.profX.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class Confidence {
	@Id
	private ObjectId id;
	private long characterId;
	private long questionId;
	private int value;
}
