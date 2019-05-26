import java.util.HashMap;
import java.util.Scanner;

public class Candies {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
        short cases; //short should be enough for c<=100
		cases = in.nextShort();
		
		 for(short caseN=0;caseN<cases;caseN++){
			 try {
			short N = in.nextShort();
			HashMap<Integer, Integer> entry2ocurrences = new HashMap<Integer, Integer>();
			HashMap<Integer, EnteroEnorme> entry2factor = new HashMap<Integer, EnteroEnorme>();

			for(short entryN =0; entryN<N; entryN++) {
				int entry = in.nextInt();
				
				int occurrence = 0;
				
				if(entry2ocurrences.containsKey(entry)) {
					occurrence = entry2ocurrences.get(entry);
				}
				entry2ocurrences.put(entry, ++occurrence);
				EnteroEnorme eeEntry = new EnteroEnorme(entry);
				EnteroEnorme eeOcurrency = new EnteroEnorme(occurrence);

				entry2factor.put(entry, EnteroEnorme.Divide(MathHelper.mcm(eeEntry, eeOcurrency), eeOcurrency));
				
			}
			EnteroEnorme factor = new EnteroEnorme(1);
			for(EnteroEnorme f : entry2factor.values()) {
				factor = MathHelper.mcm(factor, f);
			}
			
			
			EnteroEnorme totalPersons = new EnteroEnorme(0);
			EnteroEnorme candies = new EnteroEnorme(0);
			
			for(int entry : entry2ocurrences.keySet()) {
				EnteroEnorme occurrence = EnteroEnorme.Multiply(new EnteroEnorme(entry2ocurrences.get(entry)), factor);
				EnteroEnorme candyPersons = EnteroEnorme.Divide(occurrence, new EnteroEnorme(entry));
				totalPersons = EnteroEnorme.Add(totalPersons, candyPersons);
				candies = EnteroEnorme.Add(candies, EnteroEnorme.Multiply(new EnteroEnorme(entry), candyPersons));
				
			}

			EnteroEnorme mcd = MathHelper.mcd(candies, totalPersons);
			
			if(EnteroEnorme.SmallerOrEqualThan(mcd, candies) && EnteroEnorme.SmallerOrEqualThan(mcd, totalPersons)) {
				candies = EnteroEnorme.Divide(candies, mcd);
				totalPersons = EnteroEnorme.Divide(totalPersons, mcd);
			}

			 System.out.print("Case #"+(caseN+1)+": " + candies + "/" + totalPersons);
			 if(caseN < cases-1){
				 System.out.print("\n");
			 }
			 }catch(Exception e) {
				 System.out.println("EXCEPTION");
			 }
		  } 
		in.close();
	}
}
