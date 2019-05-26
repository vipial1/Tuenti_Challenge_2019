package main;

import java.util.Comparator;

public class PunchComparator implements Comparator<Punch> {

	@Override
	public int compare(Punch o1, Punch o2) {
		
		if(o1.getX() == o2.getX()) {
			return o1.getY() - o2.getY();
		}
		
		return o1.getX() - o2.getX();
	}



}
