import java.util.Collection;
import java.util.HashSet;

public class Planet {

	private final String name;
	private HashSet<Planet> destinations = new HashSet<Planet>();
	private int routes2Destination = 0;
	
	public final static String GALACTICA = "Galactica";
	public final static String NEW_EARTH = "New Earth";

	Planet(String planetName){
		this.name = planetName;
	}
	
	public void addDestination(Planet destination) {
		this.destinations.add(destination);
	}
	
	public boolean isGalactica() {
		return this.name.equals(GALACTICA);
	}
	
	public boolean isNewEarth() {
		return this.name.equals(NEW_EARTH);
	}
	
	public int getRoutes2NewEarth() {
		if(routes2Destination > 0) {
			return routes2Destination;
		}
		for(Planet destination : destinations) {
			if(destination.isNewEarth()) {
				routes2Destination++;
			} else {
				routes2Destination += destination.getRoutes2NewEarth();
			}
		}
		return routes2Destination;
	}
}
