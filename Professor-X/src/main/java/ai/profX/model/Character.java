package ai.profX.model;

import java.util.Date;

import org.springframework.data.annotation.Id;

public class Character {
	@Id
	private int charId;
	private String name;
	private Date createdDateTime;
	private int noOfTimesPlayed;
	private Date lastPlayedDateTime;
	
	public Character(String name) {
		this.name = name.toLowerCase();
		this.createdDateTime = new Date();
		this.noOfTimesPlayed = 1;
		this.lastPlayedDateTime = new Date();
		this.charId = 1; //TODO fetch the count from the DB and make this count()+1
	}

	public int getCharId() {
		return charId;
	}

	public void setCharId(int charId) {
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
