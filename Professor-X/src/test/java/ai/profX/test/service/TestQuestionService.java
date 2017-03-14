package ai.profX.test.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ai.profX.config.AppConfig;
import ai.profX.model.Question;
import ai.profX.service.QuestionService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class TestQuestionService {
	
	@Autowired
	private QuestionService questionService;
	
	
	public void test(){
		System.out.println(questionService.getTotalQuestionCount());
		
		questionService.addQuestion("Is your character alive?");
		
		Question question = questionService.getQuestionByText("is your character alive?");
		if(question!=null)
			System.out.println(question.getQuestionId());
	}
	
	@Test
	public void getQuestionById(){
		System.out.println(questionService.getQuestionById(1).getText());
	}
	
}
