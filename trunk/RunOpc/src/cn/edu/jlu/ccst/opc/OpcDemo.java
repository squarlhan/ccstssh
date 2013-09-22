package cn.edu.jlu.ccst.opc;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.tanukisoftware.wrapper.WrapperListener;
import org.tanukisoftware.wrapper.WrapperManager;

import javafish.clients.opc.browser.JOpcBrowser;
import javafish.clients.opc.exception.ConnectivityException;
import javafish.clients.opc.exception.UnableBrowseBranchException;
import javafish.clients.opc.exception.UnableIBrowseException;



public class OpcDemo implements WrapperListener{

	private List<String> item_names;
	public static int count;
	public static long timec;
	private String host;
	private String opcname;
	private String dbhost;
	private String dbport;
	private String dbname;
	private String dbuser;
	private String dbpsw;
	private int timespan;
	private Statement statement;
	private Connection conn;
	private List<String> selected;
	

	public List<String> getSelected() {
		return selected;
	}

	public void setSelected(List<String> selected) {
		this.selected = selected;
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public Statement getStatement() {
		return statement;
	}

	public void setStatement(Statement statement) {
		this.statement = statement;
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

	public List<String> getItem_names() {
		return item_names;
	}

	public void setItem_names(List<String> item_names) {
		this.item_names = item_names;
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
	
	

	public int getTimespan() {
		return timespan;
	}

	public void setTimespan(int timespan) {
		this.timespan = timespan;
	}

	public OpcDemo() {
		super();
		// TODO Auto-generated constructor stub
		item_names = new ArrayList();
		selected = new ArrayList();
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
				if(row.trim().startsWith("dbname")){
					String[] ns = row.trim().split("=");
					this.setDbname(ns[1].trim());
				}
				if(row.trim().startsWith("dbhost")){
					String[] ns = row.trim().split("=");
					this.setDbhost(ns[1].trim());
				}
				if(row.trim().startsWith("dbport")){
					String[] ns = row.trim().split("=");
					this.setDbport(ns[1].trim());
				}
				if(row.trim().startsWith("dbuser")){
					String[] ns = row.trim().split("=");
					this.setDbuser(ns[1].trim());
				}
				if(row.trim().startsWith("dbpsw")){
					String[] ns = row.trim().split("=");
					this.setDbpsw(ns[1].trim());
				}
				if (row.trim().indexOf("=")<0) {
					selected.add(row.trim());
				}
				if(row.trim().startsWith("timespan")){
					String[] hs = row.trim().split("=");
					timespan = Integer.parseInt(hs[1].trim());
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
//					System.out.println(bs[i]);
					bbs = jbrowser.getOpcBranch(bs[i]);
					if (bbs != null) {
						for (int j = 0; j < bbs.length; j++) {
//							System.out.println(bbs[j]);
							item_names.add(bs[i] + "." + bbs[j]);
						}
					}
				}
			}
			JOpcBrowser.coUninitialize();
		} catch (UnableBrowseBranchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConnectivityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnableIBrowseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void cleantable(){
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://"+dbhost+":"+dbport+"/"+dbname;
		try {
			Class.forName(driver);		
		    conn = DriverManager.getConnection(url, dbuser, dbpsw);
		    statement = conn.createStatement();
		    statement.execute("truncate table motodcsdata");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * @param args
	 */
	
	@Override
	public void controlEvent(int event) {
		// TODO Auto-generated method stub
		if ((event == WrapperManager.WRAPPER_CTRL_LOGOFF_EVENT)
				&& (WrapperManager.isLaunchedAsService() || WrapperManager
						.isIgnoreUserLogoffs())) {
		} else {
			WrapperManager.stop(0);
		}
	}

	@Override
	public Integer start(String[] arg0) {
		// TODO Auto-generated method stub
		System.out.println("OPC DEMO STARTING...");
		Timer timer = new Timer();
        timer.schedule(new TimerTask(){
        	public void run() {
        		System.out.println("********");
				long start = new Date().getTime();
				OpcDemo2 od = new OpcDemo2();
				od.cleantable();
				od.fillitemlist();
				for (String name : od.getItem_names()) {			
					new Thread(new RunOpc2(name, od.getHost(), od.getOpcname(),
							od.getSelected(), od.getConn())).start();
				}
				long end = new Date().getTime();
				od.timec = (end - start);
				System.out.println(timec);
        	}
        }, 1000, timespan*1000);
		return null;
	}

	@Override
	public int stop(int arg0) {
		// TODO Auto-generated method stub
			System.out.println("OPC DEMO STOPPING...");
			return arg0;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		WrapperManager.start( new OpcDemo(), args );

	}
}
