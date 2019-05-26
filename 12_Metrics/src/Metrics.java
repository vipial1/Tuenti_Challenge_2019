
/****************************
 * Vicent Picornell
 * vicent.de.oliva@gmail.com
 * Challenge 12 - Metrics
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
import java.util.Scanner;

public class Metrics {

	private final static int PORT = 2019;
	private final static String SERVER = "52.49.91.111";
	private final static String query = "select main.user_id user_id, DATE_FORMAT(min(main.date_time),'%Y-%m-%d %H:%i:%S') session_from, DATE_FORMAT(max(main.date_time),'%Y-%m-%d %H:%i:%S') session_to, TIMESTAMPDIFF(SECOND, min(main.date_time),max(main.date_time)) seconds, count(main.action) num_actions from (select main.date_time, main.user_id, main.action, CASE WHEN lastClose >= lastOpen AND lastClose >= minUser THEN lastClose WHEN lastOpen >= lastClose AND lastOpen >= minUser THEN lastOpen WHEN minUser >= lastClose AND minUser >= lastOpen THEN minUser ELSE lastClose END AS sessionOpened from(select outerAct.*, (select IFNULL(min(innerAct.date_time), 0) from activity innerAct where innerAct.user_id = outerAct.user_id and innerAct.date_time > (select max(sub.date_time) t from activity sub where sub.user_id = innerAct.user_id and sub.date_time < outerAct.date_time and sub.action = 'close')) lastClose, (select IFNULL(min(innerAct.date_time),0) from activity innerAct where innerAct.user_id = outerAct.user_id and innerAct.date_time >= (select max(sub.date_time) t from activity sub where sub.user_id = innerAct.user_id and sub.date_time <= outerAct.date_time and sub.action = 'open')) lastOpen, (select IFNULL(min(innerAct.date_time),0) from activity innerAct where innerAct.user_id = outerAct.user_id) minUser from activity outerAct order by outerAct.date_time ) main ) main group by main.user_id, main.sessionOpened order by 1, 2;";
		
	private static boolean readChunk(BufferedReader dataFromServer){

		try {
			char[] message = new char[1024];
			int dataRead=2;
			while(dataRead>0 && dataRead != -1){
				dataRead = dataFromServer.read(message);
				System.out.print(message);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;

	}


	public static void main(String[] args) {



		Socket socket = null;
		try {

			socket = new Socket(SERVER, PORT);

			InputStreamReader isr=new InputStreamReader(socket.getInputStream());
			BufferedReader dataFromServer = new BufferedReader(isr);
			PrintStream dataToServer=new PrintStream(socket.getOutputStream());

	
			dataToServer.println(query);

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
