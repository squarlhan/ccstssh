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
		writeincar();
		String str="OUTCAR";
		double result;
		 readoutcar rdcar=new readoutcar(str);
		 result=rdcar.GetResult();
		 System.out.print(result);
	}

}
