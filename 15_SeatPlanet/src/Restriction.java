
public class Restriction {

	private final Employee empl1, empl2;
	
	Restriction(Employee empl1, Employee empl2){
		this.empl1 = empl1;
		this.empl2 = empl2;
		
		this.empl1.addRestr(this);
		this.empl2.addRestr(this);
	}
	
	

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Restriction [empl1=");
		builder.append(empl1);
		builder.append(", empl2=");
		builder.append(empl2);
		builder.append("]");
		return builder.toString();
	}

	public Employee getEmpl1() {
		return empl1;
	}

	public Employee getEmpl2() {
		return empl2;
	}
}
