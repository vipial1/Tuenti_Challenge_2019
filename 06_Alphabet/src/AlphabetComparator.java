import java.util.HashSet;
import java.util.LinkedList;

public class AlphabetComparator {

	private HashSet<Comparation> comparations;
	private int loopCounter = 0;
	
	public AlphabetComparator(HashSet<Comparation> comparations) {
		this.comparations = comparations;
	}
	
	public boolean sort(Alphabet alphabet) {

		
		if(comparations.size() < alphabet.getAlphabet().size() -1) {
			alphabet.setAmbiguous();
			return false;
		}
		
		boolean sorted = doSort(alphabet);
		
		if(!sorted) {
			return false;
		}
		
		//All except first and last must have 2 comparators
		
		if(comparations.size() > 0) {
		
			LinkedList<Character> sortedAlph = alphabet.getSortedAlphabet();
			for(int cCounter = 0; cCounter < sortedAlph.size()-1; cCounter++) {
				char c = sortedAlph.get(cCounter);
				char nextC = sortedAlph.get(cCounter+1);
				
				if(!existsComparationsForChars(c, nextC)) {
					alphabet.setAmbiguous();
					return false;
				}
			}
		}
		
		System.out.print("");
		return sorted;
		
	}
		
	private boolean existsComparationsForChars(char bigger, char smaller) {
		for(Comparation comp : comparations) {
			if(comp.getBigger() == bigger && comp.getSmaller() == smaller) {
				return true;
			}
		}
		return false;
	}
	
	private int getComparationsForChar(char c) {
		return existsBiggerComparation(c) + existsSmallerComparation(c);
	}
	
	private int existsBiggerComparation(char c) {
		for(Comparation comp : comparations) {
			if(comp.getBigger() == c) {
				return 1;
			}
		}
		return 0;
	}
	
	private int existsSmallerComparation(char c) {
		for(Comparation comp : comparations) {
			if(comp.getSmaller() == c) {
				return 1;
			}
		}
		return 0;
	}
	
	private boolean doSort(Alphabet alphabet) {
		
		loopCounter++;
		
		if(loopCounter > comparations.size() * comparations.size() && !comparations.isEmpty()) {
			alphabet.setAmbiguous();
			return false;
		}
		
		for(Comparation comp : comparations) {
			int indexBigger = alphabet.getIndexOf(comp.getBigger());
			int indexSmaller = alphabet.getIndexOf(comp.getSmaller());
			
			if(indexSmaller < indexBigger) {
				//need to shift
				alphabet.shiftChars(comp.getBigger(), comp.getSmaller());
				doSort(alphabet);
				break;
			}
		}
		return true;
	}
	


}
