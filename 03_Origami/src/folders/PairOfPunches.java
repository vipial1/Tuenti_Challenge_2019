package folders;

import main.Punch;

public class PairOfPunches {

	private final Punch A;
	private final Punch B;

	PairOfPunches(Punch A, Punch B){
		this.A = A;
		this.B = B;
	}
	
	public Punch getA() {
		return A;
	}
	
	public Punch getB() {
		return B;
	}
	
}
