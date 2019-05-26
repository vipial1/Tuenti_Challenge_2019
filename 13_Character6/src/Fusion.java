
public class Fusion {
	
	private final Character result, char1, char2;
	
	public Fusion(Character result, Character char1, Character char2) {
		this.result = result;
		this.char1 = char1;
		this.char2 = char2;
		
		this.result.addFusion(this);
		this.char1.addFusion(this);
		this.char2.addFusion(this);
	}

	public Character getResult() {
		return result;
	}
	
	public Character getChar1() {
		return char1;
	}
	
	public Character getChar2() {
		return char2;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((char1 == null) ? 0 : char1.hashCode());
		result = prime * result + ((char2 == null) ? 0 : char2.hashCode());
		result = prime * result + ((this.result == null) ? 0 : this.result.hashCode());
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
		Fusion other = (Fusion) obj;
		if (char1 == null) {
			if (other.char1 != null)
				return false;
		} else if (!char1.equals(other.char1))
			return false;
		if (char2 == null) {
			if (other.char2 != null)
				return false;
		} else if (!char2.equals(other.char2))
			return false;
		if (result == null) {
			if (other.result != null)
				return false;
		} else if (!result.equals(other.result))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(result);
		builder.append("=");
		builder.append(char1);
		builder.append("+");
		builder.append(char2);
		return builder.toString();
	}
}
