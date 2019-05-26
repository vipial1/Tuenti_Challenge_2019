import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;

public class AlphabetMain {
	public static void main(String[] args) {
		
		Scanner in = new Scanner(System.in);
        short cases; //short should be enough for c<=100

		cases = in.nextShort();
		in.nextLine();
		
		 for(short caseN=0;caseN<cases;caseN++){
			short wordsSize = in.nextShort();
			in.nextLine();
			Dictionary dictionary = new Dictionary();
			Alphabet alphabet = new Alphabet();
			for(short wordN=0;wordN<wordsSize;wordN++){
				String word = in.nextLine();
				dictionary.addLast(word);
				alphabet.addCharsFromWord(word);
			}
			

		
			
			HashSet<Comparation> comparations = new HashSet<Comparation>();
			createComparations(dictionary, comparations);
			
			//comparations.forEach(comp -> System.out.println(comp));
			
			
			AlphabetComparator comparator = new AlphabetComparator(comparations);
			
			if(caseN == 88) {
				System.out.print("");
			}
			
			comparator.sort(alphabet);
			
			 System.out.print("Case #"+(caseN+1)+": " + alphabet.toString());
			 
			 if(caseN < cases-1){
				 System.out.print("\n");
			 }
			 
			 
		  } 
		in.close();
	}
	
	private static void createComparations(LinkedList<String> words,HashSet<Comparation> comparations) {

		if(words.size() < 2) {
			return;
		}

		char lastChar = words.get(0).charAt(0);

		LinkedList<String> innerWords = new LinkedList<String>();
		if(words.get(0).length()>1) {
			innerWords.add(words.get(0).substring(1));
		}
		
		for(int i=1;i<words.size();i++) {
			String word = words.get(i);
			char currChar = word.charAt(0);
			if(lastChar == currChar) {
				if(word.length()>1) {
					innerWords.add(word.substring(1));
				}
			} else {
				comparations.add(new Comparation(lastChar, currChar));
				lastChar = currChar;
				createComparations(innerWords, comparations);
				innerWords = new LinkedList<String>();
				if(word.length()>1) {
					innerWords.add(word.substring(1));
				}
			}
		}
		createComparations(innerWords, comparations);
	}
	
}
