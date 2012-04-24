package vasp;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

public class testproc {

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
		String str="OUTCAR";
		double result;
		 readoutcar rdcar=new readoutcar(str);
		 result=rdcar.GetResult();
		 System.out.print(result);
	}

}
