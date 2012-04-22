package vasp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import kmpso.KMPSO;
import kmpso.clustObjectFun;



import test.MaxFunction;


public class TestTest {

	public static double[][] vector_decoder(double position[], double vol) {
		int csize = position.length;
		if (csize < 6) {
			System.err.println("wrong chromosome size input!");
			return null;
		}
		double[] input = new double[6];
		for (int i = 0; i <= 2; i++) {
			double genevalue = position[i];
			input[i] = (Math.PI/6) + genevalue * ((5*Math.PI/6) -(Math.PI/6));
		}
		input[3] = position[3];
		double max = 1;
		double min = input[3] / 4;
		if (4 * input[3] < 1)
			max = 4 * input[3];
		input[4] = position[4];
		input[4] = min + input[4] * (max - min);
		double vmax = input[3];
		double vmin = input[4];
		if (input[4] > input[3]) {
			vmax = input[4];
			vmin = input[3];
		}
		min = vmax / 4;
		if (4 * vmin < 1)
			max = 4 * vmin;
		input[5] = position[5];
		input[5] = min + input[5] * (max - min);

		double[][] results = new double[3][3];
		results[0][0] = input[3];
		results[0][1] = 0;
		results[0][2] = 0;
		results[1][0] = input[4] * Math.cos(input[0]);
		results[1][1] = input[4] * Math.sin(input[0]);
		results[1][2] = 0;
		results[2][0] = input[5] * Math.cos(input[2]);
		results[2][2] = input[5]
				* Math.sqrt(Math.pow(Math.sin(input[1]), 2)
						- Math.pow((Math.cos(input[2]) - Math.cos(input[0])
								* Math.cos(input[1]))
								/ Math.sin(input[0]), 2));
		results[2][1] = Math.sqrt(Math.pow(input[5], 2)
				- Math.pow(results[2][0], 2) - Math.pow(results[2][2], 2));
		
		double v = results[0][0]*results[1][1]*results[2][2];
		double weight = Math.cbrt(vol/v);
		for(int i = 0; i<=2;i++){
			for(int j = 0; j<=2;j++){
				results[i][j] = weight*results[i][j];
			}
		}

		return results;
	}

	public static double[][] vector_decoder(Double[] chrom, double vol) {
		int csize = chrom.length;
		if (csize < 6) {
			System.err.println("wrong chromosome size input!");
			return null;
		}
		double[] input = new double[6];
		for (int i = 0; i <= 2; i++) {
			double genevalue = chrom[i];
			input[i] = (Math.PI/6) + genevalue * ((5*Math.PI/6) -(Math.PI/6));
		}
//		input[0] = Math.PI/2;
		input[3] = chrom[3];
		double max = 1;
		double min = input[3] / 4;
		if (4 * input[3] < 1)
			max = 4 * input[3];
		input[4] = chrom[4];
		input[4] = min + input[4] * (max - min);
		double vmax = input[3];
		double vmin = input[4];
		if (input[4] > input[3]) {
			vmax = input[4];
			vmin = input[3];
		}
		min = vmax / 4;
		if (4 * vmin < 1)
			max = 4 * vmin;
		input[5] = chrom[5];
		input[5] = min + input[5] * (max - min);

		double[][] results = new double[3][3];
		results[0][0] = input[3];
		results[0][1] = 0;
		results[0][2] = 0;
		results[1][0] = input[4] * Math.cos(input[0]);
		results[1][1] = input[4] * Math.sin(input[0]);
		results[1][2] = 0;
		results[2][0] = input[5] * Math.cos(input[2]);
		results[2][2] = input[5]
				* Math.sqrt(Math.abs(Math.pow(Math.sin(input[1]), 2)
						- Math.pow((Math.cos(input[2]) - Math.cos(input[0])
								* Math.cos(input[1]))
								/ Math.sin(input[0]), 2)));
		results[2][1] = Math.sqrt(Math.abs(Math.pow(input[5], 2)
				- Math.pow(results[2][0], 2) - Math.pow(results[2][2], 2)));
		
		double v = results[0][0]*results[1][1]*results[2][2];
		double weight = Math.cbrt(vol/v);
		for(int i = 0; i<=2;i++){
			for(int j = 0; j<=2;j++){
				results[i][j] = weight*results[i][j];
			}
		}

		return results;
	}

