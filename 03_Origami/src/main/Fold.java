package main;

public enum Fold {
	LEFT,
	RIGHT,
	TOP,
	BOTTOM;
	
	public static Fold createFold(String type) {
		switch(type) {
		case "L":
			return LEFT;
		case "R":
			return RIGHT;
		case "T":
			return TOP;
		case "B":
			return BOTTOM;
		}
		return null;
	}
}
