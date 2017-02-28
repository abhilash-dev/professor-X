package ai.profX.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class Confidence {
	@Id
	private ObjectId id;
	private int characterId;
	private int questionId;
	private int value;
}
