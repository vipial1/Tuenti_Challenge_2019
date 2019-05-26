import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
        short cases; //short should be enough for c<=100
		cases = in.nextShort();
		
		 for(short caseN=0;caseN<cases;caseN++){
			int lines = in.nextInt();
			in.nextLine();
			
			//Read original document
			StringBuilder line = new StringBuilder();
			for(int lineN = 0; lineN < lines; lineN++) {
				line.append(in.nextLine());
			}
			Document original = new Document(line.toString());
			//Document doc1 = new Document("Subject: Boat;From: Charlie;To: Desmond;", "Not Penny's boat");
			
			lines = in.nextInt();
			in.nextLine();
			//Read modified document
			line = new StringBuilder();
			for(int lineN = 0; lineN < lines; lineN++) {
				line.append(in.nextLine());
			}
			Document modified = new Document(line.toString());
			//Document doc2 = new Document("Subject: Boat;From: Charlie;To: Desmond;", "Penny's boat :)");

			HashHelper.calculatePrintLenght(original, modified);
			
			HashHelper.calculatePrint(original, modified);
			
			 System.out.print("Case #"+(caseN+1)+": " +modified.getPrint());
			 
			 if(caseN < cases-1){
				 System.out.print("\n");
			 }
		  } 
		in.close();
	}

}
