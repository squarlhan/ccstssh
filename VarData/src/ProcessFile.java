import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class ProcessFile {
	
	
	public List<String> names;
	
	
	
	public List<String> getNames() {
		return names;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}

	public List<String> readdata(String addr){
		List<String> result= new ArrayList();
		File file = new File(addr);
		try {
			InputStreamReader insr = new InputStreamReader(new FileInputStream(file), "gb2312");
			BufferedReader br = new BufferedReader(insr);
			String line;
			while ((line = br.readLine()) != null) {
				line = line.trim();
				if (line.length() >= 1) 
				{
					result.add(line);
				}
			}
			br.close();
			insr.close();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public List<Double> readdatad(String addr){
		List<Double> result= new ArrayList();
		File file = new File(addr);
		try {
			InputStreamReader insr = new InputStreamReader(new FileInputStream(file), "gb2312");
			BufferedReader br = new BufferedReader(insr);
			String line;
			while ((line = br.readLine()) != null) {
				line = line.trim();
				if (line.length() >= 1) 
				{
					result.add(Double.parseDouble(line));
				}
			}
			br.close();
			insr.close();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public void writefile(List<String> result, String addr){
		try {
			File file = new File(addr);
			if (file.exists()) {
				file.delete();
				if (file.createNewFile()) {
					System.out.println("result file create success!");
				} else {
					System.out.println("result file create failed!");
				}
			} else {
				if (file.createNewFile()) {
					System.out.println("result file create success!");
				} else {
					System.out.println("result file create failed!");
				}

			}

			BufferedWriter output = new BufferedWriter(new FileWriter(file));
			for(String d:result){
				output.write(d+"\n");
			}
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getallnames(List<String> input){
		names = new ArrayList();
		String  outname = "";
		for(String in:input){
			if(in.contains("VECTOR")){
				
				outname = "";
				String[] strarr = in.trim().split(" ");
				if(strarr.length>=2){
					outname = outname+strarr[strarr.length-1].trim();
				}
			}else if(in.contains("WELL")||in.contains("FIELD")||in.contains("GATHER")){
				String[] strarr = in.trim().split(" ");
				if(strarr.length>=2){
					outname = outname+"_"+strarr[strarr.length-1].trim();
					this.getNames().add(outname);
				}
			}else {
			}
			
		}
		
	}
	
	public void writescript(String addr, String ap, String ga, String std){
		try {
			File file = new File(addr);
			if (file.exists()) {
				file.delete();
				if (file.createNewFile()) {
					System.out.println("result file create success!");
				} else {
					System.out.println("result file create failed!");
				}
			} else {
				if (file.createNewFile()) {
					System.out.println("result file create success!");
				} else {
					System.out.println("result file create failed!");
				}

			}

			BufferedWriter output = new BufferedWriter(new FileWriter(file));
			for(String d:names){
				if (!d.contains("TIME")) {
					String strarr[] = d.trim().split("_");
					String xx = strarr[0]+"TIME"+"_"+strarr[strarr.length-1];
					String yy = strarr[1];
					output.write(xx + " = load('" + std + "/" + std + "_" + xx + ".txt');" + "\n");
					output.write(ap + "_" + d + " = load('" + ap + "/" + ap + "_" + d + ".txt');" + "\n");
					output.write(ga + "_" + d + " = load('" + ga + "/" + ga + "_" + d + ".txt');" + "\n");
					output.write(std + "_" + d + " = load('" + std + "/" + std + "_" + d + ".txt');" + "\n");
					output.write("[x,y] = size(" + ap + "_" + d + ");" + "\n");
					output.write("xx="+xx+"(1:x);" + "\n");
					output.write("plot(xx," + ap + "_" + d + ",'+',xx," + ga + "_" + d + ",'-.',xx," + std + "_" + d + "(1:x),'o');" + "\n");
					output.write("legend('APGA', 'PureGa','Standard');" + "\n");
					output.write("title(strrep('" + d + "','_','\\_'));" + "\n");
					output.write("xlabel('TIME');ylabel('"+yy+"');print -dpng '" + d + "';" + "\n");
					output.flush();
				}
			}
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void writemse(List<Map.Entry<String, Double>> mses, String addr){
		try {
			File file = new File(addr);
			if (file.exists()) {
				file.delete();
				if (file.createNewFile()) {
					System.out.println("result file create success!");
				} else {
					System.out.println("result file create failed!");
				}
			} else {
				if (file.createNewFile()) {
					System.out.println("result file create success!");
				} else {
					System.out.println("result file create failed!");
				}

			}

			BufferedWriter output = new BufferedWriter(new FileWriter(file));
			for (int i = 0; i <= mses.size()  - 1; i++) {
//				output.write(mses.get(i).getKey()+"\t");
//				output.write(mses.get(i).getValue()+"\n");
				output.write("copy pics\\"+mses.get(i).getKey()+".png mse\\"+String.valueOf(i+1)+"_"+mses.get(i).getKey()+".png"+"\r\n");
			}
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void process(List<String> input, String type){
		List<String> temp_out = new ArrayList();
		String  outname = "";
		for(String in:input){
			if(in.contains("VECTOR")){
				if(temp_out.size()>0){
					writefile(temp_out, outname);
				    temp_out = new ArrayList();
				}
				outname = type;
				String[] strarr = in.trim().split(" ");
				if(strarr.length>=2){
					outname = outname+"_"+strarr[strarr.length-1].trim();
				}
			}else if(in.contains("WELL")||in.contains("FIELD")||in.contains("GATHER")){
				String[] strarr = in.trim().split(" ");
				if(strarr.length>=2){
					outname = outname+"_"+strarr[strarr.length-1].trim()+".txt";
				}
			}else if(in.contains("UNITS")){
				
			}else{
				temp_out.add(in);
			}
		}
		writefile(temp_out, outname);
	}

	public Map<String, Double> getmselist(String ap, String ga, String std){
		Map<String, Double> mses = new HashMap<String, Double>();
		for (String name : names) {
			if (!name.contains("TIME")) {
				String ap_file = ap + "/" + ap + "_" + name + ".txt";
				// String ga_file = ga+"/"+ga+"_"+name+".txt";
				String std_file = std + "/" + std + "_" + name + ".txt";
				List<Double> ap_data = readdatad(ap_file);
				List<Double> std_data = readdatad(std_file);
				int s = ap_data.size();
				double sum = 0;
				double subs = 0;
				for (int i = 0; i <= s - 1; i++) {
					sum = sum + Math.pow(ap_data.get(i) - std_data.get(i), 2);
					if (s - 1 >= i + 1) {
						subs = subs + std_data.get(i + 1) - std_data.get(i);
					}

				}
				double mse = Math.sqrt(sum / s);
				if (subs > 1 && mse > 0) {
					mses.put(name, mse);
				}
			}
		}
		List<Map.Entry<String, Double>> mappingList = null;
		// 通过ArrayList构造函数把map.entrySet()转换成list
		mappingList = new ArrayList<Map.Entry<String, Double>>(mses.entrySet());
		// 通过比较器实现比较排序
		Collections.sort(mappingList,
				new Comparator<Map.Entry<String, Double>>() {
					public int compare(Map.Entry<String, Double> mapping1,
							Map.Entry<String, Double> mapping2) {
						return mapping1.getValue().compareTo(
								mapping2.getValue());
					}
				});
		writemse(mappingList, "mse.bat");
		return mses;
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ProcessFile pf = new  ProcessFile();
//		pf.process(pf.readdata("E:/APGA/历史拟合/pbrs-实际.USER"), "std");
//		pf.process(pf.readdata("E:/APGA/历史拟合/2dgtz2011100925--标准.infc"), "ga");
//		pf.process(pf.readdata("E:/APGA/历史拟合/2dgtz2011100925-聚类.infc"), "ap");

		pf.getallnames(pf.readdata("E:/APGA/历史拟合/pbrs-实际.USER"));
		pf.getmselist("ap", "ga", "std");
//		pf.writescript("tongji.m", "ap", "ga", "std");
	}

}
