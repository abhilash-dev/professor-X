package ai.profX.model;

import java.util.Date;

import org.springframework.data.annotation.Id;

public class Question {
	@Id
	private int questionId;
	private String text;
	private Date createdDateTime;
	
	public Question(String text) {
		this.text = text.toLowerCase();
		this.createdDateTime = new Date();
		this.questionId = 1; //TODO to get count from the DB and make this count()+1
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(Date createdDateTime) {
		this.createdDateTime = createdDateTime;
	}
}
