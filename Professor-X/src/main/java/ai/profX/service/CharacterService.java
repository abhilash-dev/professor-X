package ai.profX.service;

import java.util.List;

import ai.profX.model.Character;

public interface CharacterService {
	public long addNewCharacter(String name);
	public List<Character> getAllCharacters();
	public Character getCharacterByName(String name);
	public Character getCharacterById(long charId);
	public int getNoOfUnknownCharactersForQuestionId(List<Character> characters,long questionId);
	public int getNoOfCharactersWithPositiveConfidenceForQuestionId(List<Character> characters,long questionId);
	public int getNoOfCharactersWithNegativeConfidenceForQuestionId(List<Character> characters,long questionId);
	public void removeCharacter(long charId);
	public void updateNoOfTimesPlayed(long charId);
	public long getTotalCharacterCount();
}
