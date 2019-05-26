import java.util.HashMap;

public class KanjiHelper {

	private static HashMap<Character, Integer> kanjiMap = new HashMap<Character, Integer>();
	
	static {
		kanjiMap.put('一', 1);
		kanjiMap.put('二', 2);
		kanjiMap.put('三', 3);
		kanjiMap.put('四', 4);
		kanjiMap.put('五', 5);
		kanjiMap.put('六', 6);
		kanjiMap.put('七', 7);
		kanjiMap.put('八', 8);
		kanjiMap.put('九', 9);
		kanjiMap.put('十', 10);
		kanjiMap.put('百', 100);
		kanjiMap.put('千', 1000);
		kanjiMap.put('万', 10000);

	}
	
	public static int getDecimalValue(char c) {
		Integer value = kanjiMap.get(c);
		if(value == null) {
			return -1;
		}
		return value;
	}
	
}