	public static void writeposcar(double[][] vecs, int nn, String jobname) {
		try {
			File result = new File("POSCAR");
			if (result.exists()) {
				result.delete();

				if (result.createNewFile()) {
					System.out.println("result  file create success!");
				} else {
					System.out.println("result  file create failed!");
				}
			} else {
				if (result.createNewFile()) {
					System.out.println("result  file create success!");
				} else {
					System.out.println("result  file create failed!");
				}

			}
			BufferedWriter output = new BufferedWriter(new FileWriter(result));
			System.out.println(jobname);
			output.write(jobname+"\n");
			double unknow = 1;
			System.out.println(unknow);
			output.write(unknow+"\n");
			int m = vecs.length;
			int n = vecs[0].length;
			for (int i = 0; i <= m - 1; i++) {
				for (int j = 0; j <= n - 1; j++) {
					System.out.print(vecs[i][j]);
					output.write(String.valueOf(vecs[i][j]));
					System.out.print("\t");
					output.write("\t");
				}
				System.out.print("\n");
				output.write("\n");
			}
			System.out.println(nn);
			output.write(nn+"\n");
			System.out.println("Direct");
			output.write("Direct"+"\n");
			double[][] pos = new double[nn][3];
			m = pos.length;
			n = pos[0].length;
//			for (int i = 0; i <= m - 1; i++) {
//			for (int j = 0; j <= n - 1; j++) {
//				pos[i][j] = Math.random();
//				System.out.print(pos[i][j]);
//				output.write(String.valueOf(pos[i][j]));
//				System.out.print("\t");
//				output.write("\t");
//			}
//			System.out.print("\n");
//			output.write("\n");
//		}
		System.out.print(0.25);
		output.write(String.valueOf(0.25));
		System.out.print("\t");
		output.write("\t");
		System.out.print(0.75);
		output.write(String.valueOf(0.75));
		System.out.print("\t");
		output.write("\t");
		System.out.print(0.0);
		output.write(String.valueOf(0.0));
		System.out.print("\t");
		output.write("\t");
		System.out.print("\n");
		output.write("\n");
		
		System.out.print(0.75);
		output.write(String.valueOf(0.75));
		System.out.print("\t");
		output.write("\t");
		System.out.print(0.25);
		output.write(String.valueOf(0.25));
		System.out.print("\t");
		output.write("\t");
		System.out.print(0.0);
		output.write(String.valueOf(0.0));
		System.out.print("\t");
		output.write("\t");
		System.out.print("\n");
		output.write("\n");
		
		System.out.print(0.25);
		output.write(String.valueOf(0.25));
		System.out.print("\t");
		output.write("\t");
		System.out.print(0.75);
		output.write(String.valueOf(0.75));
		System.out.print("\t");
		output.write("\t");
		System.out.print(0.5);
		output.write(String.valueOf(0.5));
		System.out.print("\t");
		output.write("\t");
		System.out.print("\n");
		output.write("\n");
		
		System.out.print(0.75);
		output.write(String.valueOf(0.75));
		System.out.print("\t");
		output.write("\t");
		System.out.print(0.25);
		output.write(String.valueOf(0.25));
		System.out.print("\t");
		output.write("\t");
		System.out.print(0.5);
		output.write(String.valueOf(0.5));
		System.out.print("\t");
		output.write("\t");
		System.out.print("\n");
		output.write("\n");
		
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int nn = 4;
		Double[] ins = new Double[6];
		for (int i = 0; i <= 5; i++) {
			ins[i] = Math.random();
		}
		double[] r = {1.81,1.81,1.81,1.81};
		double[] v = new double[nn];
		double vol = 0;
		for(int i=0;i<=nn-1;i++){
			v[i] = 4*Math.PI*Math.pow(r[i], 3)/3;
			vol+=v[i];
		}
		vol = 1.2*vol;
//		double[][] vecs = vector_decoder(ins, vol);
//		writeposcar(vecs,nn,"try");
		
		long startTime = System.currentTimeMillis();
		KMPSO kmpso = new KMPSO();
		int m = 40;
		int n = 6;
		List<List<Double>> scopes = new ArrayList();
		for(int i = 0; i<=n-1;i++ ){
			List<Double> sp = new ArrayList();
			sp.add(0.0);
			sp.add(1.0);
			scopes.add(sp);
		}
		
		try {
			String prefix = "vasp_4";
			List<File> results = new ArrayList();
			
			File result0 = new File(prefix+".txt");
			results.add(result0);
			
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
			int max_gen = 200;
			int numofparticals = 40;
			int dimention = 30;
			double[] intertia = { 0.95, 0.9, 0.95, 0.9, 0.95, 1, 0.95, 0.9,0.95, 0.95, 0.9, 0.95 };
			double[] velocity = { 1, 1.45, 1.85, 1.2, 1.4, 1.15, 1.7, 1.3, 1.8,1.6, 1.1, 1.55 };
			double p_lamda = 0.8;
			double p_extra = 0.002;
			int kmeans_max = 100;
			int kmeans_num = 4;
			double lamda = 0.8;
            	for(int bb=0; bb<=0;bb++){
            		kmpso.Calculate(max_gen, numofparticals, dimention, intertia[0],velocity[0], scopes, new VaspMaxFunction(nn, vol, "AutoPOSCAR"), p_lamda, p_extra,  kmeans_max, kmeans_num, lamda, output[0]);
            	}
            	for(BufferedWriter op : output){
    				op.write("\n");
    				op.flush();
    			}
				lamda+=0.05;
//			}
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
