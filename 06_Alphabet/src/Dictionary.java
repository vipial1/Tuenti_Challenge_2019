import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.stream.Collectors;

public class Dictionary extends LinkedList<String> {
	
	HashMap<Character, HashSet<String>> char2Words = new HashMap<Character, HashSet<String>>();
	HashMap<Character, HashSet<String>> wordsByFirstChar = new HashMap<Character, HashSet<String>>();
	HashMap<String, Integer> word2Position = new HashMap<String, Integer>();

	
	@Override
	public void addLast(String word) {
		super.addLast(word);
		
		for(char c : word.toCharArray()) {
			addCharWord2Map(c, word);
		}
		addWord2FirstChar(word.charAt(0), word);
		word2Position.put(word, this.size()-1);
	}
	
	public int getPositionForWord(String word) {
		return word2Position.get(word);
	}
	
	private void addWord2FirstChar(char c, String word) {
		if(!wordsByFirstChar.containsKey(c)) {
			wordsByFirstChar.put(c, new HashSet<String>());
		}
		
		wordsByFirstChar.get(c).add(word);
	}
	
	private void addCharWord2Map(char c, String word) {
		
		if(!char2Words.containsKey(c)) {
			char2Words.put(c, new HashSet<String>());
		}
		
		char2Words.get(c).add(word);
		
	}
	
	public HashSet<String> getWordsContaining(char c){
		return char2Words.get(c);
	}
	
	public Set<String> getWordsStartingBy(String prefix){
		return this.stream().filter(word -> word.startsWith(prefix)).collect(Collectors.toSet());
	}
	
	
	public HashSet<String> getWordsContaining(char c1, char c2){
		HashSet<String> w1 = getWordsContaining(c1);
		HashSet<String> w2 = getWordsContaining(c2);
		w1.retainAll(w2);
		
		HashSet<String> intersection = new HashSet<String>(w1); 
		intersection.retainAll(w2);
		return intersection;
	}
	
	
	
	public HashSet<String> getWordsStartingBy(char c){
		return wordsByFirstChar.get(c);
	}
}
