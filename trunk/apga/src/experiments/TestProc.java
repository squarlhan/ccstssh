package experiments;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class TestProc {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			Process proc = Runtime.getRuntime().exec("ping 202.198.16.3");
			  BufferedInputStream in = new BufferedInputStream(proc.getInputStream());   
	            BufferedReader inBr = new BufferedReader(new InputStreamReader(in));   
	            String lineStr;   
	            while ((lineStr = inBr.readLine()) != null)   
	                //获得命令执行后在控制台的输出信息   
	                System.out.println(lineStr);// 打印输出信息   
	            //检查命令是否执行失败。   
	            if (proc.waitFor() != 0) {   
	                if (proc.exitValue() == 1)//p.exitValue()==0表示正常结束，1：非正常结束   
	                    System.err.println("命令执行失败!");   
	            }   
	            inBr.close();   
	            in.close();   
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("asdfasdf");
		System.out.println("asdfasdf");
		
		try {
			File result = new File("process.txt");
			BufferedWriter output = new BufferedWriter(new FileWriter(result));
			if (result.exists()) {
				result.delete();
				if (result.createNewFile()) {
					System.out.println("result1 file create success!");
				} else {
					System.out.println("result1 file create failed!");
				}
			} else {
				if (result.createNewFile()) {
					System.out.println("result file create success!");
				} else {
					System.out.println("result file create failed!");
				}

			}
			
			for(int i = 0; i<= 29;i++){
				
				System.out.println((i+1));
				output.write((i+1)+" \n");
				output.flush();
			    Thread.sleep(1000);
			}
			output.close();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		System.out.println("My Final!");
	}

}
