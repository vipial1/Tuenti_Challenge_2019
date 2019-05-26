/****************************
 * Vicent Picornell
 * vicent.de.oliva@gmail.com
 * Challenge 3 - Origami
 * java + eclipse
 ****************************/
package main;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import folders.Folder;
import folders.FolderFactory;
import folders.PairOfPunches;

public class Origami {
	
	private final static String PLANET_SEPARATOR = ":";
	private final static String DESTINATION_SEPARATOR = ",";

	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
        short cases; //short should be enough for c<=100
		cases = in.nextShort();
		
		 for(short caseN=0;caseN<cases;caseN++){
			int W = in.nextInt();
			int H = in.nextInt();
			short F = in.nextShort();
			short P = in.nextShort();
			
			in.nextLine();
			
			LinkedList<Fold> folds = new LinkedList<Fold>();
			LinkedList<Punch> punches = new LinkedList<Punch>();

			for(short foldN = 0;foldN<F; foldN++) {
				String f = in.nextLine();
				folds.add(Fold.createFold(f));
			}
			
			for(short punchN = 0;punchN<P; punchN++) {
				int pX = in.nextInt();
				int pY = in.nextInt();
				in.nextLine();
				punches.add(new Punch(pX, pY));
			}
			
			for(Fold fold : folds) {
				LinkedList<Punch> newPunches = new LinkedList<Punch>();
				Folder folder = FolderFactory.createFolder(fold);
				for(Punch punch : punches) {
					PairOfPunches newPair = folder.unfoldPunch(punch, W, H);
					newPunches.add(newPair.getA());
					newPunches.add(newPair.getB());
				}
				if(fold == Fold.TOP || fold == Fold.BOTTOM) {
					H *= 2;
				} else if(fold == Fold.LEFT || fold == Fold.RIGHT) {
					W *= 2;
				}
				punches = newPunches;
			}

			 System.out.println("Case #"+(caseN+1)+":");
			 
			 int punchN = 0;
			 Collections.sort(punches, new PunchComparator());
			 for(Punch punch : punches) {
				 System.out.print(punch.getX() + " " + punch.getY());
				 
				 if(caseN < cases-1 || ++punchN < punches.size()){
					 System.out.print("\n");
				 }
			 }
			 
			 
		  } 
		in.close();
	}
	


}
