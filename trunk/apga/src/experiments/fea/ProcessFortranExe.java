package experiments.fea;
import java.util.*;
import java.io.*;
import java.nio.*;

import org.omg.*;
public class ProcessFortranExe {
public boolean Fortran(){
	Runtime rt=Runtime.getRuntime();
	Process process=null;
	boolean flag=false;
	try{
		 
		String command="stap90.exe";
		process=rt.exec(command);
		 BufferedInputStream in = new BufferedInputStream(process.getInputStream());   
         BufferedReader inBr = new BufferedReader(new InputStreamReader(in));   
		String lineStr;   
		 System.out.println("123456789");
        while ((lineStr = inBr.readLine()) != null){   
            //�������ִ�к��ڿ���̨�������Ϣ   
        	System.out.println("123456789");
            System.out.println(lineStr);// ��ӡ�����Ϣ   
        //��������Ƿ�ִ��ʧ�ܡ�   
        }
        if (process.waitFor() != 0) {   
            if (process.exitValue() == 1)//p.exitValue()==0��ʾ�����1���������   
                System.err.println("����ִ��ʧ��!");   
        }   
        inBr.close();   
        in.close();   
		flag=true;
	}catch(Exception e){
		System.err.println("Error: stap90 execture error!");
		e.printStackTrace();
	}

	return flag;
}
}
