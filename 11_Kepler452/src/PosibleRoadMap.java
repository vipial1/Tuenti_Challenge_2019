import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class PosibleRoadMap {
	private HashSet<RoadMap> payloads = new HashSet<RoadMap>();
	
	public void addPayLoad(LinkedList<Moon> visited) {

		RoadMap roadMap = new RoadMap(visited);

		payloads.add(roadMap);
	}

	public RoadMap getBest() {
		
		int max=0;
		RoadMap best = RoadMap.EMPTY;
		
		for(RoadMap payload : payloads) {
			int sum = payload.stream().mapToInt(i -> i.getUnobtanium()).sum();
			if(sum > max) {
				max = sum;
				best = payload;
			}
		}
		
		return best;
	}
}
