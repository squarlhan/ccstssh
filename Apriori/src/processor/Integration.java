package processor;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Integration{
	
	public void integ(List<String> addrs, String aimaddr){
		try {
			File re = new File(aimaddr);
			if (re.exists()) {
				re.delete();
				if (re.createNewFile()) {
					System.out.println("result file create success!");
				} else {
					System.out.println("result file create failed!");
				}
			} else {
				if (re.createNewFile()) {
					System.out.println("result file create success!");
				} else {
					System.out.println("result file create failed!");
				}

			}

			BufferedWriter output = new BufferedWriter(new FileWriter(re));
			
			for(String addr:addrs){
				File file = new File(addr);
				InputStreamReader insr = new InputStreamReader(new FileInputStream(file), "gb2312");
				BufferedReader br = new BufferedReader(insr);
				String line;
				while ((line = br.readLine()) != null) {
					line = line.trim();				
					if (line.trim().length() >= 1) 
					{
						output.write(line.substring(0, line.length()-1)+"\n");
					   }
					}
				br.close();
				insr.close();
			}
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args){
		Integration inte = new Integration();
		String prefix = "E:/supercoli/aa/";
		String midfix = "2421/";
		String aimaddr = prefix+"42.txt";
		List<String> addrs = new ArrayList();
		for(int i =1;i<=42;i++){
			String temp = prefix+midfix+String.valueOf(i)+".tab";
			addrs.add(temp);
		}
		inte.integ(addrs, aimaddr);
	}
}