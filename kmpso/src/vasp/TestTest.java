package vasp;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
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
		double min = input[3] / 2;
		if (2 * input[3] < 1)
			max = 2 * input[3];
		input[4] = position[4];
		input[4] = min + input[4] * (max - min);
		double vmax = input[3];
		double vmin = input[4];
		if (input[4] > input[3]) {
			vmax = input[4];
			vmin = input[3];
		}
		min = vmax / 2;
		if (2 * vmin < 1)
			max = 2 * vmin;
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
		double min = input[3] / 2;
		if (2 * input[3] < 1)
			max = 2 * input[3];
		input[4] = chrom[4];
		input[4] = min + input[4] * (max - min);
		double vmax = input[3];
		double vmin = input[4];
		if (input[4] > input[3]) {
			vmax = input[4];
			vmin = input[3];
		}
		min = vmax / 2;
		if (2 * vmin < 1)
			max = 2 * vmin;
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

	public static void writeincar(){
		try {
			File result = new File("INCAR");
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
			
			

			output.write("SYSTEM = Various- local optimisation"+"\n");
			output.write("PREC = Accurate"+"\n");
			output.write("ENCUT = 300.0"+"\n");
			output.write("EDIFF = 1e-6"+"\n");
			output.write("IBRION = 2"+"\n");
			output.write("ISIF = 3"+"\n");
			output.write("NSW = 100"+"\n");
			output.write("ISMEAR = 1 ; SIGMA = 0.20"+"\n");
			output.write("POTIM = 0.100"+"\n");
			output.write("#No writing charge density and wavefunction"+"\n");
			output.write("LCHARG = FALSE"+"\n");
			output.write("LWAVE = FALSE"+"\n");
			output.write("#Target Pressure"+"\n");
			output.write("PSTRESS = 900"+"\n");
			output.write("#Finer optimization"+"\n");
			output.write("EDIFFG = 1e-4"+"\n");
			output.write("EDIFFG = -0.001"+"\n");
			
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
			double unknow = 1.00000000000000;
			System.out.println("   "+unknow);
			output.write("   "+unknow+"\n");
			int m = vecs.length;
			int n = vecs[0].length;
			for (int i = 0; i <= m - 1; i++) {
				for (int j = 0; j <= n - 1; j++) {
					System.out.print("     "+vecs[i][j]);
					output.write("     "+String.valueOf(vecs[i][j]));
//					System.out.print("\t");
//					output.write("\t");
				}
				System.out.print("\n");
				output.write("\n");
			}
			System.out.println("   "+nn);
			output.write("   "+nn+"\n");
			System.out.println("Direct");
			output.write("Direct"+"\n");
			double[][] pos = new double[nn][3];
			m = pos.length;
			n = pos[0].length;
//			for (int i = 0; i <= m - 1; i++) {
//				for (int j = 0; j <= n - 1; j++) {
//					pos[i][j] = Math.random();
//					System.out.print(pos[i][j]);
//					output.write(String.valueOf(pos[i][j]));
//					System.out.print("\t");
//					output.write("\t");
//				}
//				System.out.print("\n");
//				output.write("\n");
//			}
			
			System.out.print("  0.2499569795550727  0.7500430204449273  0.0000000000000000");
			output.write("  0.2499569795550727  0.7500430204449273  0.0000000000000000");
			System.out.print("\n");
			output.write("\n");
			System.out.print("  0.7500430204449273  0.2499569795550727  0.0000000000000000");
			output.write("  0.7500430204449273  0.2499569795550727  0.0000000000000000");
			System.out.print("\n");
			output.write("\n");
			System.out.print("  0.2500430643679579  0.2500430643679579  0.5000000000000000");
			output.write("  0.2500430643679579  0.2500430643679579  0.5000000000000000");
			System.out.print("\n");
			output.write("\n");
			System.out.print("  0.7499569356320421  0.7499569356320421  0.5000000000000000");
			output.write("  0.7499569356320421  0.7499569356320421  0.5000000000000000");
			System.out.print("\n");
			output.write("\n");
			
			System.out.print("\n");
			output.write("\n");
			System.out.print("  0.00000000E+00  0.00000000E+00  0.00000000E+00");
			output.write("  0.00000000E+00  0.00000000E+00  0.00000000E+00");
			System.out.print("\n");
			output.write("\n");
			System.out.print("  0.00000000E+00  0.00000000E+00  0.00000000E+00");
			output.write("  0.00000000E+00  0.00000000E+00  0.00000000E+00");
			System.out.print("\n");
			output.write("\n");
			System.out.print("  0.00000000E+00  0.00000000E+00  0.00000000E+00");
			output.write("  0.00000000E+00  0.00000000E+00  0.00000000E+00");
			System.out.print("\n");
			output.write("\n");
			System.out.print("  0.00000000E+00  0.00000000E+00  0.00000000E+00");
			output.write("  0.00000000E+00  0.00000000E+00  0.00000000E+00");
			System.out.print("\n");
			output.write("\n");

			
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public static double runbest(double[] bestpos, double vol, int nn, String jobname){
		double result = 0;
		double[][] vecs = vector_decoder(bestpos, vol);
		writeposcar(vecs, nn, jobname);
		writeincar();
		try {
			Process proc = Runtime.getRuntime().exec("vasp");
			BufferedInputStream in = new BufferedInputStream(
					proc.getInputStream());
			BufferedReader inBr = new BufferedReader(new InputStreamReader(in));
			String lineStr;
			while ((lineStr = inBr.readLine()) != null) {
				// 获得命令执行后在控制台的输出信息
				System.out.println(lineStr);// 打印输出信息
			}
			// 检查命令是否执行失败。
			if (proc.waitFor() != 0) {
				if (proc.exitValue() == 1)// p.exitValue()==0表示正常结束，1：非正常结束
					System.err.println("命令执行失败!");
			}
			String str = "OUTCAR";
			readoutcar rdcar = new readoutcar(str);
			result = rdcar.GetResult();
			System.out.println("+++++++++++++++++++++++++");
			System.out.println("energy: " + result + " ;");// 打印输出信息urn 0;
			inBr.close();
			in.close();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
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
		double[] r = {1.82,1.82,1.82,1.82};
		double[] v = new double[nn];
		double vol = 0;
		for(int i=0;i<=nn-1;i++){
			v[i] = 4*Math.PI*Math.pow(r[i], 3)/3;
			vol+=v[i];
		}
		vol = 1.2*vol;

		
		long startTime = System.currentTimeMillis();
		KMPSO kmpso = new KMPSO();
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
			double[][] bestposes = new double[results.size()][n];
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
			int max_gen = 10;
			int numofparticals = 40;
			int dimention = n;
			double[] intertia = { 0.95, 0.9, 0.95, 0.9, 0.95, 1, 0.95, 0.9,0.95, 0.95, 0.9, 0.95 };
			double[] velocity = { 1, 1.45, 1.85, 1.2, 1.4, 1.15, 1.7, 1.3, 1.8,1.6, 1.1, 1.55 };
			double p_lamda = 0.8;
			double p_extra = 0.002;
			int kmeans_max = 100;
			int kmeans_num = 4;
			double lamda = 0.8;
            	for(int bb=0; bb<=0;bb++){
            		bestposes[0] = kmpso.Calculate(max_gen, numofparticals, dimention, intertia[0],velocity[0], scopes, new VaspMaxFunction(nn, vol, "AutoPOSCAR"), p_lamda, p_extra,  kmeans_max, kmeans_num, lamda, output[0]);
            	}
            	for(BufferedWriter op : output){
    				op.write("\n");
    				op.flush();
    			}
			for(BufferedWriter op : output){
				op.close();
			}
			runbest(bestposes[0], vol, nn, "BESTPOSCAR");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		long endTime = System.currentTimeMillis();
		System.out.println("运行时间 " + (endTime - startTime) + "ms");
		System.out.println("符合模式：" + clustObjectFun.mycount);
		

	}

}
