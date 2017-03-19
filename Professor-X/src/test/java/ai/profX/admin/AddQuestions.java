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
import ai.profX.model.repo.QuestionRepo;
import ai.profX.service.CharacterService;
import ai.profX.service.ConfidenceService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class AddQuestions {
	
	@Autowired
	private QuestionRepo questionRepo;

	@Autowired
	private CharacterService characterService;

	@Autowired
	private ConfidenceService confidenceService;
	
	@Test
	public void readFileAndAddQuestions(){
		String fileName = "questions.txt";

		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			Iterator<String> lineIterator = stream.iterator();
			String line = null;
			String[] lineItems = null;
			String questionText = null;
			long questionId;
			
			while(lineIterator.hasNext()){
				line = lineIterator.next();
				lineItems = line.split("\t");
				questionId = Long.parseLong(lineItems[0]);
				questionText = lineItems[1];
				//printItems(questionId, questionText);
				addQuestion(questionId, questionText);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addQuestion(long questionId, String questionText){
		Question question = new Question(questionText.trim().toLowerCase());
		question.setQuestionId(questionId);
		questionRepo.insert(question);

		List<Character> characterList = characterService.getAllCharacters();
		if(characterList!=null){
			Iterator<Character> characterIterator = characterList.iterator();

			while (characterIterator.hasNext()) {
			confidenceService.initConfidence(characterIterator.next().getCharId(), questionId);
			}
		}
	}
	
	public void printItems(long questionId, String questionText){
		System.out.println(questionId + " " +questionText);
	}
}
