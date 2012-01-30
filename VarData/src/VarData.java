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
import java.util.List;


public class VarData {
	
	public List<List<Double>> readdata(String addr){
		List<List<Double>> result= new ArrayList();
		File file = new File(addr);
		try {
			InputStreamReader insr = new InputStreamReader(new FileInputStream(file), "gb2312");
			BufferedReader br = new BufferedReader(insr);
			String line;
			while ((line = br.readLine()) != null) {
				line = line.trim();
				if (line.length() >= 1) 
				{				 
					String[] lines = line.split("\t");
					List<Double> oneline = new ArrayList();
					for(String lstr:lines){
						oneline.add(Double.parseDouble(lstr));
					}
					result.add(oneline);
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
	
	public void writefile(List<Double> result, String addr){
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
			for(Double d:result){
				output.write(d+"\n");
			}
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
public Double mean(List<Double> chas){
		Double sum = 0.0;
		for(Double d:chas){
			sum+=d;
		}
		return sum/chas.size();
	}
	
	public Double var(List<Double> chas){
		Double sum = 0.0;
		Double md = mean(chas);
		for(Double d:chas){
			sum = sum + Math.pow(d-md,2.0);
		}
		return sum/chas.size();
	}
	
	public Double mse(List<Double> chas){
		Double sum = 0.0;
		for(Double d:chas){
			sum = sum + Math.pow(d,2.0);
		}
		return sum/chas.size();
	}
	
	public void process(String inputaddr, String outputaddr){
		List<List<Double>> mydata = readdata(inputaddr);
		List<Double> result = new ArrayList();
		for(List<Double> chas: mydata){
			result.add(Math.sqrt(mse(chas)));
		}
		writefile(result, outputaddr);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		VarData vardata = new VarData();
//		List<String> addrs = new ArrayList();
//		addrs.add("in/mse_ap_ackley.txt");
//		addrs.add("in/mse_ap_cos.txt");
//		addrs.add("in/mse_ap_gri.txt");
//		addrs.add("in/mse_ap_non.txt");
//		addrs.add("in/mse_ap_pen1.txt");
//		addrs.add("in/mse_ap_pen2.txt");
//		addrs.add("in/mse_ap_quar.txt");
//		addrs.add("in/mse_ap_rosen.txt");
//		addrs.add("in/mse_ap_sch.txt");
//		addrs.add("in/mse_ap_step.txt");
//		addrs.add("in/mse_ap_wei.txt");
//		addrs.add("in/mse_ap_x.txt");
//
//		addrs.add("in/mse1_kmeans_ackley.txt");
//		addrs.add("in/mse1_kmeans_cos.txt");
//		addrs.add("in/mse1_kmeans_gri.txt");
//		addrs.add("in/mse1_kmeans_non.txt");
//		addrs.add("in/mse1_kmeans_pen1.txt");
//		addrs.add("in/mse1_kmeans_pen2.txt");
//		addrs.add("in/mse1_kmeans_quar.txt");
//		addrs.add("in/mse1_kmeans_rosen.txt");
//		addrs.add("in/mse1_kmeans_sch.txt");
//		addrs.add("in/mse1_kmeans_step.txt");
//		addrs.add("in/mse1_kmeans_wei.txt");
//		addrs.add("in/mse1_kmeans_x.txt");
//		
//		addrs.add("in/mse2_kmeans_ackley.txt");
//		addrs.add("in/mse2_kmeans_cos.txt");
//		addrs.add("in/mse2_kmeans_gri.txt");
//		addrs.add("in/mse2_kmeans_non.txt");
//		addrs.add("in/mse2_kmeans_pen1.txt");
//		addrs.add("in/mse2_kmeans_pen2.txt");
//		addrs.add("in/mse2_kmeans_quar.txt");
//		addrs.add("in/mse2_kmeans_rosen.txt");
//		addrs.add("in/mse2_kmeans_sch.txt");
//		addrs.add("in/mse2_kmeans_step.txt");
//		addrs.add("in/mse2_kmeans_wei.txt");
//		addrs.add("in/mse2_kmeans_x.txt");
//		
//		addrs.add("in/mse_i_ackley.txt");
//		addrs.add("in/mse_i_cos.txt");
//		addrs.add("in/mse_i_gri.txt");
//		addrs.add("in/mse_i_non.txt");
//		addrs.add("in/mse_i_pen1.txt");
//		addrs.add("in/mse_i_pen2.txt");
//		addrs.add("in/mse_i_quar.txt");
//		addrs.add("in/mse_i_rosen.txt");
//		addrs.add("in/mse_i_sch.txt");
//		addrs.add("in/mse_i_step.txt");
//		addrs.add("in/mse_i_wei.txt");
//		addrs.add("in/mse_i_x.txt");
//		
//		for(String addr:addrs){
//			vardata.process(addr, "out/r_"+addr.substring(3));
//		}

		vardata.process("10_ap_fet.txt", "r_10_ap_fet.txt");

	}

}
