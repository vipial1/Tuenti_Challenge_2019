/****************************
 * Vicent Picornell
 * vicent.de.oliva@gmail.com
 * Challenge 11 - Kepler
 * java + eclipse
 ****************************/
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;

public class Kepler {
	
	private static String SEPARATOR = " ";
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
        short cases; //short should be enough for c<=100
		cases = in.nextShort();
		
		 for(short caseN=0;caseN<cases;caseN++){
			int M = in.nextInt();
			in.nextLine();
			String[] distances = in.nextLine().split(SEPARATOR);
			String[] radius = in.nextLine().split(SEPARATOR);
			String[] period = in.nextLine().split(SEPARATOR);
			String[] unobtanium = in.nextLine().split(SEPARATOR);
			int capacity = in.nextInt();
			float range = in.nextFloat();
			in.nextLine();
			HashSet<Moon> moons = new HashSet<Moon>();
			
			for(int i=0; i<M;i++) {
				moons.add(new Moon(i,distances[i], radius[i],period[i],unobtanium[i]));
			}
			
			RoadMap roadMap = calculateAmounts(moons, capacity, BigDecimal.valueOf(range));

			 System.out.print("Case #"+(caseN+1)+": " +roadMap);
			 if(caseN < cases-1){
				 System.out.print("\n");
			 }
		  } 
		in.close();
	}

	private static RoadMap calculateAmounts(HashSet<Moon> moons, int capacity, BigDecimal range) {
		
		LinkedList<Moon> visited = new LinkedList<Moon>();
		PosibleRoadMap roadMap = new PosibleRoadMap();

		doCalculateAmounts(moons, visited, null, capacity, range, roadMap);
		
		return roadMap.getBest();
	}

	private static PosibleRoadMap doCalculateAmounts(HashSet<Moon> moons, LinkedList<Moon> visited, Moon currentMoon, int capacity, BigDecimal range, PosibleRoadMap roadMap) {
			

		HashSet<Moon> possible = PhysicsHelper.getPossibleTravels(moons, currentMoon, range, capacity, visited);
		
		if(possible.isEmpty()) {
			roadMap.addPayLoad(visited);
			return roadMap;
		}
		
		for(Moon m : possible) {
			visited.addLast(m);
			BigDecimal distance2moon = PhysicsHelper.calculateDistance(currentMoon, m);
			MoonHelper.moveStepAll(moons);
			doCalculateAmounts(moons, visited, m, capacity - m.getUnobtanium(), range.subtract(distance2moon), roadMap);
			MoonHelper.moveStepBackAll(moons);
			visited.remove(m);
		}

		return roadMap;
	}

}
