package processor;

import java.text.DecimalFormat;
import java.util.*;
import java.util.Map.Entry;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.math.BigDecimal;

import apriori.Apriori_generateFreItemSet;
import apriori.Apriori_generateRule;
import apriori.FormatInput;
import apriori.Rule;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// TODO 自动生成方法存根
		ArrayList inputDB = new ArrayList();
		long startTime = System.currentTimeMillis();
//		Boolean test = true;
//		if (test) {
//			ArrayList t1 = new ArrayList();
//			t1.add("1");
//			t1.add("2");
//			t1.add("5");
//			inputDB.add(t1);
//
//			ArrayList t2 = new ArrayList();
//			t2.add("2");
//			t2.add("4");
//			inputDB.add(t2);
//
//			ArrayList t3 = new ArrayList();
//			t3.add("2");
//			t3.add("3");
//			inputDB.add(t3);
//
//			ArrayList t4 = new ArrayList();
//			t4.add("1");
//			t4.add("2");
//			t4.add("4");
//			inputDB.add(t4);
//
//			ArrayList t5 = new ArrayList();
//			t5.add("1");
//			t5.add("3");
//			inputDB.add(t5);
//
//			ArrayList t6 = new ArrayList();
//			t6.add("2");
//			t6.add("3");
//			inputDB.add(t6);
//
//			ArrayList t7 = new ArrayList();
//			t7.add("1");
//			t7.add("3");
//			inputDB.add(t7);
//
//			ArrayList t8 = new ArrayList();
//			t8.add("1");
//			t8.add("2");
//			t8.add("3");
//			t8.add("5");
//			inputDB.add(t8);
//
//			ArrayList t9 = new ArrayList();
//			t9.add("1");
//			t9.add("2");
//			t9.add("3");
//			inputDB.add(t9);
//		} else {
//			inputDB = FormatInput.getFormatedInput("localhost", "cluster", "root", "root", "jeda");
//		}
		File file = new File("42.txt");
		InputStreamReader insr = new InputStreamReader(new FileInputStream(file), "gb2312");
		BufferedReader br = new BufferedReader(insr);
		String line;
		while ((line = br.readLine()) != null) {
			line = line.trim();				
			if (line.trim().length() >= 1) 
			{
				String[] lines = line.split(",");
				ArrayList temp = new ArrayList();
				for(int i =0;i<=lines.length-1;i++){
					if(lines[i].trim().length()>=1){
						temp.add(lines[i].trim());
					}
				}
				inputDB.add(temp);
			   }
			}
		br.close();
		insr.close();
		
		File re = new File("freitem.txt");
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
		
//		File rere = new File("rules.txt");
//		if (rere.exists()) {
//			rere.delete();
//			if (rere.createNewFile()) {
//				System.out.println("result file create success!");
//			} else {
//				System.out.println("result file create failed!");
//			}
//		} else {
//			if (rere.createNewFile()) {
//				System.out.println("result file create success!");
//			} else {
//				System.out.println("result file create failed!");
//			}
//
//		}

		BufferedWriter output = new BufferedWriter(new FileWriter(re));
//        BufferedWriter outputre = new BufferedWriter(new FileWriter(rere));
		

		System.out.println("您输入的数据库是 :");
		System.out.println("***********************");
		for (int i = 0; i < inputDB.size(); i++) {
			System.out.println(inputDB.get(i));
		}
		
		System.out.println("最小支持度是：21");
		Map a = Apriori_generateFreItemSet.generateFreItemSet(inputDB, 21);
		System.out.println("它包含的频繁模式是:");
		Set set =a.entrySet();      
		Iterator it=set.iterator();       
		while(it.hasNext()){           
			Map.Entry<Object, Integer>  entry=(Entry<Object, Integer>) it.next();           
			output.write(entry.getKey()+": "+entry.getValue()+"\n");                 
		}
		System.out.println(a.size());
		output.close();
		long endTime = System.currentTimeMillis();
		double cost =  (double)(endTime - startTime)/60000;
		DecimalFormat myformat = new DecimalFormat("#0.00");		
		System.out.println("运行时间 " + myformat.format(cost) + "min");
		System.out.println("算法结束！");
		
//		System.out.println("***********************");
//		System.out.println("最小支持度是：21");
//		Map a = Apriori_generateFreItemSet.generateFreItemSet(inputDB, 21);
//		System.out.println("它包含的频繁模式是:");
//		System.out.println(a);
//		System.out.println(a.size());
//
//		System.out.println("最小置信度是：50%");
//		ArrayList result = Apriori_generateRule.generatreRule(a, 0.5);
//		System.out.println("规则数目：" + result.size());
//		System.out.println("规则如下：");
//		for (int i = 0; i < result.size(); i++) {
//			System.out.print("规则" + (i + 1) + ": ");
//			if (test) {
//				System.out.println(((Rule) result.get(i)).getCondition()
//								+ "=>"
//								+ ((Rule) result.get(i))
//										.getConditionAndConclution()
//								+ " 的置信度是："
//								+ (Math.round(((Rule) result.get(i))
//										.getConfidence() * 100)) + "%");
//			} else {
//				System.out.println(((Rule) result.get(i)).toString());
//			}
//		}
//		System.out.println(result.size());

	}

}