package ai.profX.model;

import java.util.Date;

import org.springframework.data.annotation.Id;

public class Question {
	@Id
	private long questionId;
	private String text;
	private Date createdDateTime;
	
	public Question(){}
	
	public Question(String text) {
		this.text = text.trim().toLowerCase();
		this.createdDateTime = new Date();
	}

	public long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(long questionId) {
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
