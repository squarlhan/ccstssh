import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javafish.clients.opc.browser.JOpcBrowser;
import javafish.clients.opc.exception.ConnectivityException;
import javafish.clients.opc.exception.UnableAddGroupException;
import javafish.clients.opc.exception.UnableAddItemException;
import javafish.clients.opc.exception.UnableBrowseBranchException;
import javafish.clients.opc.exception.UnableBrowseLeafException;
import javafish.clients.opc.exception.UnableIBrowseException;

public class RunOpc implements Runnable{
		
		private String item_name;
		private String host;
		private String opcname;
		private List<String> bss0;
		private List<List<String>> bss1;
		private List<List<String>> items;
		private HashMap<String, Integer> hashmap;
		private List<String> selected;
		private String out;



		public String getOut() {
			return out;
		}

		public void setOut(String out) {
			this.out = out;
		}

		public List<String> getSelected() {
			return selected;
		}

		public void setSelected(List<String> selected) {
			this.selected = selected;
		}

		public HashMap<String, Integer> getHashmap() {
			return hashmap;
		}

		public void setHashmap(HashMap<String, Integer> hashmap) {
			this.hashmap = hashmap;
		}

		public List<String> getBss0() {
			return bss0;
		}

		public void setBss0(List<String> bss0) {
			this.bss0 = bss0;
		}
		
		public List<List<String>> getBss1() {
			return bss1;
		}

		public void setBss1(List<List<String>> bss1) {
			this.bss1 = bss1;
		}
		public List<List<String>> getItems() {
			return items;
		}

		public void setItems(List<List<String>> items) {
			this.items = items;
		}

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
		public String getItem_name() {
			return item_name;
		}

		public void setItem_name(String item_name) {
			this.item_name = item_name;
		}

		public RunOpc(String item_name) {
			super();
			this.item_name = item_name;
			bss0 = new ArrayList();
			bss1 = new ArrayList();
			items = new ArrayList();
			hashmap = new HashMap();
 
		}
		

		public RunOpc(String item_name, String host, String opcname) {
			super();
			this.item_name = item_name;
			this.host = host;
			this.opcname = opcname;
			bss0 = new ArrayList();
			bss1 = new ArrayList();
			items = new ArrayList();
			hashmap = new HashMap();
		}
		
		public RunOpc(String item_name, List<String> selected, String host, String opcname) {
			super();
			this.item_name = item_name;
			this.selected = selected;
			this.host = host;
			this.opcname = opcname;
			bss0 = new ArrayList();
			bss1 = new ArrayList();
			items = new ArrayList();
			hashmap = new HashMap();
			this.out = "";
		}

		public void GetStructure(){
			
			try {
				JOpcBrowser.coInitialize();
				JOpcBrowser jbrowser = new JOpcBrowser(host,opcname, "JOPCBrowsers");
				jbrowser.connect();
				String[] bs = jbrowser.getOpcBranch("");
				String[] bbs = null;
				int count = 0;
				if (bs != null) {
					for (int i = 0; i < bs.length; i++) {
//						System.out.println(bs[i]);
						bss0.add(bs[i]);
						bbs = jbrowser.getOpcBranch(bs[i]);
						if (bbs != null) {
							for (int j = 0; j < bbs.length; j++) {
//								System.out.println(bbs[j]);
								item_name = bs[i] + "." + bbs[j];
								hashmap.put(item_name, count);
								count++;
								String[] item_arr = jbrowser.getOpcItems(item_name, false);
								List<String> item_list = new ArrayList();
								for(String item: item_arr){
									String[] temps = item.split("; ");
									item_list.add(temps[0].trim());
								}
								items.add(item_list);
							}
						}
						List<String> bbs_list = new ArrayList();
						for(String item: bbs){
							bbs_list.add(item);
						}
						bss1.add(bbs_list);
					}
				}
				jbrowser.coUninitialize();
			} catch (ConnectivityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnableBrowseBranchException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnableIBrowseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnableBrowseLeafException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnableAddGroupException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnableAddItemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			 String[] items = null;
				try {
					long start = new Date().getTime();
//					Thread.sleep(500);
					JOpcBrowser.coInitialize();
					JOpcBrowser jbrowser = new JOpcBrowser(host,opcname, "JOPCBrowser1");
					jbrowser.connect();
					items = jbrowser.getOpcItems(item_name, true);
					if (items != null) {
						for (int k = 0; k < items.length; k++) {							
							String[] iit = items[k].split("; ");
							List<String> a = new ArrayList();
							if((selected.size()==0)||(selected.size()>0&&selected.indexOf(iit[0].trim())>=0)){
								System.out.println(iit[0]+":"+iit[1]+":"+iit[3]);
								NewSWTApp.receiveStr(iit[0]+":"+iit[1]+":"+iit[3]+"\n");
							}
							OpcDemo.count++;
						}
					}					
					JOpcBrowser.coUninitialize();
					long end = new Date().getTime();
					OpcDemo.timec+=(end-start);
					System.out.println(item_name+":"+OpcDemo.count+":"+OpcDemo.timec);
					NewSWTApp.receiveStr(item_name+":"+OpcDemo.count+":"+OpcDemo.timec+"\n");
				} catch (UnableBrowseLeafException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnableIBrowseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnableAddGroupException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnableAddItemException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ConnectivityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
	}