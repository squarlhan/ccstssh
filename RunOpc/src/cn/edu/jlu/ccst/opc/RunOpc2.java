package cn.edu.jlu.ccst.opc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javafish.clients.opc.browser.JOpcBrowser;
import javafish.clients.opc.exception.ConnectivityException;
import javafish.clients.opc.exception.UnableAddGroupException;
import javafish.clients.opc.exception.UnableAddItemException;
import javafish.clients.opc.exception.UnableBrowseLeafException;
import javafish.clients.opc.exception.UnableIBrowseException;

public class RunOpc2 implements Runnable {

	private String item_name;
	private String host;
	private String opcname;
	private Statement statement;
	private List<String> selected;
	
	

	public List<String> getSelected() {
		return selected;
	}

	public void setSelected(List<String> selected) {
		this.selected = selected;
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

	public Statement getStatement() {
		return statement;
	}

	public void setStatement(Statement statement) {
		this.statement = statement;
	}

	public RunOpc2(String item_name) {
		super();
		this.item_name = item_name;
	}

	public RunOpc2(String item_name, String host, String opcname) {
		super();
		this.item_name = item_name;
		this.host = host;
		this.opcname = opcname;
	}

	public RunOpc2(String item_name, String host, String opcname,
			List<String> selected, Statement statement) {
		super();
		this.item_name = item_name;
		this.host = host;
		this.opcname = opcname;
		this.statement = statement;
		this.selected = selected;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		String[] items = null;
		try {
			long start = new Date().getTime();
			JOpcBrowser.coInitialize();
			JOpcBrowser jbrowser = new JOpcBrowser(host, opcname,
					"JOPCBrowser1");
			jbrowser.connect();
			items = jbrowser.getOpcItems(item_name, true);

			if (items != null) {
				for (int k = 0; k < items.length; k++) {
					// System.out.println(items[k]);
					OpcDemo.count++;
					String[] rows = items[k].split(";");
					if (rows.length == 4 && (!rows[3].trim().startsWith("bad"))) {
						String name = rows[0].trim();
						String item = rows[2].trim();
						String r3 = rows[3].trim();
						double value;
						if (r3.equalsIgnoreCase("false")) {
							value = 1;
						} else if (r3.equalsIgnoreCase("true")) {
							value = 0;
						} else {
							value = Double.parseDouble(r3);
						}
						if ((selected.size() == 0)
								|| (selected.size() > 0 && selected
										.indexOf(name.trim()) >= 0)) {
							String sqld = "insert into motodcsdata(equipment, item, value) values('"
									+ name + "','" + item + "'," + value + ")";
							SimpleDateFormat formatter = new SimpleDateFormat(
									"yyyy-MM-dd HH:mm:ss");
							String mytime = formatter.format(new Date());
							String sqlh = "insert into motodcshistory(equipment, item, seqno,value) values('"
									+ name
									+ "','"
									+ item
									+ "','"
									+ mytime
									+ "'," + value + ")";
							statement.execute(sqld);
							statement.execute(sqlh);
							// statement.close();
							// conn.close();
						}
					}
				}
			}
			JOpcBrowser.coUninitialize();
			long end = new Date().getTime();
			OpcDemo.timec += (end - start);
			System.out.println(item_name + ":" + OpcDemo.count + ":"
					+ OpcDemo.timec);
		} catch (ConnectivityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}