import java.util.Date;

import javafish.clients.opc.browser.JOpcBrowser;
import javafish.clients.opc.exception.ConnectivityException;
import javafish.clients.opc.exception.UnableAddGroupException;
import javafish.clients.opc.exception.UnableAddItemException;
import javafish.clients.opc.exception.UnableBrowseLeafException;
import javafish.clients.opc.exception.UnableIBrowseException;

public class RunOpc implements Runnable{
		
		private String item_name;
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
		public String getItem_name() {
			return item_name;
		}

		public void setItem_name(String item_name) {
			this.item_name = item_name;
		}

		public RunOpc(String item_name) {
			super();
			this.item_name = item_name;
		}
		

		public RunOpc(String item_name, String host, String opcname) {
			super();
			this.item_name = item_name;
			this.host = host;
			this.opcname = opcname;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			 String[] items = null;
				try {
					long start = new Date().getTime();
					JOpcBrowser.coInitialize();
					JOpcBrowser jbrowser = new JOpcBrowser(host,opcname, "JOPCBrowser1");
					jbrowser.connect();
					items = jbrowser.getOpcItems(item_name, true);
					if (items != null) {
						for (int k = 0; k < items.length; k++) {
							System.out.println(items[k]);
							OpcDemo.count++;
						}
					}					
					JOpcBrowser.coUninitialize();
					long end = new Date().getTime();
					OpcDemo.timec+=(end-start);
					System.out.println(item_name+":"+OpcDemo.count+":"+OpcDemo.timec);
				} catch (UnableBrowseLeafException | UnableIBrowseException
						| UnableAddGroupException | UnableAddItemException | ConnectivityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
	}