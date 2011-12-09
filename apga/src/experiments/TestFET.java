package experiments;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgap.FitnessFunction;
import org.jgap.impl.DoubleGene;

import experiments.apga.APGA;
import experiments.apga.clustObjectFun;


public class TestFET {

	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		

        
		
		
		long startTime = System.currentTimeMillis();
		APGA a1 = new APGA();
		int m = 40;
		int n = 5;
		List<List<Double>> scopes5 = new ArrayList();
		for(int i = 0; i<=n-1;i++ ){
			List<Double> sp5 = new ArrayList();
			
		    sp5.add(0.0);
		    sp5.add(5.0);
		    scopes5.add(sp5);
		}
		try {
			String prefix = "1_";
			
			File result = new File(prefix+"ap_fet.txt");
			
			BufferedWriter output = new BufferedWriter(new FileWriter(result));
			
			double p_lamda =0.8;
			double p_lamda0 =0.0;
			double lamda =0.8;
			double lamda2 =0.8;
			double p_extra0 =0.00;
			double p_extra =0.01;
			int ap_max = 100;
			double ap_lamda = 0.8;
//            while(lamda2<=1.05){
            	for(int bb=0; bb<=0;bb++){       
//            	a1.Calculate(200, 40, 5, scopes5, new FetMaxFunction(), p_lamda0, p_extra0, ap_max, ap_lamda, lamda2, output);
            	           	
            	a1.Calculate(200, 40, 5, scopes5, new FetMaxFunction(), p_lamda, p_extra, ap_max, ap_lamda, lamda, output);
            		 output.write("\n");
            		 output.flush();
                	
            	}
//                      for (BufferedWriter op : output) {
//    					   op.write("\n");
//    					   op.flush();
//    					   }
//				lamda2+=0.05;
//			}
				output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		long endTime = System.currentTimeMillis();
		System.out.println("运行时间 " + (endTime - startTime) + "ms");
		System.out.println("fet计算次数：" + FetMaxFunction.counts);
	}

}
