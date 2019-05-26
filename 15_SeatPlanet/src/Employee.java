import java.util.HashSet;

public class Employee {
	private int id;
	private HashSet<Restriction> restr = new HashSet<Restriction>();
	private HashSet<Employee> toSitTogether = new HashSet<Employee>();
	private Table table;

	public Employee(int i) {
		this.id = i;
		toSitTogether.add(this);
	}
	
	public int getNeedTableSize() {
		return toSitTogether.size();
	}
	
	public HashSet<Employee> getToSitTogether(){
		return this.toSitTogether;
	}
	
	public void addRestr(Restriction r) {
		restr.add(r);
		toSitTogether.add(r.getEmpl1());
		toSitTogether.add(r.getEmpl2());
		
		Employee notMe = r.getEmpl1().equals(this) ? r.getEmpl2() : r.getEmpl1();

		toSitTogether.addAll(notMe.toSitTogether);
		
		for(Employee c: toSitTogether) {
			if(!c.equals(this)) {
				c.addRestr(toSitTogether);
			}
		}
		
	}
	
	private boolean addRestr(HashSet<Employee> e) {
		return this.toSitTogether.addAll(e);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Employee other = (Employee) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public void sit(Table t) {
		this.table = t;
		t.addEmployee(this);
	}
	
	public void unsit(Table t) {
		this.table = null;
		t.unsit(this);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(id);
		return builder.toString();
	}

	public HashSet<Restriction> getAllRestrictions() {
		return this.restr;
	}

}
