package cn.edu.jlu.ccst.opc;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafish.clients.opc.browser.JOpcBrowser;
import javafish.clients.opc.exception.ConnectivityException;
import javafish.clients.opc.exception.UnableBrowseBranchException;
import javafish.clients.opc.exception.UnableIBrowseException;



public class OpcDemo {

	private List<String> item_names;
	public static int count;
	public static long timec;
	private String host;
	private String opcname;
	
	

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getOpcname() {
		return opcname;
	}

	public void setOpcname(String opcname) {
		this.opcname = opcname;
	}

	public List<String> getItem_names() {
		return item_names;
	}

	public void setItem_names(List<String> item_names) {
		this.item_names = item_names;
	}

	
	
	public OpcDemo() {
		super();
		// TODO Auto-generated constructor stub
		item_names = new ArrayList();
		FileReader read;
		try {
			read = new FileReader("opcConfig.ini");
			BufferedReader br = new BufferedReader(read);
			String row;
			while ((row = br.readLine()) != null) {
				if(row.trim().startsWith("host")){
					String[] hs = row.trim().split("=");
					this.setHost(hs[1].trim());
				}
				if(row.trim().startsWith("name")){
					String[] ns = row.trim().split("=");
					this.setOpcname(ns[1].trim());
				}
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void fillitemlist() {
		count = 0;
		item_names = new ArrayList();
		try {
			JOpcBrowser.coInitialize();
			JOpcBrowser jbrowser = new JOpcBrowser(host,opcname, "JOPCBrowser1");
			jbrowser.connect();
			String[] bs = jbrowser.getOpcBranch("");
			String[] bbs = null;
			if (bs != null) {
				for (int i = 0; i < bs.length; i++) {
					System.out.println(bs[i]);
					bbs = jbrowser.getOpcBranch(bs[i]);
					if (bbs != null) {
						for (int j = 0; j < bbs.length; j++) {
							System.out.println(bbs[j]);
							item_names.add(bs[i] + "." + bbs[j]);
						}
					}
				}
			}
			JOpcBrowser.coUninitialize();
		} catch (UnableBrowseBranchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnableIBrowseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConnectivityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		long start = new Date().getTime();
		OpcDemo od = new OpcDemo();
		od.fillitemlist();
		String host = od.getHost();
		String opcname = od.getOpcname();
		for (String name : od.getItem_names()) {			
			new Thread(new RunOpc(name, host, opcname)).start();
		}
		long end = new Date().getTime();
		od.timec = (end - start);
		System.out.println(timec);

	}
}
