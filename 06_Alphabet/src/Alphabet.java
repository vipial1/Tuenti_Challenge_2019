import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class Alphabet {
	
	private HashSet<Character> allChars = new HashSet<Character>();
	private HashMap<Character, Integer> char2index = new HashMap<Character, Integer>();
	private boolean isAmbiguous = false;
	
	
	public void addCharsFromWord(String word) {
		for(char c : word.toCharArray()) {
			allChars.add(c);
			
			if(!char2index.keySet().contains(c)) {
				char2index.put(c, char2index.keySet().size());
			}
		}
	}
	
	public HashSet<Character> getAlphabet(){
		return allChars;
	}
	
	public LinkedList<Character> getSortedAlphabet(){
		LinkedList<Character> sortedChar = new LinkedList<Character>();
		for(int i=0;i<char2index.size();i++) {
			sortedChar.addLast(getCharAtIndex(i));
		}
		return sortedChar;
	}
	
	public int getIndexOf(char c) {
		return char2index.get(c);
	}
	
	public void shiftChars(char c1, char c2) {
		int i1= char2index.get(c1);
		char2index.put(c1, char2index.get(c2));
		char2index.put(c2, i1);
	}

	@Override
	public String toString() {
		
		if(isAmbiguous) {
			return "AMBIGUOUS";
		}
		
		LinkedList<Character> sortedChar = getSortedAlphabet();
		
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i< sortedChar.size(); i++) {
			builder.append(sortedChar.get(i));
			if(i < sortedChar.size() -1) {
				builder.append(" ");
			}
		}
		return builder.toString();
	}
	
	private char getCharAtIndex(int index) {
		for(char c : char2index.keySet()) {
			if(char2index.get(c) == index) {
				return c;
			}
		}
		return ' ';
	}

	public void setAmbiguous() {
		this.isAmbiguous = true;
	}
}
