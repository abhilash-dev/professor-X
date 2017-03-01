package ai.profX.model;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;

import ai.profX.model.repo.QuestionRepo;

public class Question {
	@Id
	private long questionId;
	private String text;
	private Date createdDateTime;
	
	@Autowired
	private QuestionRepo questionRepo;
	
	public Question(String text) {
		this.text = text.toLowerCase();
		this.createdDateTime = new Date();
		this.questionId = questionRepo.count()+1;
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
