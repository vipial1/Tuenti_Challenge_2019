import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.stream.Collectors;

public class Case {

	private final int initialGold;
	private final Character desiredChar;
	private final HashSet<String> expectedSkills = new HashSet<String>();
	private int bestCost = Integer.MAX_VALUE;
	
	public Case(String line){
		
		String[] lineSplitted = line.split(Main.SEPARATOR);
		this.initialGold = Integer.parseInt(lineSplitted[0]);
		this.desiredChar = Main.almanac.getCharacter(lineSplitted[1]);
		int expectedSkillsN = Integer.parseInt(lineSplitted[2]);
		
		for(int i=0;i<expectedSkillsN;i++) {
			this.expectedSkills.add(lineSplitted[3+i]);
		}
		Main.almanac.resetAll();
	}
	
	public String getSolution() {
		if(bestCost == Integer.MAX_VALUE) {
			return "IMPOSSIBLE";
		}
		return Integer.toString(bestCost);
	}
	
	
	public int solve() {
		LinkedList<Character> initialChar = new LinkedList<Character>();
		initialChar.add(desiredChar);
		return doSolve(initialChar, new LinkedList<String>(desiredChar.getSkills()));
	}
	
	public int doSolve(LinkedList<Character> needChars, LinkedList<String> skills) {
		
		if(hasAllSkills(skills) && calculateCost(needChars)<=initialGold) {
			int cost = calculateCost(needChars);
			if(cost < bestCost) {
				bestCost = cost;
			}
		}
		
		LinkedList<Character> moreChars = new LinkedList<Character>(needChars);
		for(int cIndx = 0 ; cIndx<needChars.size();cIndx++) {
			Character c = needChars.get(cIndx);
			
			HashSet<Fusion> possibleFusions = c.getPosibleFusionsAsResult();
			for(Fusion f:possibleFusions) {
				if(moreChars.contains(f.getChar1()) && moreChars.contains(f.getChar2())) {
					continue;
				} 
				moreChars.removeFirstOccurrence(c);
				moreChars.add(cIndx, f.getChar2());
				moreChars.add(cIndx, f.getChar1());
				skills.addAll(f.getChar1().getSkills());
				skills.addAll(f.getChar2().getSkills());
				doSolve(moreChars, skills);
				moreChars.remove(f.getChar1());
				moreChars.remove(f.getChar2());
				skills.removeAll(f.getChar1().getSkills());
				skills.removeAll(f.getChar2().getSkills());
				moreChars.add(cIndx, c);
			}
		}
		
		return bestCost;
	}
	
	private int calculateCost(LinkedList<Character> needChars) {
		return needChars.stream().mapToInt(c -> c.getCost()).sum();
	}
	
	private boolean hasEnoughGold(LinkedList<Character> needChars) {
		int totalCost = needChars.stream().mapToInt(c -> c.getCost()).sum();
		
		return totalCost < initialGold;
	}
	
	private boolean hasAllSkills(LinkedList<String> skills) {
		//Set<String> allSkills = needChars.stream().flatMap(c->c.getSkills().stream()).collect(Collectors.toSet());
		HashSet<String> desired = new HashSet<String>(expectedSkills);
		desired.removeAll(skills);
		return desired.isEmpty();
		
	}
}
