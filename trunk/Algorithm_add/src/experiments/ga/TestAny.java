package experiments.ga;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import experiments.Matrix;
import experiments.apga.APGA;
import experiments.apga.clustObjectFun;


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
		APGA a1 = new APGA();
		int m = 40;
		int n = 30;
		Matrix consValue32 = new Matrix(2, n);
		Matrix consValue100 = new Matrix(2, n);
		Matrix consValue512 = new Matrix(2, n);
		Matrix lastPos32 = new Matrix(m, n);
		Matrix lastPos100 = new Matrix(m, n);
		Matrix lastPos512 = new Matrix(m, n);
		Matrix pBestackley = new Matrix(1, m);
		Matrix pBestx = new Matrix(1, m);
		Matrix pBestmax = new Matrix(1, m);
		Matrix pBeststep = new Matrix(1, m);
		Matrix pBestcos = new Matrix(1, m);
		for(int i = 0; i<=n-1;i++ ){
			consValue32.data[0][i] = -32;
			consValue32.data[1][i] = 32;
			consValue100.data[0][i] = -100;
			consValue100.data[1][i] = 100;
			consValue512.data[0][i] = -5.12;
			consValue512.data[1][i] = 5.12;
		}
		for(int i = 0; i<=m-1;i++ ){
			for(int j = 0; j<=n-1;j++ ){
				lastPos32.data[i][j] = Math.random()*64-32;
				lastPos100.data[i][j] = Math.random()*200-100;
				lastPos512.data[i][j] = Math.random()*10.24-5.12;
			}
		}
		for(int i = 0; i<=m-1; i++){
			double totalacley = 0;
			double totalcos = 0;
			double totalx = 0;
			double totalmax = 0;
			double totalcos0 = 0;
			double totalstep = 0;
			for(int j = 0; j<=n-1; j++){
				totalacley +=  (lastPos32.data[i][j]*lastPos32.data[i][j]);
				totalcos += (Math.cos(2*Math.PI*lastPos32.data[i][j]));
				totalcos0+=(lastPos512.data[i][j]*lastPos512.data[i][j]-10*Math.cos(2*lastPos512.data[i][j]*Math.PI)+10);
				totalx+=Math.pow(lastPos100.data[i][j], 2.0);
				if(totalmax<Math.abs(lastPos100.data[i][j]))totalmax=Math.abs(lastPos100.data[i][j]);
				totalstep+=(Math.floor(lastPos100.data[i][j]+0.5)*Math.floor(lastPos100.data[i][j]+0.5));
			}
			pBestackley.data[0][i] = 20*Math.exp(-0.2*Math.sqrt(totalacley/n))+Math.exp(totalcos/n);
			pBestcos.data[0][i] = n*111-totalcos0;
			pBestx.data[0][i] = n*Math.pow(10, 2.0)-totalx;
			pBeststep.data[0][i] = n*Math.pow(10, 2.0)-totalstep;
			pBestmax.data[0][i] = 100-totalmax;
		}
		
		try {
			File result1 = new File("x.txt");
			if (result1.exists()) {
				result1.delete();
				if (result1.createNewFile()) {
					System.out.println("result1 file create success!");
				} else {
					System.out.println("result1 file create failed!");
				}
			} else {
				if (result1.createNewFile()) {
					System.out.println("result1 file create success!");
				} else {
					System.out.println("result1 file create failed!");
				}

			}
			File result2 = new File("cos.txt");
			if (result2.exists()) {
				result2.delete();
				if (result2.createNewFile()) {
					System.out.println("result2 file create success!");
				} else {
					System.out.println("result2 file create failed!");
				}
			} else {
				if (result2.createNewFile()) {
					System.out.println("result2 file create success!");
				} else {
					System.out.println("result2 file create failed!");
				}

			}
			File result3 = new File("ackley.txt");
			if (result3.exists()) {
				result3.delete();
				if (result3.createNewFile()) {
					System.out.println("result3 file create success!");
				} else {
					System.out.println("result3 file create failed!");
				}
			} else {
				if (result3.createNewFile()) {
					System.out.println("result3 file create success!");
				} else {
					System.out.println("result3 file create failed!");
				}

			}
			
			File result4 = new File("Quardirc.txt");
			if (result4.exists()) {
				result4.delete();
				if (result4.createNewFile()) {
					System.out.println("result4 file create success!");
				} else {
					System.out.println("result4 file create failed!");
				}
			} else {
				if (result4.createNewFile()) {
					System.out.println("result4 file create success!");
				} else {
					System.out.println("result4 file create failed!");
				}

			}
			
			File result5 = new File("step.txt");
			if (result5.exists()) {
				result5.delete();
				if (result5.createNewFile()) {
					System.out.println("result5 file create success!");
				} else {
					System.out.println("result5 file create failed!");
				}
			} else {
				if (result5.createNewFile()) {
					System.out.println("result5 file create success!");
				} else {
					System.out.println("result5 file create failed!");
				}

			}

			BufferedWriter output1 = new BufferedWriter(new FileWriter(result1));
			BufferedWriter output2 = new BufferedWriter(new FileWriter(result2));
			BufferedWriter output3 = new BufferedWriter(new FileWriter(result3));
			BufferedWriter output4 = new BufferedWriter(new FileWriter(result4));
			BufferedWriter output5 = new BufferedWriter(new FileWriter(result5));
			
			double lamda =1.0;
//			for(int a=0; a<=0;a++){
            while(lamda<=2){
//				a1.Calculate(new AckleyMaxFunction(), 0.8, 0.01, 100.0, 0.8, lamda, consValue32, lastPos32, pBestackley, 200, output3);
//				a1.Calculate(new AckleyMaxFunction(), 0.8, 0.01, 100.0, 0.8, 0.8, consValue32, lastPos32, pBestackley, 200, output3);
//				a1.Calculate(new MaxFunction(), 0.8, 0.01, 100.0, 0.8,lamda, consValue100, lastPos100, pBestx, 200, output1);
//				a1.Calculate(new MaxFunction(), 0.8, 0.01, 100.0, 0.8,  0.8, consValue100, lastPos100, pBestx, 200, output1);
//				a1.Calculate(new CosMaxFunction(), 0.8, 0.01, 100.0, 0.8, lamda, consValue512, lastPos512, pBestcos, 200, output2);
//				a1.Calculate(new CosMaxFunction(), 0.8, 0.01, 100.0, 0.8,  0.8, consValue512, lastPos512, pBestcos, 200, output2);
            	a1.Calculate(new QuardircMaxFunction(), 0.8, 0.01, 100.0, 0.8,lamda, consValue100, lastPos100, pBestmax, 200, output4);
//				a1.Calculate(new QuardircMaxFunction(), 0.8, 0.01, 100.0, 0.8,  0.8, consValue100, lastPos100, pBestmax, 200, output4);
				a1.Calculate(new StepMaxFunction(), 0.8, 0.01, 100.0, 0.8,lamda, consValue100, lastPos100, pBeststep, 200, output5);
//				a1.Calculate(new StepMaxFunction(), 0.8, 0.01, 100.0, 0.8,  0.8, consValue100, lastPos100, pBeststep, 200, output5);
				lamda+=0.05;
			}
			
			output1.close();
			output2.close();
			output3.close();
			output4.close();
			output5.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//a1.runapgaexample();

		long endTime = System.currentTimeMillis();
		System.out.println("运行时间 " + (endTime - startTime) + "ms");
		System.out.println("符合模式：" + clustObjectFun.mycount);
	}

}
