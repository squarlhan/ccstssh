package experiments.fea;
import java.util.*;
import java.util.regex.Pattern;
import java.io.*;
import java.nio.*;

public class OutputInFortran {
	private final List list;
	public OutputInFortran(List list){
		this.list=list;
	}
	public void OutputtoFile(){
		try{
		PrintWriter  Opt=new PrintWriter(new FileWriter("stap90.in"));
		Opt.println("a test to STAP90");
//		Pattern pattern = Pattern.compile("[0-9]+");
		Iterator Oitor=list.iterator();
		while(Oitor.hasNext()){
			Iterator Subitor=((List)Oitor.next()).iterator();
			while(Subitor.hasNext()){
				Object str=Subitor.next();
				if(str instanceof Integer){
					int i=(Integer)str;
					Opt.printf("%5d", i);
				}
				else{
					double d=(Double)str;
					Opt.printf("%10.3e", d);
				}
//				Opt.print("          "+(Object)Subitor.next());
			}
			Opt.println();
		}
		Opt.println("stop");
		Opt.close();
		}catch (Exception e) {
			   e.printStackTrace();
		  }

	}
}
