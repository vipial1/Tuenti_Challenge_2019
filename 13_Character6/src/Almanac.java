import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;

public class Almanac {
	
	private final HashMap<String, Character> characters = new HashMap<String, Character>();
	private final HashSet<Fusion> fusions = new HashSet<Fusion>();
	
	public Almanac(String filename) {
		read(filename);
	}
	
	private HashMap<String, Character> read(String filename){
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File(filename));

			int C = scanner.nextInt();
			int F = scanner.nextInt();
			
			scanner.nextLine();
			
			for(int c=0;c<C;c++) {
				String characterLine = scanner.nextLine();
				Character character = new Character(characterLine);
				characters.put(character.getName(), character);
			}
			for(int f=0;f<F;f++) {
				String fusionLine = scanner.nextLine();
				String[] chars = fusionLine.split(Main.SEPARATOR);
				Character result = characters.get(chars[0]);
				Character char1 = characters.get(chars[1]);
				Character char2 = characters.get(chars[2]);

				Fusion fusion = new Fusion(result, char1, char2);
				fusions.add(fusion);
			}
			
			
		} catch (FileNotFoundException e) {
			//Hope never to get this...
			System.out.println("Hey Tuenti! please, put "+ filename + " in the shell current directory. Thanks!");
			e.printStackTrace();  
		}
		finally{
			if(scanner != null) scanner.close();
		}
		
		return this.characters;
	}
	
	public Character getCharacter(String character) {
		return characters.get(character);
	}

	public void resetAll() {
		characters.values().forEach(c -> c.reset());
	}
}
