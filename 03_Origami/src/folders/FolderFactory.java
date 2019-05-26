package folders;

import main.Fold;

public class FolderFactory {

	public static Folder createFolder(Fold type) {
		switch(type) {
		case BOTTOM:
			return new BottomFolder();
		case LEFT:
			return new LeftFolder();
		case RIGHT:
			return new RightFolder();
		case TOP:
			return new TopFolder();
		default:
			break;
		
		}
		return null;
	}
	
}
