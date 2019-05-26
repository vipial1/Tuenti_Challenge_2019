import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Keeper {
	
	private static final String filename = "a.j";

	public static void main(String[] args) {
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File(filename));

			while(scanner.hasNextLine()) {
				String line = scanner.nextLine();
				
				if(line.contains("bipush")) {
					int i= line.indexOf("bipush");
					if(!line.substring(i+7).startsWith("-")) {
					int n = Integer.parseInt(line.substring(i+7).replace(" ", ""));
					if(n > 0) {
						System.out.print((char)n);
					}
					}
				}
			}
			
		} catch (FileNotFoundException e) {
			//Hope never to get this...
			System.out.println("Hey Tuenti! please, put "+ filename + " in the shell current directory. Thanks!");
			e.printStackTrace();  
		}
		finally{
			if(scanner != null) scanner.close();
		}
		
	}

}
