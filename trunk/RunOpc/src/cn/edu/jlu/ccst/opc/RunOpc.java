package cn.edu.jlu.ccst.opc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafish.clients.opc.browser.JOpcBrowser;
import javafish.clients.opc.exception.ConnectivityException;
import javafish.clients.opc.exception.UnableAddGroupException;
import javafish.clients.opc.exception.UnableAddItemException;
import javafish.clients.opc.exception.UnableBrowseLeafException;
import javafish.clients.opc.exception.UnableIBrowseException;

public class RunOpc implements Runnable {

	private String item_name;
	private String host;
	private String opcname;
	private String dbhost;
	private String dbport;
	private String dbname;
	private String dbuser;
	private String dbpsw;

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
	
	public String getDbhost() {
		return dbhost;
	}

	public void setDbhost(String dbhost) {
		this.dbhost = dbhost;
	}

	public String getDbport() {
		return dbport;
	}

	public void setDbport(String dbport) {
		this.dbport = dbport;
	}

	public String getDbname() {
		return dbname;
	}

	public void setDbname(String dbname) {
		this.dbname = dbname;
	}

	public String getDbuser() {
		return dbuser;
	}

	public void setDbuser(String dbuser) {
		this.dbuser = dbuser;
	}

	public String getDbpsw() {
		return dbpsw;
	}

	public void setDbpsw(String dbpsw) {
		this.dbpsw = dbpsw;
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
	
	

	public RunOpc(String item_name, String host, String opcname, String dbhost,
			String dbport, String dbname, String dbuser, String dbpsw) {
		super();
		this.item_name = item_name;
		this.host = host;
		this.opcname = opcname;
		this.dbhost = dbhost;
		this.dbport = dbport;
		this.dbname = dbname;
		this.dbuser = dbuser;
		this.dbpsw = dbpsw;
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
			
			String driver = "com.mysql.jdbc.Driver";
			String url = "jdbc:mysql://"+dbhost+":"+dbport+"/"+dbname;
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, dbuser, dbpsw);
			Statement statement = conn.createStatement();
			
			if (items != null) {
				for (int k = 0; k < items.length; k++) {
					System.out.println(items[k]);
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
								+ "',"
								+ value + ")";
						statement.execute(sqld);
						statement.execute(sqlh);
//						statement.close();
//						conn.close();
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
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}