import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Case {
	
	private final static int tablesN = 8;
	private final int size;
	private final int restrictionsN;
	private final HashSet<Restriction> restr = new HashSet<Restriction>();
	private final HashMap<Integer, Employee> allEmpl = new HashMap<Integer, Employee>();
	private final HashMap<Integer,Table> tables = new HashMap<Integer,Table>();
	
	
	Case(String line){
		line = line.trim();
		String[] lines = line.split("\n");
		this.size = Integer.parseInt(lines[0].trim());
		this.restrictionsN = Integer.parseInt(lines[1].trim());
		
		for(int i=1;i<=(size*tablesN);i++) {
			Employee e = new Employee(i);
			allEmpl.put(i, e);
		}
		
		for(int i=0;i<tablesN;i++) {
			tables.put(i, new Table(i,size));
		}
		
		for(int i= 0; i<this.restrictionsN;i++) {
			int restIndx = 2 + i;
			line = lines[restIndx];
			String[] subLines = line.split(" ");
			Employee emp1 = allEmpl.get(Integer.parseInt(subLines[0].trim()));
			Employee emp2 = allEmpl.get(Integer.parseInt(subLines[1].trim()));

			restr.add(new Restriction(emp1, emp2));
		}
	}
	
	public Table getTable(int t) {
		return tables.get(t);
	}
	
	public List<Employee> solve() {
		List<Employee> toBeSeated = new LinkedList<Employee>(allEmpl.values());

		toBeSeated = sitCompleteTables(toBeSeated);
		
		toBeSeated = sitRemaining(toBeSeated);
		
		if(!toBeSeated.isEmpty()) {
			reconfigureTables(toBeSeated);
		}

		return toBeSeated;
		
	}
	
	private void reconfigureTables(List<Employee> toBeSeated) {
		
		
		for(int currE=0;currE<toBeSeated.size();currE++) {
			Employee e = toBeSeated.get(0);
			int groupSize =e.getNeedTableSize();
			
			List<Table> freeSortedTables = getSortedTables();
	
			for(Table t : freeSortedTables) {
				List<Integer> groupSizes = getGroupSizes(t.getSortedEmployees());
				int needgroupSize = groupSize - t.getFreeSeats();
				if(needgroupSize == 0) {
					for(Employee add : e.getToSitTogether()) {
						add.sit(t);
						toBeSeated.remove(add);
					}
					return;
				}
				for(int curGroupSize = needgroupSize; curGroupSize<= groupSize; curGroupSize++) {
					int curNeedgroupSize = curGroupSize - t.getFreeSeats();

					List<Employee> optToRemove = t.containsGroupSizeMulti(curGroupSize);
					if(curNeedgroupSize == 0 || optToRemove.size() <=1) {
						//make not sense to change by the same...
						continue;
					}
					if(!optToRemove.isEmpty()) {
						for(Employee toRemove : optToRemove) {
							for(Employee rem : toRemove.getToSitTogether()) {
								rem.unsit(t);
								toBeSeated.add(rem);
							}
							for(Employee add : e.getToSitTogether()) {
								add.sit(t);
								toBeSeated.remove(add);
							}
							
						}
						reconfigureTables(toBeSeated);
						break;
					}
				}
			}
		}
	}

	private Employee getEmployeeForGroupSize(List<Employee> toBeSeated, int groupSize) {
		return toBeSeated.stream().filter(e -> e.getToSitTogether().size() == groupSize).findAny().get();
	}
	
	private HashMap<Table, List<Integer>> getGroupSizeForTables(List<Integer> groupSizes){
		
		HashMap<Table, List<Integer>> table2GroupSize = new HashMap<Table, List<Integer>>();
		for(Table t:tables.values()) {
			table2GroupSize.put(t, new ArrayList<Integer>());
		}
		return calculateGroupSizeForTables(table2GroupSize, groupSizes);
	}
	
	private HashMap<Table, List<Integer>> calculateGroupSizeForTables(HashMap<Table, List<Integer>> table2GroupSize, List<Integer> groupSizes){
		
		if(groupSizes.isEmpty()) {
			return table2GroupSize;
		}
		List<Integer> remainingGroupSizes = new ArrayList<Integer>(groupSizes);
		for(Integer groupSize : groupSizes) {
			for(Table t : tables.values()) {
				if(t.getFreeSeats() == 0) {
					continue;
				}
				List<Integer> groupSizesForTable = table2GroupSize.get(t);
				int ocupiedSeats = getOcupiedSeats(groupSizesForTable);
				if(t.getFreeSeats() >= ocupiedSeats + groupSize) {
					groupSizesForTable.add(groupSize);
					remainingGroupSizes.remove(groupSize);
					HashMap<Table, List<Integer>> table2GroupSizeReturned = calculateGroupSizeForTables(table2GroupSize, remainingGroupSizes);
					if(!table2GroupSizeReturned.isEmpty()) {
						return table2GroupSizeReturned;
					}
					remainingGroupSizes.add(groupSize);
					groupSizesForTable.remove(groupSize);
				}
			}
		}
		return new HashMap<Table, List<Integer>>();
		
	}
	
	private int getOcupiedSeats(List<Integer> groupSizesForTable) {
		return groupSizesForTable.stream().mapToInt(i -> i).sum();
	}
	
	private List<Integer> getGroupSizes(List<Employee> toBeSeated){
		List<Integer> groupSizes = new ArrayList<Integer>();
		
		for(Employee e: toBeSeated) {
			int groupSize = e.getNeedTableSize();
			groupSizes.add(groupSize);
		}
		return groupSizes;
	}
	
	public List<Employee> sitRemaining(List<Employee> toBeSeated) {
		
		toBeSeated = toBeSeated.stream().sorted((e1,e2) -> {
			return e2.getNeedTableSize() - e1.getNeedTableSize();
		}).collect(Collectors.toList());
		
		List<Table> freeSortedTables = getFreeSortedTables();
			
		emplFor : for(int i = 0; i <toBeSeated.size(); i++) {
			Employee e = toBeSeated.get(i);
			for(Table t : freeSortedTables) {
				if(t.getFreeSeats() >= e.getNeedTableSize()) {
					for(Employee toSit : e.getToSitTogether()) {
						toSit.sit(t);
						toBeSeated.remove(toSit);
					}
					//try to complete table
					int remainingSeats = t.getFreeSeats();
					Optional<Employee> emp = canCompleteTable(remainingSeats, toBeSeated);
					if(emp.isPresent()) {
						for(Employee toSit : emp.get().getToSitTogether()) {
							toSit.sit(t);
							toBeSeated.remove(toSit);
						}
					} 
					else if(freeSortedTables.size() > 1){
						//Need to find a combination that complete the table
						List<Employee> toAdd = getEmployeeToCompleteTable(toBeSeated, t);
						
							for(Employee toSit : toAdd) {
							toSit.sit(t);
							toBeSeated.remove(toSit);
						}
					}
					i = -1;
					freeSortedTables = getFreeSortedTables();
					continue emplFor;
				}
			}
		}
		
		return toBeSeated;
	}
	
	private List<Employee> getEmployeeToCompleteTable(List<Employee> toBeSeated, Table t){
		int remainingSeats = t.getFreeSeats();
		List<Employee> toReturn = new ArrayList<Employee>();
		
		return doGetEmployeeToCompleteTable(toReturn, toBeSeated, remainingSeats);
		
		
	}
	
	private List<Employee> doGetEmployeeToCompleteTable(List<Employee> toReturn, List<Employee> toBeSeated, int freeSeats){
		
		if(freeSeats == 0) {
			return toReturn;
		}

		
		List<Employee> toBeSeatedEven = toBeSeated.stream()
				.filter(e -> e.getNeedTableSize() %2 ==0)
				.sorted((e1,e2) -> {
					return e2.getNeedTableSize() - e1.getNeedTableSize();
				}).collect(Collectors.toList());
		List<Employee> toBeSeatedOdd = toBeSeated.stream()
				.filter(e -> e.getNeedTableSize() %2 ==0)
				.sorted((e1,e2) -> {
					return e2.getNeedTableSize() - e1.getNeedTableSize();
				}).collect(Collectors.toList());
//		toBeSeated = toBeSeated.stream().sorted((e1,e2) -> {
//			return e2.getNeedTableSize() - e1.getNeedTableSize();
//		}).collect(Collectors.toList());
		toBeSeated=toBeSeatedEven;
		toBeSeated.addAll(toBeSeatedOdd);
		
		HashSet<Integer> checkedSizes = new HashSet<Integer>();
		for(int i=0;i<toBeSeated.size();i++) {
			Employee emp = toBeSeated.get(i);
			int groupSize = emp.getToSitTogether().size();
			if(checkedSizes.contains(groupSize)) {
				continue;
			}
			checkedSizes.add(groupSize);
			if(groupSize <= freeSeats) {
				for(Employee toSit : emp.getToSitTogether()) {
					toReturn.add(0, toSit);
					toBeSeated.remove(toSit);
				}
				
				List<Employee> finalResult = doGetEmployeeToCompleteTable(toReturn, toBeSeated, freeSeats - groupSize);
				if(!finalResult.isEmpty()) {
					return finalResult;
				} 
				for(Employee toSit : emp.getToSitTogether()) {
					toReturn.remove(toSit);
					toBeSeated.add(0, toSit);
				}
			}	
		}
		return new ArrayList<Employee>();
	}
	
	private List<Table> getFreeSortedTables(){
		List<Table> freeSortedTables = tables.values()
				.stream()
				.filter(t -> t.getFreeSeats() >0)
				//.filter(t -> t.isEmpty())
				.sorted((t1,t2)->{
					return t1.getFreeSeats() - t2.getFreeSeats();
				}).collect(Collectors.toList());
		
		return freeSortedTables;

	}
	
	private List<Table> getSortedTables(){
		List<Table> freeSortedTables = tables.values()
				.stream()
				.sorted((t1,t2)->{
					return t2.getFreeSeats() - t1.getFreeSeats();
				}).collect(Collectors.toList());
		
		return freeSortedTables;

	}
	
	private Optional<Employee> canCompleteTable(int remainingSeats, List<Employee> toBeSeated) {
		Optional<Employee> emp = toBeSeated.stream()
				.filter(e -> e.getNeedTableSize() == remainingSeats)
				.findAny();
	
		return emp;
	}
	
	public List<Employee> sitCompleteTables(List<Employee> toBeSeated) {
		HashSet<Integer> sizesChecked = new HashSet<Integer>();
		
		emplFor : for(int i = 0; i <toBeSeated.size(); i++) {
			Employee e = toBeSeated.get(i);
			
			int sizeNeed = e.getNeedTableSize();
			if(sizesChecked.contains(sizeNeed)) {
				continue;
			}
			sizesChecked.add(sizeNeed);
			
			for(Table t:tables.values()) {
				if(t.getFreeSeats() == sizeNeed) {
					for(Employee toSit : e.getToSitTogether()) {
						toSit.sit(t);
						toBeSeated.remove(toSit);
					}
					i = -1;
					sizesChecked.clear();
					continue emplFor;
				}
			}
		}
		return toBeSeated;
	}
}
