package experiments.fea;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;
import java.nio.*;

public class InputStructureData {
public InputStructureData(String string){
	this.string=string;
}
public   List InputStrData=new ArrayList();
private String string;
private  BufferedReader filereader=null;
private  String pData;
private  String[] Data;

public List GetInputData(){
	try{
		filereader=new BufferedReader(new FileReader(string));
		while((pData=filereader.readLine())!=null){
//			Data=pData.split(" ");
//			 System.out.println(Data);
			InputStrData.add(pData);
		}
		filereader.close();
	}catch(IOException ex){
		ex.printStackTrace();
		System.err.println("����ļ������ڣ�");
	}
	InputStrData=DataConverter();
	return InputStrData;
}
private List DataConverter(){
	List ArrayData=new ArrayList();
	Iterator itor=InputStrData.iterator();

	while(itor.hasNext()){		
		List SubList=new ArrayList();
		pData=(String)itor.next();
   Matcher m=Pattern.compile("(\\d\\S+)|(\\d)|(\\-\\d\\S+)").matcher(pData);
   boolean flag=false;
   while(m.find()){
	   flag=true;
	   SubList.add(m.group());   
   }
//   System.out.println(SubList);
   if(flag){
   ArrayData.add(SubList);
   }
	}
	
	return ArrayData;
}
}
