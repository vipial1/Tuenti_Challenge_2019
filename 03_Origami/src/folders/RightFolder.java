package folders;

import main.Punch;

public class RightFolder implements Folder {

	@Override
	public PairOfPunches unfoldPunch(Punch currentPunch, int width, int height) {
		Punch leftPunch = currentPunch;
		Punch rigthPunch = new Punch(((2*width)-1) - currentPunch.getX(), currentPunch.getY());
		
		return new PairOfPunches(leftPunch, rigthPunch);
	}

}
