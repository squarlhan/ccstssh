package experiments.ga;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jgap.IChromosome;

import experiments.Matrix;
import experiments.apga.APGA;
import experiments.apga.clustObjectFun;

public class TestTest {

	public static double[][] vector_decoder(IChromosome chrom, double vol) {
		int csize = chrom.size();
		if (csize < 6) {
			System.err.println("wrong chromosome size input!");
			return null;
		}
		double[] input = new double[6];
		for (int i = 0; i <= 2; i++) {
			double genevalue = (Double) chrom.getGene(i).getAllele();
			input[i] = (Math.PI/6) + genevalue * ((5*Math.PI/6) -(Math.PI/6));
		}
		input[3] = (Double) chrom.getGene(3).getAllele();
		double max = 1;
		double min = input[3] / 4;
		if (4 * input[3] < 1)
			max = 4 * input[3];
		input[4] = (Double) chrom.getGene(4).getAllele();
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
		input[5] = (Double) chrom.getGene(5).getAllele();
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
		APGA a1 = new APGA();
		int m = 40;
		int n = 30;
		Matrix consValue5 = new Matrix(2, n);
		Matrix consValue32 = new Matrix(2, n);
		Matrix consValue30 = new Matrix(2, n);
		Matrix consValue50 = new Matrix(2, n);
		Matrix consValue100 = new Matrix(2, n);
		Matrix consValue512 = new Matrix(2, n);
		Matrix consValue500 = new Matrix(2, n);
		Matrix consValue600 = new Matrix(2, n);
		Matrix lastPos5 = new Matrix(m, n);
		Matrix lastPos32 = new Matrix(m, n);
		Matrix lastPos30 = new Matrix(m, n);
		Matrix lastPos50 = new Matrix(m, n);
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
		Matrix pBestpen1 = new Matrix(1, m);
		Matrix pBestpen2 = new Matrix(1, m);
		Matrix pBestwei = new Matrix(1, m);
		Matrix pBestnon = new Matrix(1, m);
		for(int i = 0; i<=n-1;i++ ){
			consValue5.data[0][i] = -0.5;
			consValue5.data[1][i] = 0.5;
			consValue30.data[0][i] = -30;
			consValue30.data[1][i] = 30;
			consValue32.data[0][i] = -32;
			consValue32.data[1][i] = 32;
			consValue50.data[0][i] = -50;
			consValue50.data[1][i] = 50;
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
				lastPos5.data[i][j] = Math.random()-0.5;
				lastPos30.data[i][j] = Math.random()*60-30;
				lastPos32.data[i][j] = Math.random()*64-32;
				lastPos50.data[i][j] = Math.random()*100-50;
				lastPos100.data[i][j] = Math.random()*200-100;
				lastPos512.data[i][j] = Math.random()*10.24-5.12;
				lastPos500.data[i][j] = Math.random()*1000-500;
				lastPos600.data[i][j] = Math.random()*1200-600;
			}
		}
		double a = 0.5;
	    double b = 3;
	    int kmax = 20;
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
			double totalpen1 = 0;
			double totalpen2 = 0;
			double totalu1 = 0;
			double totalu2 = 0;
			double totalwei = 0;
			double totalnon = 0;
			double totalnon2 = 0;
			double[] y = new double[n];
			double yy = 0;
			for(int j = 0; j<=n-1; j++){
				totalacley +=  (lastPos32.data[i][j]*lastPos32.data[i][j]);
				totalcos += (Math.cos(2*Math.PI*lastPos32.data[i][j]));
				totalcos0+=(lastPos512.data[i][j]*lastPos512.data[i][j]-10*Math.cos(2*lastPos512.data[i][j]*Math.PI)+10);
				totalx+=Math.pow(lastPos100.data[i][j], 2.0);
				if(totalmax<Math.abs(lastPos100.data[i][j]))totalmax=Math.abs(lastPos100.data[i][j]);
				totalstep+=(Math.floor(lastPos100.data[i][j]+0.5)*Math.floor(lastPos100.data[i][j]+0.5));
		    	y[j] = y(lastPos50.data[i][j]);
				if(j<=n-1-1){
					y[j+1] = y(lastPos50.data[i][j+1]);
					totalrosen+=(100*Math.pow((lastPos30.data[i][j+1]-Math.pow(lastPos30.data[i][j], 2)), 2)+Math.pow((lastPos30.data[i][j]-1), 2));
					totalpen1+=((y[j]-1)*(y[j]-1)*(1+10*Math.sin(Math.PI*y[j+1])*Math.sin(Math.PI*y[j+1])));
					totalpen2+=((lastPos50.data[i][j]-1)*(lastPos50.data[i][j]-1)*(1+Math.sin(3*Math.PI*lastPos50.data[i][j+1])*Math.sin(3*Math.PI*lastPos50.data[i][j+1])));
				}
				totalsch+=(-1*lastPos500.data[i][j]*Math.sin(Math.sqrt(Math.abs(lastPos500.data[i][j]))));
				totalgri+=(Math.pow(lastPos600.data[i][j], 2));
				prodgri*=(Math.cos(lastPos600.data[i][j]/Math.sqrt(j+1)));
				totalu1+=u(lastPos50.data[i][j],10,100,4);
				totalu2+=u(lastPos50.data[i][j],5,100,4);
				if(Math.abs(lastPos512.data[i][j])<0.5){
		        	yy = lastPos512.data[i][j];
		        }else{
		        	yy = Math.round(2*lastPos512.data[i][j])/2;
		        }
				totalwei +=  (yy*yy-10*Math.cos(2*yy*Math.PI)+10);
				double totalnon1 = 0;
				for(int k = 0; k<=kmax;k++){
					totalnon1 += (Math.pow(a, k)*Math.cos(2*Math.PI*Math.pow(b, k)*(lastPos5.data[i][j]+0.5)));
		        	totalnon2 += (Math.pow(a, k)*Math.cos(2*Math.PI*Math.pow(b, k)*0.5));
		        }
		    	totalnon +=  totalnon1;
			}
			pBestackley.data[0][i] = 20*Math.exp(-0.2*Math.sqrt(totalacley/n))+Math.exp(totalcos/n);
			pBestcos.data[0][i] = n*41-totalcos0;
			pBestx.data[0][i] = n*Math.pow(10, 2.0)-totalx;
			pBeststep.data[0][i] = n*Math.pow(10, 2.0)-totalstep;
			pBestmax.data[0][i] = 100-totalmax;
			pBestrosen.data[0][i] = (1999999999-totalrosen)<=0?1:1999999999-totalrosen;
			pBestsch.data[0][i] = (838*n-418.9829*n-totalsch)<=0?1:838*n-418.9829*n-totalsch;
			pBestgri.data[0][i] = (2701-1-totalgri/4000+prodgri)<=0?1:2701-1-totalgri/4000+prodgri;
			double tempen1 = Math.PI/n*(10*Math.sin(Math.PI*y[0])*Math.sin(Math.PI*y[0])+totalpen1+(y[n-1]-1)*(y[n-1]-1))+totalu1;
			pBestpen1.data[0][i] = (1999999999-tempen1)<=0?1:1999999999-tempen1;
			double tempen2 = 0.1*(Math.sin(3*Math.pow(Math.PI*lastPos50.data[i][0], 2))+totalpen2+Math.pow(lastPos50.data[i][n-1]-1, 2))+totalu2;
			pBestpen2.data[0][i] = (1999999999-tempen2)<=0?1:1999999999-tempen2;
			pBestwei.data[0][i] = n*40.25-totalwei;
			pBestnon.data[0][i] = n*4-totalnon+totalnon2;
		}
		
		try {
			String prefix = "lamda_50_";
			List<File> results = new ArrayList();
			
			File result0 = new File(prefix+"ap_x.txt");
			File result1 = new File(prefix+"ap_cos.txt");
			File result2 = new File(prefix+"ap_ackley.txt");
			File result3 = new File(prefix+"ap_quar.txt");
			File result4 = new File(prefix+"ap_step.txt");
			File result5 = new File(prefix+"ap_rosen.txt");
			File result6 = new File(prefix+"ap_sch.txt");
			File result7 = new File(prefix+"ap_gri.txt");
			File result8 = new File(prefix+"ap_pen1.txt");
			File result9 = new File(prefix+"ap_pen2.txt");
			File result10 = new File(prefix+"ap_wei.txt");
			File result11 = new File(prefix+"ap_non.txt");
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
			
			double lamda =0.0;
			double extra =0.01;
//			for(int aa=0; aa<=0;aa++){
//				long time1 = System.currentTimeMillis();
//				a1.Calculate(new ThreadAckleyMaxFunction(), 0.8, extra, 100.0, 0.8, lamda, consValue32, lastPos32, pBestackley, 200, output[2]);
//				long time2 = System.currentTimeMillis();
//				a1.Calculate(new AckleyMaxFunction(), 0.8, extra, 100.0, 0.8, lamda, consValue32, lastPos32, pBestackley, 200, output[2]);
//				long time3 = System.currentTimeMillis();
//				System.out.println("并行运行时间 " + (time2 - time1) + "ms");
//				System.out.println("运行时间 " + (time3 - time2) + "ms");
//				output[2].write("并行运行时间 " + (time2 - time1) + "ms \n");
//				output[2].write("运行时间 " + (time3 - time2) + "ms \n");
            while(lamda<=2.05){
            	for(int bb=0; bb<=49;bb++){
				a1.Calculate(new MaxFunction(), 0.8, extra, 100.0, 0.8,lamda, consValue100, lastPos100, pBestx, 200, output[0]);
//				a1.Calculate(new MaxFunction(), 0.8, extra, 100.0, 0.8,  0.8, consValue100, lastPos100, pBestx, 200, output[0]);
            	}
            	for(BufferedWriter op : output){
    				op.write("\n");
    				op.flush();
    			}
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
