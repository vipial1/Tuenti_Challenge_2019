import java.util.Scanner;

public class Forbidden {
	public static void main(String[] args) {
		
		Scanner in = new Scanner(System.in);
        short cases; //short should be enough for c<=100
		KeyboardLayout layout = new KeyboardLayout();

		cases = in.nextShort();
		in.nextLine();
		
		 for(short caseN=0;caseN<cases;caseN++){
			char SenderChar = in.nextLine().charAt(0);
			Sender sender = Sender.getSender(SenderChar);
			Message message = new Message(sender, in.nextLine());
			
			Offset offset = layout.getDistanceBetweenKeys(message.getLastChar(), message.getSender().getChar());

			 System.out.print("Case #"+(caseN+1)+": " + message.decrypt(offset, layout));
			 
			 if(caseN < cases-1){
				 System.out.print("\n");
			 }
			 
			 
		  } 
		in.close();
	}
}
