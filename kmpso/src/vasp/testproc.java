package vasp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

public class testproc {
	
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
				  if(Double.isNaN(results[i][j])){
					  return null;
				  }
			}
		}

		return results;
	}
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
//		Process proc1 = Runtime.getRuntime().exec(" awk '/free energy    TOTEN/{print $5;}' OUTCAR |tail -1");
//		Process proc1 = Runtime.getRuntime().exec("ps");
//		BufferedInputStream in1 = new BufferedInputStream(proc1.getInputStream());
//		BufferedReader inBr1 = new BufferedReader(new InputStreamReader(in1));
//		 InputStreamReader in1= new InputStreamReader(proc1.getInputStream());
//         LineNumberReader inBr1 = new LineNumberReader (in1);
//		String lineStr1 = "";
//		while (inBr1.readLine() != null) {
//			// 获得命令执行后在控制台的输出信息
//			lineStr1 = inBr1.readLine();
//			System.out.println("linestr:"+lineStr1+".");// 打印输出信息
//		}
//		inBr1.close();
//		in1.close();
//		writeincar();
//		String str="OUTCAR";
//		double result;
//		 readoutcar rdcar=new readoutcar(str);
//		 result=rdcar.GetResult();
//		 System.out.print(result);
		int nn = 4;
		double[] ins = new double[6];
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
		ins[0] = 0.1643626803127769; 
		ins[1] = 0.5408592607690118; 
		ins[2] = 0.0985749192902503; 
		ins[3] = 0.288750694206369; 
		ins[4] = 0.413897603365761; 
		ins[5] = 0.4889969804661154;
		
		double[][] vecs = vector_decoder(doublearr2Double(ins), vol);
		System.out.println(vecs);
		while(vecs!=null){
			for (int i = 0; i <= 5; i++) {
				ins[i] = Math.random();
			}
			vecs = vector_decoder(doublearr2Double(ins), vol);
		}
		System.out.println(ins);
	}
	
	public static Double[] doublearr2Double(double[] ins){
		Double[] inin = new Double[ins.length];
		for(int i = 0; i<=ins.length-1;i++){
			inin[i] = Double.valueOf(ins[i]);
		}
		return inin;
	}

}
