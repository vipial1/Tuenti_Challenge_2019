package folders;

import main.Fold;
import main.Punch;

public interface Folder {
	public PairOfPunches unfoldPunch(Punch currentPunch, int width, int height);
}
