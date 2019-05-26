import java.awt.Point;
import java.util.HashMap;

public class KeyboardLayout {

	private static char[][] layout = new char[][] {
		new char[] {'1','2','3','4','5','6','7','8','9','0'},
		new char[] {'Q','W','E','R','T','Y','U','I','O','P'},
		new char[] {'A','S','D','F','G','H','J','K','L',';'},
		new char[] {'Z','X','C','V','B','N','M',',','.','-'}
	};
	
	private final static int layoutX = layout[0].length;
	private final static int layoutY = layout.length;
	
	private static HashMap<Character, Point> char2Point = new HashMap<Character, Point>();
	
	static {
		for(int y=0; y<layout.length;y++) {
			for(int x=0;x<layout[y].length;x++) {
				char2Point.put(layout[y][x], new Point(x, y));
			}
		}
	}
	
	public Offset getDistanceBetweenKeys(char sourceK, char destK) {
		Point source = char2Point.get(sourceK);
		Point dest = char2Point.get(destK);
		
		int offsetX = (((int)(dest.getX() - source.getX())) + layoutX) % layoutX;
		int offsetY = (((int)(dest.getY() - source.getY())) + layoutY) % layoutY;

		return new Offset(offsetX, offsetY);
	}
	
	public char shift(Offset offset, char sourceK) {
		
		Point source = char2Point.get(sourceK);

		int destX = ((int) (source.getX() + offset.getOffsetX())+layoutX)%layoutX;
		int destY = ((int) (source.getY() + offset.getOffsetY())+layoutY)%layoutY;

		return layout[destY][destX];
	}
	
}
