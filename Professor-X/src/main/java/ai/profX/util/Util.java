package ai.profX.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

import ai.profX.common.Constants;

public class Util {
	public static LinkedHashMap<Long, Integer> sortByValueDesc(LinkedHashMap<Long, Integer> map){
		LinkedHashMap<Long, Integer> sortedMap = new LinkedHashMap<>();
		Stream<Map.Entry<Long,Integer>> stream = map.entrySet().stream();
		
		stream.sorted(Map.Entry.<Long,Integer>comparingByValue().reversed()).forEachOrdered(e -> sortedMap.put(e.getKey(), e.getValue()));
		
		return sortedMap;
	}
	
	public static boolean isQuestionIdPresent(long questionId,HashMap<Long, Integer> askedQuestions) {
		Iterator<Long> questionIdIterator = askedQuestions.keySet().iterator();
		while(questionIdIterator.hasNext()){
			if(questionIdIterator.next()==questionId)
				return true;
		}
		return false;
	}
	
	public static boolean isAnswerValid(int answer) {
		if(answer == Constants.YES || answer == Constants.NO || answer == Constants.I_DONT_KNOW){
			return true;
		}
		return false;
	}

	public static boolean isCharacterIdPresent(long characterId,LinkedHashMap<Long, Integer> characterValues) {
		Iterator<Long> chracterIdIterator = characterValues.keySet().iterator();
		
		while(chracterIdIterator.hasNext()){
			if(characterId == chracterIdIterator.next())
				return true;
		}
		return false;
	}
}
