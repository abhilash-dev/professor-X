package ai.profX.service;

import java.util.LinkedHashMap;
import java.util.List;

import ai.profX.model.Character;
import ai.profX.model.Question;

public interface Game {
	public List<Question> getInitialQuestions();
	public LinkedHashMap<Long, Integer> initCharacterValues();
	public LinkedHashMap<Long, Integer> sortCharacterValues(LinkedHashMap<Long, Integer> characterValues);
	public List<Character> getNearByCharacters(LinkedHashMap<Long, Integer> characterValues,int count);
	public LinkedHashMap<Long, Integer> getNearbyCharacterValues(LinkedHashMap<Long, Integer> characterValues,int count);
	public Double getId3Entropy(List<Character> characterList,Question question);
	public String chooseQuestion(List<Question> initialQuestions, LinkedHashMap<Long, Integer> characterValues, List<Question> askedQuestions, int count);
	public void updateLocalKnowledge(LinkedHashMap<Long, Integer> characterValues, List<Question> askedQuestions, long questionId, int answer);
	public Character guess(LinkedHashMap<Long, Integer> characterValues);
	public long learnNewCharacter(List<Question> askedQuestions, String text);
	public void learn(List<Question> askedQuestions, long charId);
}
