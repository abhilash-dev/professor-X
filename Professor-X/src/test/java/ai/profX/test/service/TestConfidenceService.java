package ai.profX.test.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ai.profX.config.AppConfig;
import ai.profX.model.Confidence;
import ai.profX.service.ConfidenceService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class TestConfidenceService {
	
	@Autowired
	private ConfidenceService confidenceService;
	
	@Test
	public void test(){
		
		long characterId = 1;
		long questionId = 2;
		
		List<Confidence> confidenceList = confidenceService.getConfidenceByCharacterId(characterId);
		List<Confidence> confidenceList2 = confidenceService.getConfidenceByQuestionId(questionId);
	}
}
