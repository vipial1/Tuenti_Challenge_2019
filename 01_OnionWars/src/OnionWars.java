/****************************
 * Vicent Picornell
 * vicent.de.oliva@gmail.com
 * Challenge 1 - Onion wars
 * java + eclipse
 ****************************/
import java.util.Scanner;

public class OnionWars {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
        short cases; //short should be enough for c<=100
		cases = in.nextShort();
		
		 for(short caseN=0;caseN<cases;caseN++){
			int N = in.nextInt();
			int M = in.nextInt();
			
			int withOnion = (int) Math.ceil((float)M/2.0);
			int withoutOnion = (int) Math.ceil((float)N/2.0);

			 System.out.print("Case #"+(caseN+1)+": " + (withOnion+withoutOnion));
			 if(caseN < cases-1){
				 System.out.print("\n");
			 }
		  } 
		in.close();
	}

}
