package cn.edu.jlu.ccst.opc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Runme {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int timespan = 20;
		FileReader read;
		try {
			read = new FileReader("opcConfig.ini");
			BufferedReader br = new BufferedReader(read);
			String row;
			while ((row = br.readLine()) != null) {
				if(row.trim().startsWith("timespan")){
					String[] hs = row.trim().split("=");
					timespan = Integer.parseInt(hs[1].trim());
				}
			}
			br.close();
			System.out.println(timespan);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Timer timer = new Timer();
        timer.schedule(new TimerTask(){
        	public void run() {
        		try {
        			System.out.println("********");
					Runtime.getRuntime().exec("java -jar opcread.jar");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        }, 1000, timespan*1000);
	}
}
