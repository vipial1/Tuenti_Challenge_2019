import java.util.HashMap;

public class PlanetHelper {

	HashMap<String, Planet> name2Planet = new HashMap<String,Planet>();

	public Planet getOrCreatePlanet(String planetName) {
		
		if(!name2Planet.containsKey(planetName)) {
			Planet planet = new Planet(planetName);
			name2Planet.put(planetName, planet);
		}
		
		return name2Planet.get(planetName);
	}
	
	public int getPaths2NewEarth() {
		Planet source = name2Planet.get(Planet.GALACTICA);

		return source.getRoutes2NewEarth();
		
	}
	
}
