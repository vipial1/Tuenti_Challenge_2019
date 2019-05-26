package folders;

import main.Punch;

public class BottomFolder implements Folder {

	@Override
	public PairOfPunches unfoldPunch(Punch currentPunch, int width, int height) {
		
		Punch topPunch = currentPunch;
		Punch bottomPunch = new Punch(currentPunch.getX(), ((2*height)-1) - currentPunch.getY());
		
		return new PairOfPunches(bottomPunch, topPunch);
	}

}
