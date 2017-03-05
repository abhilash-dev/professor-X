package ai.profX.util;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Util {
	public static LinkedHashMap<Long, Integer> sortByValueDesc(LinkedHashMap<Long, Integer> map){
		LinkedHashMap<Long, Integer> sortedMap = new LinkedHashMap<>();
		Stream<Map.Entry<Long,Integer>> stream = map.entrySet().stream();
		
		stream.sorted(Map.Entry.<Long,Integer>comparingByValue().reversed()).forEachOrdered(e -> sortedMap.put(e.getKey(), e.getValue()));
		
		return sortedMap;
	}
}
