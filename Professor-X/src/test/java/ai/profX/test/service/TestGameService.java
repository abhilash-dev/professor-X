package ai.profX.test.service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.math.DoubleMath;

import ai.profX.config.AppConfig;
import ai.profX.model.Character;
import ai.profX.model.Question;
import ai.profX.service.CharacterService;
import ai.profX.service.Game;
import ai.profX.service.QuestionService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfig.class })
public class TestGameService {
	
	@Autowired
	private CharacterService characterService;
	
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private Game game;
	
	public void test() {
		double positiveFraction = 0.0, negativeFraction = 0.0, entropy = 0.0;
		double pOfI = (double)6/10;
		double nOfI = (double)4/10;
		positiveFraction = (-1 * pOfI) * DoubleMath.log2(pOfI);
		negativeFraction = (-1 * nOfI) * DoubleMath.log2(nOfI);

		entropy = positiveFraction + negativeFraction;
		entropy *= (6 + 4) / 10;

		if (entropy != 0.0)
			entropy = 1 / entropy;
		else
			entropy = Double.POSITIVE_INFINITY;
	}
	
	@Test
	public void testEntropy(){
		List<Character> characterList = characterService.getAllCharacters();
		List<Question> questionList = questionService.getAllQuestions();
		/*FileWriter fw = null;
		try {
			fw = new FileWriter("E:\\AllConfidenceOutput.txt", true);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		BufferedWriter writer = new BufferedWriter(fw);
		for(Question q:questionList){
			try {
				writer.write(String.valueOf(q.getQuestionId()));
				writer.write(String.valueOf(game.getId3Entropy(characterList, q)));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}*/
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("file.txt"))) {
			for(Question q:questionList){
				writer.write(String.valueOf(q.getQuestionId()));
				writer.newLine();
				writer.write(String.valueOf(game.getId3Entropy(characterList, q)));
		        writer.newLine();
		    }
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
