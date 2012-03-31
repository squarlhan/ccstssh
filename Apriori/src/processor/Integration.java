package processor;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
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
	
	public List<String> getSeqFile(String addr) {
		List<String> result = new ArrayList();
		File file = new File(addr);
		InputStreamReader insr;
		try {
			insr = new InputStreamReader(new FileInputStream(file), "gb2312");

			BufferedReader br = new BufferedReader(insr);
			String line;
			while ((line = br.readLine()) != null) {
				line = line.trim();
				if (line.length() >= 1) {
					result.add(line);
				}
			}
			br.close();
			insr.close();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public void parsefile(List<String> myseq, String inaddr, String outaddr){
		List<Integer> indexes = new ArrayList();
		try {
			File file = new File(inaddr);
			InputStreamReader insr = new InputStreamReader(new FileInputStream(file), "gb2312");
			BufferedReader br = new BufferedReader(insr);
			String line;
			while ((line = br.readLine()) != null) {
				line = line.trim();				
				if (line.trim().length() >= 1) {
					indexes.add(Integer.parseInt(line.trim()));
				}
			}
			br.close();
			insr.close();
			
			List<List<String>> result = new ArrayList();
			List<String> sub= new ArrayList();
			for(int i=0;i<=indexes.size()-1;i++){
				if(indexes.get(i)==1){
					if(sub.size()>=1){
						result.add(sub);
					}
					sub = new ArrayList();
					sub.add(myseq.get(i));
				}else{
					sub.add(myseq.get(i));
				}
			}
			if(indexes.get(0)!=1){
				result.get(0).addAll(result.get(result.size()-1));
				result.remove(result.size()-1);
			}
			
			File re = new File(outaddr);
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
			for(List<String> sublist: result){
				for(String item:sublist){
					output.write(item+",");
				}
				output.write("\n");
				output.flush();
			}
			output.close();
		}catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	public static void main(String[] args){
		Integration inte = new Integration();
		String prefix = "E:/supercoli/aa/";
		String midfix = "2549/";
		String aimaddr = prefix+"2549_50.txt";
		List<String> addrs = new ArrayList();
		for(int i =1;i<=50;i++){
			String temp = prefix+midfix+String.valueOf(i)+".tab";
			addrs.add(temp);
		}
		inte.integ(addrs, aimaddr);
		
//		prefix = "E:/supercoli/exe1/rs/";
//		List<String> myseq = inte.getSeqFile("seq2549.txt");
//		for(int i=58;i<=64;i++){
//			inte.parsefile(myseq, prefix+String.valueOf(i)+".txt", prefix+String.valueOf(i)+".tab");
//		}
	}
}