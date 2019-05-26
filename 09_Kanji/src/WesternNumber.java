import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class WesternNumber {
	private final static int MAX_NUMBER = 99999;
	private String kangis;
	private HashSet<Integer> possibleNumbers = new HashSet<Integer>();
	private HashMap<Integer, Integer> digit2occurences = new HashMap<Integer, Integer>();
	private LinkedList<Integer> allDigits = new LinkedList<Integer>();
	private HashSet<Integer> multipliers = new HashSet<Integer>();
	
	WesternNumber(String number){
		this.kangis = number;
		
		calculateOcurrences();
		calculatePossibleNumbers();
	}
	
	private void calculateOcurrences() {
		for(char k : this.kangis.toCharArray()) {
			int kanji = KanjiHelper.getDecimalValue(k);
			addKanji(kanji);
			allDigits.add(kanji);
		}
		
	}
	
	private void addKanji(int kanji) {
		if(kanji>9) {
			addMultiplier(kanji);
		} else {
			addDigit(kanji);
		}
	}
	
	private void addDigit(int digit) {
		int occurences = 0;
		if(digit2occurences.containsKey(digit)) {
			occurences = digit2occurences.get(digit);
		}
		digit2occurences.put(digit, occurences+1);
	}
	
	private void addMultiplier(int mult) {
		
		multipliers.add(mult);
	}
	
	private void calculatePossibleNumbers() {
		LinkedList<Integer> digits = getAllDigits();
		HashSet<Integer> mult = new HashSet<Integer>(this.multipliers);
		int acumulate = 0;
		
		doCalculatePossibleNumbers(acumulate, digits, mult);
		
		return;
	}
	
	private void doCalculatePossibleNumbers(int acumulate, LinkedList<Integer> digits, HashSet<Integer> mult) {
		HashSet<Integer> remainingMult = new HashSet<Integer>(mult);
		
		if(digits.isEmpty() || mult.isEmpty()) {
			if(!mult.isEmpty() && digits.isEmpty()) {
				for(int m : mult) {
					acumulate += m;
				}
			} else if(mult.isEmpty() && digits.size() ==1) {
				acumulate += digits.getFirst();
			} else if(digits.size() > 1 || mult.size() > 1) {
				return;
			}

			if(acumulate < MAX_NUMBER) {
				possibleNumbers.add(acumulate);
			}
			return;
		}
		
		for(int m : mult) {
			remainingMult.remove(m);
			LinkedList<Integer> remainingDigits = new LinkedList<Integer>(digits);

			//multiplier can work without digit, except 10000
			if(m < 10000 ) {
				doCalculatePossibleNumbers(acumulate + m,remainingDigits,remainingMult);
			}
			remainingDigits = new LinkedList<Integer>(digits);

			for(int d : digits) {
				if(!(d == 1 && m < 10000)) {
					remainingDigits.remove(new Integer(d));
					int accum =  acumulate + (d * m);
					doCalculatePossibleNumbers(accum,remainingDigits,remainingMult);
					remainingDigits.add(d);
				}

			}
			remainingMult.add(m);
		}
	}

	private LinkedList<Integer> getAllDigits(){
		LinkedList<Integer> allDigits = new LinkedList<Integer>();
		
		for(int digit : digit2occurences.keySet()) {
			for(int ocu=0; ocu < digit2occurences.get(digit); ocu++) {
				allDigits.add(digit);
			}
		}
		return allDigits;
	}
	
	public HashSet<Integer> getPossibleNumbers(){
		return possibleNumbers;
	}
}
