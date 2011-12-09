package experiments.fea;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;
import java.nio.*;

public class InputResultData {
	public InputResultData(String string){
		this.string=string;
	}
	public   List InputStrData=new ArrayList();
	private String string;
	private  BufferedReader filereader=null;
	private  String pData;
	private  String[] Data;

	public List GetInputData(String str){
		try{
			filereader=new BufferedReader(new FileReader(string));
			while((pData=filereader.readLine())!=null){
//				Data=pData.split(" ");
//				 System.out.println(Data);
				InputStrData.add(pData);
			}
			filereader.close();
		}catch(IOException ex){
			ex.printStackTrace();
			System.err.println("����ļ������ڣ�");
		}
		InputStrData=DataConverter(str);
		return InputStrData;
	}
	private List DataConverter(String string){
		List ArrayData=new ArrayList();
		Iterator itor=InputStrData.iterator();
		boolean Flag=false;

		while(itor.hasNext()){		
			List SubList=new ArrayList();
			pData=(String)itor.next();
			if(pData.contentEquals(string)){
				Flag=true;
			}
			if(!Flag){
				continue;
			}
	   Matcher m=Pattern.compile("(\\d\\S+)|(\\d)").matcher(pData);
	   boolean flag=false;
	   int j=0;
	   while(m.find()){
		   flag=true;
		   j++;
		   SubList.add(m.group());   
	   }
//	   System.out.println(SubList);
	   if(flag){
		   if(j>1){
	   ArrayData.add(SubList);
		   }
	   }
		}
		
		return ArrayData;
	}
}
