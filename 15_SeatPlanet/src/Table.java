import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;

public class Table {
	private final int id;
	private final int size;
	private final HashSet<Employee> sitEmployees = new HashSet<Employee>();
	private final HashSet<Restriction> restrictions = new HashSet<Restriction>();
	private final LinkedList<Employee> sortedEmployees;
	
	Table(int id, int size){
		this.id = id;
		this.size = size;
		this.sortedEmployees = new LinkedList<Employee>();
	}
	
	public int getFreeSeats() {
		return size - sitEmployees.size();
	}
	
	public void unsit(Employee e) {
		sitEmployees.remove(e);
		restrictions.removeAll(e.getAllRestrictions());

	}
	
	public List<Employee> containsGroupSizeMulti(int groupSize) {
		 List<Employee> toRemove = containsGroupSize(groupSize);
		
		if(!toRemove.isEmpty()) {
			return toRemove;
		}
		for(int i=1;i< (groupSize/2)+1;i++) {
			int size1 = i;
			int size2= groupSize -i;
			 List<Employee> toRemove1 = containsGroupSize(size1);
			 List<Employee> toRemove2 = containsGroupSizeNotIn(toRemove1,size2);
			 if(!toRemove1.isEmpty() && !toRemove2.isEmpty()) {
				 toRemove1.addAll(toRemove2);
				 return toRemove1;
			 }
		}
		return toRemove;
		
	}
	
	private List<Employee> containsGroupSizeNotIn(List<Employee> notIN, int groupSize) {
		Optional<Employee> toRemove = sitEmployees.stream()
				.filter(e -> !notIN.contains(e))
				.filter(e -> e.getNeedTableSize() == groupSize)
				.findAny();
		
		if(toRemove.isPresent()) {
			List<Employee> toReturn = new ArrayList<Employee>();
			toReturn.add(toRemove.get());
			return toReturn;
		}
		return Collections.EMPTY_LIST;
		
	}
	
	private List<Employee> containsGroupSize(int groupSize) {
		Optional<Employee> toRemove = sitEmployees.stream().filter(e -> e.getNeedTableSize() == groupSize).findAny();
		
		if(toRemove.isPresent()) {
			return Collections.singletonList(toRemove.get());
		}
		return Collections.EMPTY_LIST;
		
	}
	
	public boolean isEmpty() {
		return sitEmployees.isEmpty();
	}

	public void addEmployee(Employee employee) {
		sitEmployees.add(employee);
		restrictions.addAll(employee.getAllRestrictions());
		
//		if(getFreeSeats() == 0) {
//			for(Employee e : sitEmployees) {
//				sortedEmployees.addLast(e);
//			}
//			sortTable();
//		}
	}
	
	public LinkedList<Employee> getSortedEmployees(){
		if(sortedEmployees.isEmpty()) {
			for(Employee e : sitEmployees) {
				sortedEmployees.addLast(e);
			}
			sortTable();
		}
		return this.sortedEmployees;
	}
	
	private boolean sortTable() {
		for(Restriction r : restrictions) {
			int indx1 = getIndexForEmployee(r.getEmpl1());
			int indx2 = getIndexForEmployee(r.getEmpl2());
			if(indx1 != (indx2-1) && !(indx1 == sortedEmployees.size()-1 && indx2 == 0)) {
				int correctIndx2 = (indx1 +1)%sortedEmployees.size();
				sortedEmployees.remove(r.getEmpl2());
				sortedEmployees.add(correctIndx2, r.getEmpl2());
				return sortTable();
			}
		}
		return false;
	}
	
	private int getIndexForEmployee(Employee e) {
		for(int i=0;i<sortedEmployees.size();i++) {
			if(sortedEmployees.get(i).equals(e)) {
				return i;
			}
		}
		return -1;
	}
	
	private Employee getEmployeeOnIndex(int indx) {
		
		return sortedEmployees.get(indx);
	}

	@Override
	public String toString() {
		
		
		try {
			sortedEmployees.clear();
			for(Employee e : sitEmployees) {
				sortedEmployees.addLast(e);
			}
			sortTable();
		}catch(Exception e) {
			System.out.println("STOP");
		}
		
		StringBuilder builder = new StringBuilder();
		for(int i=0; i<sortedEmployees.size();i++) {
			builder.append(getEmployeeOnIndex(i));
			if(i<sortedEmployees.size()-1) {
				builder.append(",");
			}
		}
		return builder.toString();
	}
	
}
