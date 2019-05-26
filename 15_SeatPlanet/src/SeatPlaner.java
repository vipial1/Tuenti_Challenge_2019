
/****************************
 * Vicent Picornell
 * vicent.de.oliva@gmail.com
 * Challenge 15 - SeatPlaner
 * java + eclipse
 ****************************/

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class SeatPlaner {

	private final static int PORT = 1888;
	private final static String SERVER = "52.49.91.111";
		
	private static Case readChunk(BufferedReader dataFromServer){

		try {
			char[] message = new char[10240];
			int dataRead =0;
			StringBuilder line = new StringBuilder();
			do {
			dataFromServer.read(message);
			line.append(message);
			}while(dataRead == 10240);
			System.out.print(line.toString().trim());
			return new Case(line.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new Case("");
	}
	
	private static int readFirstChunk(BufferedReader dataFromServer){

		try {
			char[] message = new char[1024];
			dataFromServer.read(message);
			System.out.print(message);
			String line = new String(message);
			line = line.replace("\n", "").trim();
			return Integer.parseInt(line);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}


	public static void main(String[] args) {



		Socket socket = null;
		try {

			socket = new Socket(SERVER, PORT);

			InputStreamReader isr=new InputStreamReader(socket.getInputStream());
			BufferedReader dataFromServer = new BufferedReader(isr);
			PrintStream dataToServer=new PrintStream(socket.getOutputStream());
	
			dataToServer.println("SUBMIT");

			int cases = readFirstChunk(dataFromServer);
			for(int caseN = 0;caseN<cases;caseN++) {
				Case casei = readChunk(dataFromServer);
				List<Employee> toBeSeated = casei.solve();
				
				if(!toBeSeated.isEmpty()) {
					System.out.println("NOT EMPTY");
					for(Employee e:toBeSeated) {
						System.out.println(e);
					}
				}

				System.out.println("Table 1 = size="+casei.getTable(0).getSortedEmployees().size()+"= " + casei.getTable(0));
				System.out.println("Table 2 = size="+casei.getTable(1).getSortedEmployees().size()+"= " + casei.getTable(1));
				System.out.println("Table 3 = size="+casei.getTable(2).getSortedEmployees().size()+"=" + casei.getTable(2));
				System.out.println("Table 4 = size="+casei.getTable(3).getSortedEmployees().size()+"=" + casei.getTable(3));
				System.out.println("Table 5 = size="+casei.getTable(4).getSortedEmployees().size()+"=" + casei.getTable(4));
				System.out.println("Table 6 = size="+casei.getTable(5).getSortedEmployees().size()+"=" + casei.getTable(5));
				System.out.println("Table 7 = size="+casei.getTable(6).getSortedEmployees().size()+"=" + casei.getTable(6));
				System.out.println("Table 8 = size="+casei.getTable(7).getSortedEmployees().size()+"=" + casei.getTable(7));
				dataToServer.println(casei.getTable(0));
				dataToServer.println(casei.getTable(1));
				dataToServer.println(casei.getTable(2));
				dataToServer.println(casei.getTable(3));
				dataToServer.println(casei.getTable(4));
				dataToServer.println(casei.getTable(5));
				dataToServer.println(casei.getTable(6));
				dataToServer.println(casei.getTable(7));
			}

			readChunk(dataFromServer);

			
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{		
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	
}
