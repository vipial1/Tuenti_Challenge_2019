import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.IntStream;

public class Character {
	
	private final String name;
	private final int level;
	private final int cost;
	private final HashSet<String> skills = new HashSet<String>();
	private HashSet<Fusion> fusionIAmResult = new HashSet<Fusion>();
	private HashSet<Fusion> fusionIAmChar = new HashSet<Fusion>();
	private int fusionedTimes = 0;

	public Character(String characterLine) {
		this(characterLine.split(Main.SEPARATOR)[0],
			characterLine.split(Main.SEPARATOR)[1],
			characterLine.split(Main.SEPARATOR)[2],
			IntStream.range(4, 4+ Integer.parseInt(characterLine.split(Main.SEPARATOR)[3]))
			.mapToObj(i -> characterLine.split(Main.SEPARATOR)[i])
			.toArray(String[]::new));
	}

	
	public Character(String name, String level, String cost, String[] skills) {
		this.name = name;
		this.level = Integer.parseInt(level);
		this.cost = Integer.parseInt(cost);
		this.skills.addAll(Arrays.asList(skills));
		
	}
	
	public void addFusion(Fusion fusion) {
		if(fusion.getResult().equals(this)) {
			this.fusionIAmResult.add(fusion);
		} else {
			this.fusionIAmChar.add(fusion);
		}
	}
	
	public final HashSet<String> getSkills(){
		return this.skills;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Character other = (Character) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	public HashSet<Fusion> getPosibleFusionsAsResult() {
		if(this.fusionedTimes < 100000) {
			this.fusionedTimes++;
			return this.fusionIAmResult;
		}
		return new HashSet<Fusion>();
	}
	
	public int getCost() {
		return this.cost;
	}


	public String getName() {
		return name;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(name);
		return builder.toString();
	}


	public void reset() {
		this.fusionedTimes = 0;
	}
}
