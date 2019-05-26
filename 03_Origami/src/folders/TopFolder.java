package folders;

import main.Punch;

public class TopFolder implements Folder {
	
	@Override
	public PairOfPunches unfoldPunch(Punch currentPunch, int width, int height) {
		
		Punch bottomPunch = new Punch(currentPunch.getX(), currentPunch.getY() + height);
		Punch topPunch = new Punch(currentPunch.getX(), height - 1 - currentPunch.getY());
		
		return new PairOfPunches(bottomPunch, topPunch);
	}

}
