package ai.profX.admin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ai.profX.config.AppConfig;
import ai.profX.model.Character;
import ai.profX.model.Question;
import ai.profX.model.repo.CharacterRepo;
import ai.profX.service.ConfidenceService;
import ai.profX.service.QuestionService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class AddCharacters {
	
	@Autowired
	private CharacterRepo characterRepo;
	
	@Autowired
	private QuestionService questionService;

	@Autowired
	private ConfidenceService confidenceService;
	
	@Test
	public void readFileAndAddCharacters(){
		String fileName = "characters.txt";

		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			Iterator<String> lineIterator = stream.iterator();
			String line = null;
			String[] lineItems = null;
			String characterName = null;
			long charId;
			
			while(lineIterator.hasNext()){
				line = lineIterator.next();
				lineItems = line.split("\t");
				charId = Long.parseLong(lineItems[0]);
				characterName = lineItems[1];
				//printItems(charId, characterName);
				addCharacter(charId, characterName);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addCharacter(long charId, String characterName){
		Character character = new Character(characterName.trim().toLowerCase());
		character.setCharId(charId);
		characterRepo.insert(character);

		List<Question> questionList = questionService.getAllQuestions();
		if(questionList!=null){
			Iterator<Question> questionIterator = questionList.iterator();

			while (questionIterator.hasNext()) {
				confidenceService.initConfidence(charId, questionIterator.next().getQuestionId());
			}
		}
	}
	
	public void printItems(long charId, String characterName){
		System.out.println(charId + " " +characterName);
	}
}
