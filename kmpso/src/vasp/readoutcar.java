package vasp;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;
import java.nio.*;

public class readoutcar {
	private String string;
	private List RDoutcar=new ArrayList();
	private BufferedReader filereader=null;
	private  String pData;
	private  String[] Data;
public readoutcar(String string){
	this.string=string;
}
public double GetResult(){
	double res=0.0;
	try{
		filereader=new BufferedReader(new FileReader(string));
		while((pData=filereader.readLine())!=null){
     		Data=pData.split(" ");
     		String[] temp=new String[Data.length];
     		int j=0;
     		for(int i=0;i<Data.length;i++){
     		        int len=Data[i].length();
     		        char[] ch=new char[len+1];
     		        ch=Data[i].toCharArray();
     		        for(int k=0;k<len;k++){
     		        	if(ch[k]!=' '){
     		        		temp[j++]=Data[i];
     		        		break;
     		        	}
     		        }  		
     		}
     		Data=temp;
     		if(Data.length>=4){
//     			System.out.println(Data[0]+"*"+Data[1]+"*"+Data[2]+"*"+Data[3]);
     		
     		if(Data[0].equals("free")&&Data[1].equals("energy")&&Data[2].equals("TOTEN")){
//     			System.out.println(pData);
     			if(Data[4]!=null){
     				res=Double.parseDouble(Data[4]);
//     				System.out.print(res+"  ");
     				if(res<=0.001&&res>=-0.001){
     					while((pData=filereader.readLine())!=null){
     					Data=pData.split(" ");
     					if(Data[0].equals("energy")&&Data[1].equals("without")&&Data[2].equals("entropy")){
     						break;
     					}
     				  }
     					res=Double.parseDouble(Data[3]);
     				}
     			}
     		}
		}
//			 System.out.println(Data);
			RDoutcar.add(pData);
		}
		filereader.close();
	}catch(IOException ex){
		ex.printStackTrace();
		System.err.println("ssss");
	}
	
	
	return res;
}
}
