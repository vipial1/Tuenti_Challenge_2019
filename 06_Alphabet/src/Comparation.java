
public class Comparation {


	private final char bigger;
	private final char smaller;
	
	public Comparation(char bigger, char smaller) {
		this.bigger = bigger;
		this.smaller = smaller;
	}

	public char getBigger() {
		return bigger;
	}

	public char getSmaller() {
		return smaller;
	}
	
	@Override
	public String toString() {
		return bigger + ">" + smaller;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + bigger;
		result = prime * result + smaller;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Comparation other = (Comparation) obj;
		if (bigger != other.bigger)
			return false;
		if (smaller != other.smaller)
			return false;
		return true;
	}
}
