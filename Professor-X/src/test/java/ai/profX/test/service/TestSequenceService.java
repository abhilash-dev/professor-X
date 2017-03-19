package ai.profX.test.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ai.profX.config.AppConfig;
import ai.profX.service.NextSequenceService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class TestSequenceService {
	
	@Autowired
	private NextSequenceService nextSequenceService;
	
	@Test
	public void test(){
		System.out.println(nextSequenceService.getCurrentSequence("character"));

	}
}
