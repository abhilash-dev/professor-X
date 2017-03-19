package ai.profX.admin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.Stream;

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
public class InitializeConfidence {
	
	@Autowired
	private ConfidenceService confidenceService;
	
	@Test
	public void readFileAndAddCharacters(){
		String fileName = "confidence.txt";

		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			Iterator<String> lineIterator = stream.iterator();
			String line = null;
			String[] lineItems = null;
			long charId,questionId;
			int value;
			
			while(lineIterator.hasNext()){
				line = lineIterator.next();
				lineItems = line.split("\t");
				charId = Long.parseLong(lineItems[0]);
				questionId = Long.parseLong(lineItems[1]);
				value = Integer.parseInt(lineItems[2]);
				//printItems(charId, questionId, value);
				updateConfidence(charId, questionId, value);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void updateConfidence(long charId,long questionId,int value){
		Confidence confidence = confidenceService.getConfidence(charId, questionId);
		if(confidence!=null){
			confidenceService.updateConfidence(charId, questionId, value);
		}
	}
	
	public void printItems(long charId,long questionId,int value){
		System.out.println(charId+" "+questionId+" "+value);
	}
}
