package ai.profX.model;

import org.springframework.data.annotation.Id;

public class CustomSequences {
	
	@Id
	private String id;
	private long seq;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public long getSeq() {
		return seq;
	}
	
	public void setSeq(long seq) {
		this.seq = seq;
	}
	
	public CustomSequences(String id, long seq) {
		this.id = id;
		this.seq = seq;
	}
}
