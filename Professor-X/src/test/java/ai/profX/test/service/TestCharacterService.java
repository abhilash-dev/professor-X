package ai.profX.test.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ai.profX.config.AppConfig;
import ai.profX.model.Character;
import ai.profX.service.CharacterService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class TestCharacterService {
	
	@Autowired
	private CharacterService characterService;
	
	@Test
	public void test(){
		String text = "Virat Kohli";
		Character character;
		if(text.trim()!="") {
			character = characterService.getCharacterByName(text.trim().toLowerCase());
			if (character != null) {
				long characterId = character.getCharId();
			}else{
				System.out.println();
			}
		}

	}
}
