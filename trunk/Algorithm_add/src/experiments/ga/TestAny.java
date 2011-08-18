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
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		
		long startTime = System.currentTimeMillis();
		APGA a1 = new APGA();
		int m = 40;
		int n = 30;
		Matrix consValue = new Matrix(2, n);
		Matrix lastPos = new Matrix(m, n);
		Matrix pBest = new Matrix(1, m);
		for(int i = 0; i<=n-1;i++ ){
			consValue.data[0][i] = -10;
			consValue.data[1][i] = 10;
		}
		for(int i = 0; i<=m-1;i++ ){
			for(int j = 0; j<=n-1;j++ ){
				lastPos.data[i][j] = Math.random()*20-10;
//				lastPos.data[i][j] = 0.0;
			}
		}
		for(int i = 0; i<=m-1; i++){
			double total = 0;
			for(int j = 0; j<=n-1; j++){
//				total+=(lastPos.data[i][j]*lastPos.data[i][j]-10*Math.cos(2*lastPos.data[i][j]*Math.PI)+10);
				total+=Math.pow(lastPos.data[i][j], 2.0);
			}
//			pBest.data[0][i] = n*111-total;
			pBest.data[0][i] = n*Math.pow(10, 2.0)-total;
		}
		
		try {
			File result = new File("newpga_x_2.txt");
			if (result.exists()) {
				result.delete();
				if (result.createNewFile()) {
					System.out.println("result file create success!");
				} else {
					System.out.println("result file create failed!");
				}
			} else {
				if (result.createNewFile()) {
					System.out.println("result file create success!");
				} else {
					System.out.println("result file create failed!");
				}

			}
			File result2 = new File("newpga_cos_2.txt");
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

			BufferedWriter output = new BufferedWriter(new FileWriter(result));
			BufferedWriter output2 = new BufferedWriter(new FileWriter(result2));
			
			for(int a=0; a<=0;a++){
				a1.Calculate(new MaxFunction(), 0.95, 0.01, 100.0, 0.8, 0.7, consValue, lastPos, pBest, 200, output);
				a1.Calculate(new CosMaxFunction(), 0.95, 0.01, 100.0, 0.8, 0.7, consValue, lastPos, pBest, 200, output2);
			}
			
			output.close();
			output2.close();
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