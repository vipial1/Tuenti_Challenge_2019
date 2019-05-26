package folders;

import main.Punch;

public class LeftFolder implements Folder {

	@Override
	public PairOfPunches unfoldPunch(Punch currentPunch, int width, int height) {
		Punch rigthPunch = new Punch(currentPunch.getX() + width, currentPunch.getY());
		Punch leftPunch = new Punch(width - 1 -currentPunch.getX(), currentPunch.getY());
		
		return new PairOfPunches(leftPunch, rigthPunch);
	}

}
