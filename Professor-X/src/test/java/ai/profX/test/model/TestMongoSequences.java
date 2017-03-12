package ai.profX.test.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ai.profX.config.AppConfig;
import ai.profX.model.CustomSequences;
import ai.profX.model.repo.CustomSequencesRepo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class TestMongoSequences {
	
	@Autowired
	private CustomSequencesRepo customSequencesRepo;
	
	@Test
	public void init(){
		CustomSequences characterSequence = new CustomSequences("character",0);
		customSequencesRepo.save(characterSequence);
		
		CustomSequences questionSequence = new CustomSequences("question",0);
		customSequencesRepo.save(questionSequence);
	}
}
