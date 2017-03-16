package ai.profX.admin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ai.profX.config.AppConfig;
import ai.profX.service.CharacterService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class AddCharacters {
	
	@Autowired
	private CharacterService characterService;
	
	@Test
	public void readFileAndAddQuestions(){
		String fileName = "characters.txt";

		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			stream.forEach(e -> addQuestion(e));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addQuestion(String characterName){
		characterService.addNewCharacter(characterName.trim().toLowerCase());
	}
}
