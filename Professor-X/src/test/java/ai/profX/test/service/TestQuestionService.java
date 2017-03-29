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
	
	@Test
	public void test(){
		System.out.println(questionService.getTotalQuestionCount());
		
		questionService.addQuestion("Dummy questions 2?");
		
		Question question = questionService.getQuestionByText("Dummy questions 2?");
		if(question!=null)
			System.out.println(question.getQuestionId());
	}
	
	
	public void getQuestionById(){
		System.out.println(questionService.getQuestionById(1).getText());
	}
	
}
