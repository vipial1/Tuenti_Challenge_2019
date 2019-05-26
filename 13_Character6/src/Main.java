import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Scanner;

public class Main {
	
	public final static String SEPARATOR = " ";

	public final static String FILENAME = "almanac.data";
	public final static Almanac almanac = new Almanac(FILENAME);

	public static void main(String[] args) {
		
		
		Scanner in = new Scanner(System.in);
        short cases; //short should be enough for c<=100
		cases = in.nextShort();
		in.nextLine();
		 for(short caseN=0;caseN<cases;caseN++){
			String charLine = in.nextLine();
			
			Case cur_case = new Case(charLine);
			cur_case.solve();
			
			 System.out.print("Case #"+(caseN+1)+": " + cur_case.getSolution());
			 if(caseN < cases-1){
				 System.out.print("\n");
			 }
		  } 
		in.close();
	}

}
