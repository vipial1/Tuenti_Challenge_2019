import java.util.HashSet;

public class MoonHelper {

	
	public static void moveStepAll(HashSet<Moon> allMoons) {
		for(Moon m : allMoons) {
			m.moveOneStep();
		}
	}
	
	
	public static int calculatePayload(HashSet<Moon> visited) {
		return visited.stream().mapToInt(m -> m.getUnobtanium()).sum();
	}

	public static void moveStepBackAll(HashSet<Moon> allMoons) {
		for(Moon m : allMoons) {
			m.moveOneStepBack();
		}		
	}
}
