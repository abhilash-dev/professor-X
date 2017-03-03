package ai.profX.test.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ai.profX.config.AppConfig;
import ai.profX.model.repo.CharacterRepo;
import ai.profX.service.CharacterService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class TestCharacterService {
	
	@Autowired
	private CharacterService characterService;
	
	@Autowired
	private CharacterRepo characterRepo;
	
	@Test
	public void test(){
		System.out.println(characterService.getTotalCharacterCount());
		System.out.println(characterRepo.count());
	}
}
