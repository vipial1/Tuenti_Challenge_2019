import java.math.BigDecimal;
import java.math.MathContext;
import java.util.HashSet;
import java.util.LinkedList;

public class PhysicsHelper {
	
	public final static BigDecimal PI = new BigDecimal(3.14159);


	
	public static BigDecimal calculateDistance(Moon m1, Moon m2) {
		return calculateDistanceWithTrigo(m1, m2);
	}
	
	public static BigDecimal calculateDistanceWithTrigo(Moon m1, Moon m2) {
		
		UniversePoint m1Position = null;
		UniversePoint m2Position = null;
		
		if(m1 != null && m2 != null) {
			m1Position = m1.getPosition();
			m2Position = m2.getPosition();
		} 
		if(m1 == null) {
			m1Position = new UniversePoint();
			m2Position = m2.getPosition();
		}
		if(m2 == null) {
			m1Position = m1.getPosition();
			m2Position = new UniversePoint();
		}
		
		BigDecimal xDiff = (m2Position.getX().subtract(m1Position.getX()));
		BigDecimal yDiff = (m2Position.getY().subtract(m1Position.getY()));

		BigDecimal xDiffSqr = xDiff.pow(2);
		BigDecimal yDiffSqr = yDiff.pow(2);
		
		return xDiffSqr.add(yDiffSqr).sqrt(MathContext.DECIMAL32);
	}
	
	public static HashSet<Moon> getPossibleTravels(HashSet<Moon> allMoons, Moon moon, BigDecimal range, int remainingCapacity, LinkedList<Moon> visited){
		HashSet<Moon> result = new HashSet<Moon>();
		
		HashSet<Moon> possible = new HashSet<Moon>(allMoons);
		possible.removeAll(visited);
		
		for(Moon m : possible) {
			BigDecimal distance = moon == null ? m.getDistance2PlanetInMKm() :calculateDistance(moon, m);
			if(isPossible(m,range.subtract(distance),remainingCapacity-m.getUnobtanium())) {
				result.add(m);
			}

		}
		return result;
	}
	
	public static boolean isPossible(Moon moon, BigDecimal range, int remainingCapacity) {
		return canComeBackHome(moon, range) && remainingCapacity >= 0;
		
	}
	
	private static boolean canComeBackHome(Moon moon, BigDecimal range) {
		if(range.compareTo(BigDecimal.ZERO) < 0) {
			return false;
		}
		
		return range.compareTo(moon.getDistance2PlanetInMKm()) >= 0;

	}
	
}
