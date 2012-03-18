package processor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class Matcher {

	public List<List<String>> getFreqItemSet(String fiaddr, int cutoff) {
		List<List<String>> result = new ArrayList();
		File file = new File(fiaddr);
		InputStreamReader insr;
		try {
			insr = new InputStreamReader(new FileInputStream(file), "gb2312");

			BufferedReader br = new BufferedReader(insr);
			String line;
			while ((line = br.readLine()) != null) {
				line = line.trim();
				if (line.length() >= 1) {
					String[] lines = line.split(":");
					if (lines.length == 2) {
						int supp = Integer.parseInt(lines[1].trim());
						if(supp>=cutoff){
							String[] items = lines[0].trim().split("\\|");
							List<String> item_list = new ArrayList();
							for(String item:items){
								if(item!=null&&item.trim().length()>=1){
									item_list.add(item.trim());
								}
								
							}
							result.add(item_list);
						}

					}
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
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	
	public List<String> getSeqFile(String addr) {
		List<String> result = new ArrayList();
		File file = new File(addr);
		InputStreamReader insr;
		try {
			insr = new InputStreamReader(new FileInputStream(file), "gb2312");

			BufferedReader br = new BufferedReader(insr);
			String line;
			while ((line = br.readLine()) != null) {
				line = line.trim();
				if (line.length() >= 1) {
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
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public List<List<String>> getNewFile(String addr) {
		List<List<String>> result = new ArrayList();
		File file = new File(addr);
		InputStreamReader insr;
		try {
			insr = new InputStreamReader(new FileInputStream(file), "gb2312");

			BufferedReader br = new BufferedReader(insr);
			String line;
			while ((line = br.readLine()) != null) {
				line = line.trim();
				if (line.length() >= 1) {
					String[] lines = line.split(",");
					if (lines.length >=1) {
						List<String> item_list = new ArrayList();
						for(String item:lines){
							if(item!=null&&item.trim().length()>=1){
								item_list.add(item.trim());
							}							
						}
						result.add(item_list);
					}
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
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public int mymatcher(List<List<String>> mypattern, List<List<String>> myfile){
		int count = 0;
		for(List<String> p_item:mypattern){
			for(List<String> f_item:myfile){
				if(f_item.containsAll(p_item)){
					count++;
				}
			}
		}
		return count;
	}
	
	public List<List<String>> randomsc(List<String> myseq){
		List<List<String>> result = new ArrayList();
		int len = (int)(450+Math.random()*100);
		int slen = myseq.size();
		List<Integer> indexes = new ArrayList();
		for(int i=1;i<=slen;i++){
			double a = Math.random();
			double b = (double)len/slen;
			if(a>b){
				indexes.add(0);
			}else{
				indexes.add(1);
			}
		}
		List<String> sub= new ArrayList();
		for(int i=0;i<=indexes.size()-1;i++){
			if(indexes.get(i)==1){
				if(sub.size()>=1){
					result.add(sub);
				}
				sub = new ArrayList();
				sub.add(myseq.get(i));
			}else{
				sub.add(myseq.get(i));
			}
		}
		if(indexes.get(0)!=1){
			result.get(0).addAll(result.get(result.size()-1));
			result.remove(result.size()-1);
		}
		return result;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Matcher matcher = new Matcher();
		List<List<String>> mypattern = matcher.getFreqItemSet("freitem.txt", 30);
		List<String> myseq = matcher.getSeqFile("seq2549.txt");
		for (int i = 1; i <= 8; i++) {
//			List<List<String>> myfile = matcher.getNewFile(i+".tab");
			List<List<String>> myfile = matcher.randomsc(myseq);
			int count = matcher.mymatcher(mypattern, myfile);
			System.out.println("Pattern Length: " + mypattern.size()
					+" File Length: " + myfile.size()
					+" Pattern Matched: " + count);
		}
		
	}

}
