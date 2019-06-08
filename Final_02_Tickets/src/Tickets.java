/****************************
 * Vicent Picornell
 * vicent.de.oliva@gmail.com
 * Challenge Final 2 - Tickets
 * java + eclipse
 ****************************/
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

public class Tickets {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
        short cases; //short should be enough for c<=100
		cases = in.nextShort();
		
		
		 for(short caseN=0;caseN<cases;caseN++){
			int People = in.nextInt();
			String strCost = in.next();
			BigInteger eachCost = new BigInteger(strCost);
			BigInteger totalCost = eachCost.multiply(BigInteger.valueOf(People));
			int N = in.nextInt();
			
			SortedSet<Integer> values = new TreeSet<Integer>();
			
			for(int i=0;i<N;i++) {
				int V = in.nextInt();
				values.add(V);
			}
			
			List<Integer> sorted = new LinkedList<Integer>(values);
			Collections.sort(sorted, Collections.reverseOrder());
			
			SortedSet<Integer> reversed = new TreeSet<Integer>(sorted);
			
			
//			for(int val : values) {
//				BigInteger value = BigInteger.valueOf(val);
//				
//				//value > remainToPay
//				if(remainToPay.subtract(value).signum() < 0) {
//					continue;
//				}
//				
//				BigInteger[] resultAndRemainder = remainToPay.divideAndRemainder(value);
//				tickets = tickets.add(resultAndRemainder[0]);
//				BigInteger paid = value.multiply(resultAndRemainder[0]);
//				remainToPay = remainToPay.subtract(paid);
//					
//			}
			
			BigInteger needTickets = calculateMinTickets(totalCost, reversed);

			

			 System.out.print("Case #"+(caseN+1)+": " + (needTickets));
			 if(caseN < cases-1){
				 System.out.print("\n");
			 }
		  } 
		in.close();
	}
	
	public static BigInteger calculateMinTickets(BigInteger originalCost, SortedSet<Integer> values) {
		
		BigInteger minTickets = originalCost;
		
		if(values.size() == 1) {
			
			BigInteger value = BigInteger.valueOf(values.first());
			BigInteger[] ticketsAndRem = originalCost.divideAndRemainder(value);
			
			if(ticketsAndRem[1].equals(BigInteger.ZERO)) {
				return ticketsAndRem[0];
			}

			return originalCost;
		}
		
		
		for(int iValue : values) {
			BigInteger value = BigInteger.valueOf(iValue);
			
			//max tickets of this value
			BigInteger maxTickets = originalCost.divide(value);
			
			BigInteger counter = BigInteger.ZERO;
			
			SortedSet<Integer> newValues = new TreeSet<Integer>(values);
			newValues.remove(new Integer(iValue));
			
			while(counter.subtract(maxTickets).signum() <= 0) {
				
				BigInteger ticketsOfThisValue = counter;
				
				if(ticketsOfThisValue.subtract(minTickets).signum() >= 0) {
					break;
				}
				
				BigInteger paid = counter.multiply(value);
				BigInteger toPaid = originalCost.subtract(paid);
				
				BigInteger remainingTickets = calculateMinTickets(toPaid, newValues);
				
				BigInteger totalTickets = ticketsOfThisValue.add(remainingTickets);
				
				//minTickets > totalTickets
				if(totalTickets.subtract(minTickets).signum() < 0) {
					minTickets = totalTickets;
				}
				counter = counter.add(BigInteger.ONE);
			}
			
			
		}

		return minTickets;
	}

}
