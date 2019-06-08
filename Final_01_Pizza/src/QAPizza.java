/****************************
 * Vicent Picornell
 * vicent.de.oliva@gmail.com
 * Challenge Final 1 - Pizza
 * java + eclipse
 ****************************/
import java.util.Scanner;

public class QAPizza {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
        short cases; //short should be enough for c<=100
		cases = in.nextShort();
		
		 for(short caseN=0;caseN<cases;caseN++){
			int S = in.nextInt();
			float A = in.nextFloat();
			int N = in.nextInt();

			float totalSlicesToBeEaten = S * A;
			
			int pizzas = (int) Math.ceil(totalSlicesToBeEaten/N);

			 System.out.print("Case #"+(caseN+1)+": " + (pizzas));
			 if(caseN < cases-1){
				 System.out.print("\n");
			 }
		  } 
		in.close();
	}

}
