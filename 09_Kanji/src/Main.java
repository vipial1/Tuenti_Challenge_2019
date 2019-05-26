import java.util.Scanner;

public class Main {
	

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
        short cases; //short should be enough for c<=100
		cases = in.nextShort();
		in.nextLine();
		
		 for(int lineN=0;lineN<cases;lineN++){
			
			 String line = in.nextLine();
			 Operation operation = new Operation(line);
			 
			 operation.validate();
				
			 System.out.print("Case #"+(lineN+1)+": " + operation);
			 if(lineN < cases-1){
				 System.out.print("\n");
			 }
		  } 
		in.close();
	}

}
