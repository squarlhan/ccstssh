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

import experiments.kmeansga.KMeansGA;
import experiments.kmeansga.clustObjectFun;


public class TestAny {

	
	public static List<Integer> doube2binary(double min, double max, int p, double num){
		List<Integer> result = new ArrayList();
		int rank = (int) ((Math.pow(2, p)-1)*(num-min)/(max-min));
		String temp = Integer.toBinaryString(rank);
		if(temp.length()>0){
			for(int i = 0;i<=temp.length()-1;i++){
				result.add(Integer.parseInt(temp.substring(i, i+1)));
			}
			while(result.size()<p){
				result.add(0,0);
			}
		}else{
			return null;
		}
		return result;
	}
	//二进制分三部分，第一位是符号位，接下来几位表示整数部分，最后一些是小数部分
	public static List<Integer> doube2binary2(double min, double max, int p, double num){
		List<Integer> result = new ArrayList();
		//首先确定符号，1表示+，0表示-
		int sign = num<0?0:1;
		result.add(sign);
		//确定整数部分
		int num_int = (int) Math.abs(num);
		int int_lenth = (int) Math.ceil(Math.log10(Math.max(Math.abs(min), Math.abs(max)))/Math.log10(2));
		String int_str = Integer.toBinaryString(num_int);
		while(int_str.length()<int_lenth){
			int_str = "0"+int_str;
		}
		for(int i = 0;i<=int_lenth-1;i++){
			result.add(Integer.parseInt(int_str.substring(i, i+1)));
		}
		//确定小数部分，这个和精度有关系， 比如小数点后面3位，精度为10， 4位精度为14位， 5位为17, 6位为20位
		double pp = Math.pow(2, p);
		int pp_curser = 10;
		while(pp/pp_curser>10){
			pp_curser*=10;
		}
		//根据精度截取小数部分
		double num_float = num-(int)num;
		int num_float_int = (int) Math.abs(num_float*pp_curser);
		int float_lenth = (int) Math.ceil(Math.log10(pp_curser)/Math.log10(2));
		String float_str = Integer.toBinaryString(num_float_int);
		while(float_str.length()<float_lenth){
			float_str = "0"+float_str;
		}
		for(int i = 0;i<=float_lenth-1;i++){
			result.add(Integer.parseInt(float_str.substring(i, i+1)));
		}
		return result;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		

        
		
		
		long startTime = System.currentTimeMillis();
		KMeansGA a1 = new KMeansGA();
		int m = 40;
		int n = 30;
		List<List<Double>> scopes5 = new ArrayList();
        List<List<Double>> scopes30 = new ArrayList();
        List<List<Double>> scopes32 = new ArrayList();
        List<List<Double>> scopes50 = new ArrayList();
        List<List<Double>> scopes100 = new ArrayList();
        List<List<Double>> scopes500 = new ArrayList();
        List<List<Double>> scopes512 = new ArrayList();
        List<List<Double>> scopes600 = new ArrayList();
		for(int i = 0; i<=n-1;i++ ){
			List<Double> sp5 = new ArrayList();
			List<Double> sp30 = new ArrayList();
		    List<Double> sp32 = new ArrayList();
		    List<Double> sp50 = new ArrayList();
		    List<Double> sp100 = new ArrayList();
		    List<Double> sp500 = new ArrayList();
		    List<Double> sp512 = new ArrayList();
		    List<Double> sp600 = new ArrayList();
			
		    sp5.add(-0.5);
		    sp5.add(0.5);
		    scopes5.add(sp5);
		    sp30.add(-30.0);
		    sp30.add(30.0);
		    scopes30.add(sp30);
		    sp32.add(-32.0);
		    sp32.add(32.0);
		    scopes32.add(sp32);
		    sp50.add(-50.0);
		    sp50.add(50.0);
		    scopes50.add(sp50);
		    sp100.add(-100.0);
		    sp100.add(100.0);
		    scopes100.add(sp100);
		    sp500.add(-500.0);
		    sp500.add(500.0);
		    scopes500.add(sp500);
		    sp512.add(-5.12);
		    sp512.add(5.12);
		    scopes512.add(sp512);
		    sp600.add(-600.0);
		    sp600.add(600.0);
		    scopes600.add(sp600);
		}
		try {
			String prefix = "10_50_";
			List<File> results = new ArrayList();
			
			File result0 = new File(prefix+"kmeans_x.txt");
			File result1 = new File(prefix+"kmeans_cos.txt");
			File result2 = new File(prefix+"kmeans_ackley.txt");
			File result3 = new File(prefix+"kmeans_quar.txt");
			File result4 = new File(prefix+"kmeans_step.txt");
			File result5 = new File(prefix+"kmeans_rosen.txt");
			File result6 = new File(prefix+"kmeans_sch.txt");
			File result7 = new File(prefix+"kmeans_gri.txt");
			File result8 = new File(prefix+"kmeans_pen1.txt");
			File result9 = new File(prefix+"kmeans_pen2.txt");
			File result10 = new File(prefix+"kmeans_wei.txt");
			File result11 = new File(prefix+"kmeans_non.txt");
			results.add(result0);
			results.add(result1);
			results.add(result2);
			results.add(result3);
			results.add(result4);
			results.add(result5);
			results.add(result6);
			results.add(result7);
			results.add(result8);
			results.add(result9);
			results.add(result10);
			results.add(result11);
			
			BufferedWriter[] output = new BufferedWriter[results.size()];
			
			for(int i = 0; i<= results.size()-1;i++){
				if (results.get(i).exists()) {
					results.get(i).delete();
					if (results.get(i).createNewFile()) {
						System.out.println("result"+i+" file create success!");
					} else {
						System.out.println("result"+i+" file create failed!");
					}
				} else {
					if (results.get(i).createNewFile()) {
						System.out.println("result"+i+" file create success!");
					} else {
						System.out.println("result"+i+" file create failed!");
					}

				}
				output[i] = new BufferedWriter(new FileWriter(results.get(i)));
			}
			
			double p_lamda0 =0.0;
			double p_lamda =0.8;
			double lamda =1.15;
			double lamda2 =0.0;
			double p_extra =0.0;
			int kmeans_max = 100;
			double kmeans_num = 3.0;
//            while(lamda<=2.05){
            	for(int bb=0; bb<=0;bb++){       
            	a1.Calculate(200, 40, 30, scopes100, new MaxFunction(), p_lamda0, p_extra, kmeans_max, kmeans_num, lamda2, output[0]);
            	a1.Calculate(200, 40, 30, scopes512, new CosMaxFunction(), p_lamda0, p_extra, kmeans_max, kmeans_num, lamda2, output[1]);
            	a1.Calculate(200, 40, 30, scopes32, new AckleyMaxFunction(), p_lamda0, p_extra, kmeans_max, kmeans_num, lamda2, output[2]);
            	a1.Calculate(200, 40, 30, scopes100, new QuardircMaxFunction(), p_lamda0, p_extra, kmeans_max, kmeans_num, lamda2, output[3]);
            	a1.Calculate(200, 40, 30, scopes100, new StepMaxFunction(), p_lamda0, p_extra, kmeans_max, kmeans_num, lamda2, output[4]);
            	a1.Calculate(200, 40, 30, scopes30, new RosenbrockMaxFunction(), p_lamda0, p_extra, kmeans_max, kmeans_num, lamda2, output[5]);
            	a1.Calculate(200, 40, 30, scopes500, new SchwefelMaxFunction(), p_lamda0, p_extra, kmeans_max, kmeans_num, lamda2, output[6]);
            	a1.Calculate(200, 40, 30, scopes600, new GriewankMaxFunction(), p_lamda0, p_extra, kmeans_max, kmeans_num, lamda2, output[7]);
            	a1.Calculate(200, 40, 30, scopes50, new PenalizedMaxFunction(), p_lamda0, p_extra, kmeans_max, kmeans_num, lamda2, output[8]);
            	a1.Calculate(200, 40, 30, scopes50, new Penalized2MaxFunction(), p_lamda0, p_extra, kmeans_max, kmeans_num, lamda2, output[9]);
            	a1.Calculate(200, 40, 30, scopes512, new WeiMaxFunction(), p_lamda0, p_extra, kmeans_max, kmeans_num, lamda2, output[10]);
            	a1.Calculate(200, 40, 30, scopes5, new NonMaxFunction(), p_lamda0, p_extra, kmeans_max, kmeans_num, lamda2, output[11]);
            	           	
//            	a1.Calculate(200, 40, 30, scopes100, new MaxFunction(), p_lamda, p_extra, kmeans_max, kmeans_num, lamda, output[0]);
//            	a1.Calculate(200, 40, 30, scopes512, new CosMaxFunction(), p_lamda, p_extra, kmeans_max, kmeans_num, lamda, output[1]);
//            	a1.Calculate(200, 40, 30, scopes32, new AckleyMaxFunction(), p_lamda, p_extra, kmeans_max, kmeans_num, lamda, output[2]);
//            	a1.Calculate(200, 40, 30, scopes100, new QuardircMaxFunction(), p_lamda, p_extra, kmeans_max, kmeans_num, lamda, output[3]);
//            	a1.Calculate(200, 40, 30, scopes100, new StepMaxFunction(), p_lamda, p_extra, kmeans_max, kmeans_num, lamda, output[4]);
//            	a1.Calculate(200, 40, 30, scopes30, new RosenbrockMaxFunction(), p_lamda, p_extra, kmeans_max, kmeans_num, lamda, output[5]);
//            	a1.Calculate(200, 40, 30, scopes500, new SchwefelMaxFunction(), p_lamda, p_extra, kmeans_max, kmeans_num, lamda, output[6]);
//            	a1.Calculate(200, 40, 30, scopes600, new GriewankMaxFunction(), p_lamda, p_extra, kmeans_max, kmeans_num, lamda, output[7]);
//            	a1.Calculate(200, 40, 30, scopes50, new PenalizedMaxFunction(), p_lamda, p_extra, kmeans_max, kmeans_num, lamda, output[8]);
//            	a1.Calculate(200, 40, 30, scopes50, new Penalized2MaxFunction(), p_lamda, p_extra, kmeans_max, kmeans_num, lamda, output[9]);
//            	a1.Calculate(200, 40, 30, scopes512, new WeiMaxFunction(), p_lamda, p_extra, kmeans_max, kmeans_num, lamda, output[10]);
//            	a1.Calculate(200, 40, 30, scopes5, new NonMaxFunction(), p_lamda, p_extra, kmeans_max, kmeans_num, lamda, output[11]);
            	
                	for (BufferedWriter op : output) {
    					   op.write("\n");
    					   op.flush();
    					   }
            	}

//				lamda+=0.05;
//			}
			for(BufferedWriter op : output){
				op.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		long endTime = System.currentTimeMillis();
		System.out.println("运行时间 " + (endTime - startTime) + "ms");
		System.out.println("符合模式：" + clustObjectFun.mycount);
	}

}
