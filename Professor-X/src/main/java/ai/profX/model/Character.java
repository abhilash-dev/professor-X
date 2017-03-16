package ai.profX.model;

import java.util.Date;

import org.springframework.data.annotation.Id;

public class Character {
	@Id
	private long charId;
	private String name;
	private Date createdDateTime;
	private int noOfTimesPlayed;
	private Date lastPlayedDateTime;
	
	public Character(){}
	
	public Character(String name) {
		this.name = name.trim().toLowerCase();
		this.createdDateTime = new Date();
		this.noOfTimesPlayed = 0;
		this.lastPlayedDateTime = new Date();
	}

	public long getCharId() {
		return charId;
	}

	public void setCharId(long charId) {
		this.charId = charId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(Date createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public int getNoOfTimesPlayed() {
		return noOfTimesPlayed;
	}

	public void setNoOfTimesPlayed(int noOfTimesPlayed) {
		this.noOfTimesPlayed = noOfTimesPlayed;
	}

	public Date getLastPlayedDateTime() {
		return lastPlayedDateTime;
	}

	public void setLastPlayedDateTime(Date lastPlayedDateTime) {
		this.lastPlayedDateTime = lastPlayedDateTime;
	}
}
