package experiments.ga;

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

import org.jgap.impl.DoubleGene;

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
		Matrix consValue30 = new Matrix(2, n);
		Matrix consValue100 = new Matrix(2, n);
		Matrix consValue512 = new Matrix(2, n);
		Matrix consValue500 = new Matrix(2, n);
		Matrix consValue600 = new Matrix(2, n);
		Matrix lastPos32 = new Matrix(m, n);
		Matrix lastPos30 = new Matrix(m, n);
		Matrix lastPos100 = new Matrix(m, n);
		Matrix lastPos512 = new Matrix(m, n);
		Matrix lastPos500 = new Matrix(m, n);
		Matrix lastPos600 = new Matrix(m, n);
		Matrix pBestrosen = new Matrix(1, m);
		Matrix pBestackley = new Matrix(1, m);
		Matrix pBestx = new Matrix(1, m);
		Matrix pBestmax = new Matrix(1, m);
		Matrix pBeststep = new Matrix(1, m);
		Matrix pBestcos = new Matrix(1, m);
		Matrix pBestsch = new Matrix(1, m);
		Matrix pBestgri = new Matrix(1, m);
		for(int i = 0; i<=n-1;i++ ){
			consValue30.data[0][i] = -30;
			consValue30.data[1][i] = 30;
			consValue32.data[0][i] = -32;
			consValue32.data[1][i] = 32;
			consValue100.data[0][i] = -100;
			consValue100.data[1][i] = 100;
			consValue512.data[0][i] = -5.12;
			consValue512.data[1][i] = 5.12;
			consValue500.data[0][i] = -500;
			consValue500.data[1][i] = 500;
			consValue600.data[0][i] = -600;
			consValue600.data[1][i] = 600;
		}
		for(int i = 0; i<=m-1;i++ ){
			for(int j = 0; j<=n-1;j++ ){
				lastPos30.data[i][j] = Math.random()*60-30;
				lastPos32.data[i][j] = Math.random()*64-32;
				lastPos100.data[i][j] = Math.random()*200-100;
				lastPos512.data[i][j] = Math.random()*10.24-5.12;
				lastPos500.data[i][j] = Math.random()*1000-500;
				lastPos600.data[i][j] = Math.random()*1200-600;
			}
		}
		for(int i = 0; i<=m-1; i++){
			double totalacley = 0;
			double totalcos = 0;
			double totalx = 0;
			double totalmax = 0;
			double totalcos0 = 0;
			double totalstep = 0;
			double totalrosen = 0;
			double totalsch = 0;
			double totalgri = 0;
			double prodgri = 1;
			for(int j = 0; j<=n-1; j++){
				totalacley +=  (lastPos32.data[i][j]*lastPos32.data[i][j]);
				totalcos += (Math.cos(2*Math.PI*lastPos32.data[i][j]));
				totalcos0+=(lastPos512.data[i][j]*lastPos512.data[i][j]-10*Math.cos(2*lastPos512.data[i][j]*Math.PI)+10);
				totalx+=Math.pow(lastPos100.data[i][j], 2.0);
				if(totalmax<Math.abs(lastPos100.data[i][j]))totalmax=Math.abs(lastPos100.data[i][j]);
				totalstep+=(Math.floor(lastPos100.data[i][j]+0.5)*Math.floor(lastPos100.data[i][j]+0.5));
				if(j<=n-1-1){
					totalrosen+=(100*Math.pow((lastPos30.data[i][j+1]-Math.pow(lastPos30.data[i][j], 2)), 2)+Math.pow((lastPos30.data[i][j]-1), 2));
				}
				totalsch+=(-1*lastPos500.data[i][j]*Math.sin(Math.sqrt(Math.abs(lastPos500.data[i][j]))));
				totalgri+=(Math.pow(lastPos600.data[i][j], 2));
				prodgri*=(Math.cos(lastPos600.data[i][j]/Math.sqrt(j+1)));
			}
			pBestackley.data[0][i] = 20*Math.exp(-0.2*Math.sqrt(totalacley/n))+Math.exp(totalcos/n);
			pBestcos.data[0][i] = n*111-totalcos0;
			pBestx.data[0][i] = n*Math.pow(10, 2.0)-totalx;
			pBeststep.data[0][i] = n*Math.pow(10, 2.0)-totalstep;
			pBestmax.data[0][i] = 100-totalmax;
			pBestrosen.data[0][i] = (1999999999-totalrosen)<=0?1:1999999999-totalrosen;
			pBestsch.data[0][i] = (838*n-418.9829*n-totalsch)<=0?1:838*n-418.9829*n-totalsch;
			pBestgri.data[0][i] = (2701-1-totalgri/4000+prodgri)<=0?1:2701-1-totalgri/4000+prodgri;
		}
		
		try {
			
			File[] result = {new File("x.txt"), new File("cos.txt"), 
					new File("ackley.txt"),	new File("Quardirc.txt"), 
					new File("step.txt"), new File("rosen.txt"), 
					new File("sch.txt"), new File("gri.txt")};
			BufferedWriter[] output = new BufferedWriter[result.length];
			for(int i = 0; i<= result.length-1;i++){
				if (result[i].exists()) {
					result[i].delete();
					if (result[i].createNewFile()) {
						System.out.println("result"+i+" file create success!");
					} else {
						System.out.println("result"+i+" file create failed!");
					}
				} else {
					if (result[i].createNewFile()) {
						System.out.println("result"+i+" file create success!");
					} else {
						System.out.println("result"+i+" file create failed!");
					}

				}
				output[i] = new BufferedWriter(new FileWriter(result[i]));
			}
			
			double lamda =1.0;
			for(int a=0; a<=0;a++){
//            while(lamda<=2){
//				a1.Calculate(new AckleyMaxFunction(), 0.8, 0.01, 100.0, 0.8, lamda, consValue32, lastPos32, pBestackley, 200, output[2]);
//				a1.Calculate(new AckleyMaxFunction(), 0.8, 0.01, 100.0, 0.8, 0.8, consValue32, lastPos32, pBestackley, 200, output[2]);
//				a1.Calculate(new MaxFunction(), 0.8, 0.01, 100.0, 0.8,lamda, consValue100, lastPos100, pBestx, 200, output[0]);
//				a1.Calculate(new MaxFunction(), 0.8, 0.01, 100.0, 0.8,  0.8, consValue100, lastPos100, pBestx, 200, output[0);
//				a1.Calculate(new CosMaxFunction(), 0.8, 0.01, 100.0, 0.8, lamda, consValue512, lastPos512, pBestcos, 200, output[1]);
//				a1.Calculate(new CosMaxFunction(), 0.8, 0.01, 100.0, 0.8,  0.8, consValue512, lastPos512, pBestcos, 200, output[1]);
//            	a1.Calculate(new QuardircMaxFunction(), 0.8, 0.01, 100.0, 0.8,lamda, consValue100, lastPos100, pBestmax, 200, output[3]);
//				a1.Calculate(new QuardircMaxFunction(), 0.8, 0.01, 100.0, 0.8,  0.8, consValue100, lastPos100, pBestmax, 200, output[3]);
//				a1.Calculate(new StepMaxFunction(), 0.8, 0.01, 100.0, 0.8,lamda, consValue100, lastPos100, pBeststep, 200, output[4]);
//				a1.Calculate(new StepMaxFunction(), 0.8, 0.01, 100.0, 0.8,  0.8, consValue100, lastPos100, pBeststep, 200, output[4]);
//				a1.Calculate(new RosenbrockMaxFunction(), 0.8, 0.01, 100.0, 0.8,lamda, consValue30, lastPos30, pBestrosen, 200, output[5]);
//				a1.Calculate(new RosenbrockMaxFunction(), 0.8, 0.01, 100.0, 0.8,  0.8, consValue30, lastPos30, pBestrosen, 200, output[5]);
				a1.Calculate(new SchwefelMaxFunction(), 0.8, 0.01, 100.0, 0.8,lamda, consValue500, lastPos500, pBestsch, 200, output[6]);
				a1.Calculate(new SchwefelMaxFunction(), 0.8, 0.01, 100.0, 0.8,  0.8, consValue500, lastPos500, pBestsch, 200, output[6]);
				a1.Calculate(new GriewankMaxFunction(), 0.8, 0.01, 100.0, 0.8,lamda, consValue600, lastPos600, pBestgri, 200, output[7]);
				a1.Calculate(new GriewankMaxFunction(), 0.8, 0.01, 100.0, 0.8,  0.8, consValue600, lastPos600, pBestgri, 200, output[7]);
				lamda+=0.05;
			}
			for(BufferedWriter op : output){
				op.close();
			}
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
