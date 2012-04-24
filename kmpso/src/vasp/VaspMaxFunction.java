/*
 * This file is part of JGAP.
 *
 * JGAP offers a dual license model containing the LGPL as well as the MPL.
 *
 * For licensing information please see the file license.txt included with JGAP
 * or have a look at the top of class org.jgap.Chromosome which representatively
 * includes the JGAP license policy applicable for any file delivered with JGAP.
 */
package vasp;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pso.FitnessFunction;


/**
 * Fitness function for our example. See evaluate(...) method for details.
 *
 * @author Neil Rotstan
 * @author Klaus Meffert
 * @since 2.0
 */
public class VaspMaxFunction 
    extends FitnessFunction{
  /** String containing the CVS revision. Read out via reflection!*/
  private final static String CVS_REVISION = "$Revision: 1.6 $";
  
  public static int counts = 0;
  private int nn; //原子个数
  private double vol ; //体积
  private String jobname; //任务名
  
  

  public VaspMaxFunction(int nn, double vol, String jobname) {
	super();
	this.nn = nn;
	this.vol = vol;
	this.jobname = jobname;
}
public int getNn() {
	return nn;
}
public void setNn(int nn) {
	this.nn = nn;
}
public double getVol() {
	return vol;
}
public void setVol(double vol) {
	this.vol = vol;
}
public String getJobname() {
	return jobname;
}
public void setJobname(String jobname) {
	this.jobname = jobname;
}

	public boolean isDouble(String testString) {
		// 表达式的功能：验证必须为数字（整数或小数）
		String pattern = "[0-9]+(.[0-9]+)?";
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(testString);
		boolean b = m.matches();
		return b;

	}

/**
   *
   * @param a_subject the Chromosome to be evaluated
   * @return defect rate of our problem
   */
public double evaluate(double position[]) {
    double result = 0;
    int time_delay = 0;
    try {
		Thread.sleep(time_delay);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    result = runvasp(position);
    
    counts ++;
    return -1*result;
  }
  
/**
 * 
 * @param a_subject生成POSCAR，运行vasp，并得到最小的能量
 * @return
 */
  @SuppressWarnings("null")
public double runvasp(double position[]){
	  double result = 0;
	  double[][] vecs = vector_decoder(position);
	  if(vecs==null){
		  System.out.println("Decoding Error!");
		  return 0;
	  }
	    this.writeposcar(vecs);
	    
		try {
				Process proc = Runtime.getRuntime().exec("vasp");
				BufferedInputStream in = new BufferedInputStream(proc.getInputStream());
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
				Process proc1 = Runtime.getRuntime().exec(" awk '/free energy    TOTEN/{print $5;}' OUTCAR |tail -1");
//				BufferedInputStream in1 = new BufferedInputStream(proc1.getInputStream());
//				BufferedReader inBr1 = new BufferedReader(new InputStreamReader(in1));
				 InputStreamReader in1= new InputStreamReader(proc1.getInputStream());
		         LineNumberReader inBr1 = new LineNumberReader (in1);
				String lineStr1 = "";
				while (inBr1.readLine() != null) {
					// 获得命令执行后在控制台的输出信息
					lineStr1 = inBr1.readLine();
					System.out.println("linestr:"+lineStr1+".");// 打印输出信息
				}
				System.out.println("**************************");
				System.out.println("linestr:"+lineStr1+";");
				if(lineStr1!=null&&lineStr1.trim().length()>1&&isDouble(lineStr1.trim())){
					result = Double.parseDouble(lineStr1.trim());
					System.out.println("RRRRRRRRRRRRRRRRRRRRR");
					System.out.println("energy:"+result+";");// 打印输出信息
				}else{
					return 0;
				}
				// 检查命令是否执行失败。
				if (proc1.waitFor() != 0) {
					if (proc1.exitValue() == 1)// p.exitValue()==0表示正常结束，1：非正常结束
						System.err.println("命令执行失败!");
				}
				inBr.close();
				in.close();
				inBr1.close();
				in1.close();
	} catch (InterruptedException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	    return result;
  }
  /**
   * 解析染色体成晶格的矢量坐标
   * @param chrom
   * @return
   */
  public double[][] vector_decoder(double position[]) {
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
				  if(Double.isNaN(results[i][j]))return null;
			}
		}

		
		return results;
	}
  /**
   * 生成POSCAR文件
   * @param vecs 晶格 晶失坐标
   */
  public void writeposcar(double[][] vecs) {
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
}
