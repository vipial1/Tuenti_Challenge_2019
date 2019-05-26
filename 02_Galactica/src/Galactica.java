/****************************
 * Vicent Picornell
 * vicent.de.oliva@gmail.com
 * Challenge 2 - Galactica
 * java + eclipse
 ****************************/
import java.util.HashMap;
import java.util.Scanner;

public class Galactica {
	
	private final static String PLANET_SEPARATOR = ":";
	private final static String DESTINATION_SEPARATOR = ",";

	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
        short cases; //short should be enough for c<=100
		cases = in.nextShort();
		
		 for(short caseN=0;caseN<cases;caseN++){
			int P = in.nextInt();
			in.nextLine();
			PlanetHelper helper = new PlanetHelper();

			for(short planetN = 0;planetN<P;planetN++) {
				String line = in.nextLine();
				String planetName = line.substring(0, line.indexOf(PLANET_SEPARATOR));
				Planet planet = helper.getOrCreatePlanet(planetName);
				String destinationsNames = line.substring(planetName.length()+1);
				String[] destinations = destinationsNames.split(DESTINATION_SEPARATOR);
				for(String destinationName : destinations) {
					Planet destination = helper.getOrCreatePlanet(destinationName);
					planet.addDestination(destination);
				}
			}

			 System.out.print("Case #"+(caseN+1)+": " + helper.getPaths2NewEarth());
			 if(caseN < cases-1){
				 System.out.print("\n");
			 }
		  } 
		in.close();
	}
	


}
