import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class RoadMap extends LinkedList<Moon> {
	
	public static RoadMap EMPTY = new RoadMap();

	public RoadMap(List<Moon> moons) {
		super();
		this.addAll(moons);
	}
	
	private RoadMap(){
		super();
	}

	@Override
	public String toString() {
		
		if(this.isEmpty()) {
			return "None";
		}
		
		Collections.sort(this);
		StringBuilder builder = new StringBuilder();
		for(int i=0; i<this.size();i++) {
			builder.append(this.get(i).getUnobtanium());
			if(i < this.size() -1) {
				builder.append(" ");
			}
		}
		return builder.toString();
	}

}
