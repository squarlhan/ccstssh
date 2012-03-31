package processor;

import java.text.DecimalFormat;
import java.util.*;
import java.util.Map.Entry;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

import apriori.Apriori_generateFreItemSet;
import apriori.Apriori_generateRule;
import apriori.FormatInput;
import apriori.Rule;

public class Test {
	
	public ArrayList gettestdata(boolean test){
		ArrayList inputDB = new ArrayList();
		if (test) {
			ArrayList t1 = new ArrayList();
			t1.add("1");
			t1.add("2");
			t1.add("5");
			inputDB.add(t1);

			ArrayList t2 = new ArrayList();
			t2.add("2");
			t2.add("4");
			inputDB.add(t2);

			ArrayList t3 = new ArrayList();
			t3.add("2");
			t3.add("3");
			inputDB.add(t3);

			ArrayList t4 = new ArrayList();
			t4.add("1");
			t4.add("2");
			t4.add("4");
			inputDB.add(t4);

			ArrayList t5 = new ArrayList();
			t5.add("1");
			t5.add("3");
			inputDB.add(t5);

			ArrayList t6 = new ArrayList();
			t6.add("2");
			t6.add("3");
			inputDB.add(t6);

			ArrayList t7 = new ArrayList();
			t7.add("1");
			t7.add("3");
			inputDB.add(t7);

			ArrayList t8 = new ArrayList();
			t8.add("1");
			t8.add("2");
			t8.add("3");
			t8.add("5");
			inputDB.add(t8);

			ArrayList t9 = new ArrayList();
			t9.add("1");
			t9.add("2");
			t9.add("3");
			inputDB.add(t9);
		} else {
			inputDB = FormatInput.getFormatedInput("localhost", "cluster", "root", "root", "jeda");
		}
		return inputDB;
	}
	
	public ArrayList readinfile(String addr, List<String> seq){
		ArrayList inputDB = new ArrayList();
		File file = new File(addr);
		InputStreamReader insr;
		try {
			insr = new InputStreamReader(new FileInputStream(file), "gb2312");

			BufferedReader br = new BufferedReader(insr);
			String line;
			while ((line = br.readLine()) != null) {
				line = line.trim();
				if (line.trim().length() >= 1) {
					String[] lines = line.split(",");
					ArrayList temp = new ArrayList();
					for (int i = 0; i <= lines.length - 1; i++) {
						if (lines[i].trim().length() >= 1) {
//							String bno = encoding(lines[i].trim(), seq);
//							if(bno.equals( "0")){
//								System.out.println(lines[i].trim());
//							}
//							temp.add(bno);
							temp.add(lines[i].trim());
						}
					}
					inputDB.add(temp);
				}
			}
			br.close();
			insr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return inputDB;
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
	
//	public String encoding(String item, List<String> seq){
//		for(int i=0;i<=seq.size()-1;i++){
//			if(seq.get(i).equals(item)){
//				return String.valueOf(i+1);
//			}
//		}
//		return "0";
//	}
//	public String decoding(String i, List<String> seq){
//		int inti = Integer.parseInt(i);
//		if(inti>0){
//			return seq.get(inti-1);
//		}else{
//			return null;
//		}
//	}
	
	public Map runforfreqitems(int mini_sup, ArrayList inputDB, List<String> seq, String freqaddr) throws IOException{
		File re = new File(freqaddr);
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
		System.out.println("最小支持度是："+ mini_sup);
		Map a = Apriori_generateFreItemSet.generateFreItemSet(inputDB, mini_sup);
		System.out.println("它包含的频繁模式是:");
		Set set =a.entrySet();      
		Iterator it=set.iterator();       
		while(it.hasNext()){           
			Map.Entry<String, Integer>  entry=(Entry<String, Integer>) it.next();  
			String[] keys = entry.getKey().split("\\|");
			for(int i = 0;i<=keys.length-1;i++){
				if(i==keys.length-1){
//					output.write(decoding(keys[i].trim(), seq)+" ");
					output.write(keys[i].trim()+" ");
				}else{
//					output.write(decoding(keys[i].trim(), seq)+"|");
					output.write(keys[i].trim()+"|");
				}
				
			}
			output.write(": "+entry.getValue()+"\n");                 
		}
		System.out.println(a.size());
		output.close();
		return a;
	}

	public ArrayList runforrules(double mini_confidence, Map a, String ruleaddr) throws IOException{
		File rere = new File(ruleaddr);
		if (rere.exists()) {
			rere.delete();
			if (rere.createNewFile()) {
				System.out.println("result file create success!");
			} else {
				System.out.println("result file create failed!");
			}
		} else {
			if (rere.createNewFile()) {
				System.out.println("result file create success!");
			} else {
				System.out.println("result file create failed!");
			}

		}

		
        BufferedWriter outputre = new BufferedWriter(new FileWriter(rere));
	

		System.out.println("最小置信度是："+mini_confidence*100+"%");
		ArrayList result = Apriori_generateRule.generatreRule(a, mini_confidence);
		System.out.println("规则数目：" + result.size());
		System.out.println("规则如下：");
		for (int i = 0; i < result.size(); i++) {
			System.out.print("规则" + (i + 1) + ": ");
			System.out.println(((Rule) result.get(i)).toString());
			outputre.write(((Rule) result.get(i)).toString()+"\n");
		}
		outputre.close();
		System.out.println(result.size());
		return result;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// TODO 自动生成方法存根
		Test test = new Test();
		ArrayList inputDB = new ArrayList();
		long startTime = System.currentTimeMillis();

		List<String> seq = test.getSeqFile("seq2549.txt");
		inputDB = test.readinfile("2549_46.txt", seq);
//		List<String> seq = test.getSeqFile("seqtest.txt");
//		inputDB = test.gettestdata(true);

		int[] counts = new int[31];
		for(int i=0; i<=30;i++){
			counts[i] = 0;
		}
		int min = 5;
		int max = 5;
		System.out.println("您输入的数据库是 :");
		System.out.println("***********************");
		for (int i = 0; i < inputDB.size(); i++) {
			int subsize = ((ArrayList)inputDB.get(i)).size();
			if(subsize>max)max = subsize;
			if(subsize<min)min = subsize;
			counts[subsize-1]++;
//			System.out.println(inputDB.get(i));
		}
		 
		System.out.println("max = "+max);
		System.out.println("min = "+ min);
		System.out.println("+++++++++++");
		for(int i=0; i<=30;i++){
			System.out.println(counts[i]);
		}
		
		
		Map a = test.runforfreqitems(23, inputDB, seq, "freitem.txt");
		
		long endTime = System.currentTimeMillis();
		double cost =  (double)(endTime - startTime)/60000;
		DecimalFormat myformat = new DecimalFormat("#0.00");		
		System.out.println("运行时间 " + myformat.format(cost) + "min");
		System.out.println("频繁算法结束！");
		
//		startTime = System.currentTimeMillis();
//		
//		ArrayList r = test.runforrules(0.5, a, "rules.txt");
//		
//		endTime = System.currentTimeMillis();
//		cost =  (double)(endTime - startTime)/60000;
//		System.out.println("运行时间 " + myformat.format(cost) + "min");
//		System.out.println("规则算法结束！");
		
		

	}

}