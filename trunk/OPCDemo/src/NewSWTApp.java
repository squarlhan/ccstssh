

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import com.cloudgarden.resource.SWTResourceManager;

/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class NewSWTApp extends org.eclipse.swt.widgets.Composite {
	private List list1;
	private Label label1;
	private TreeItem treeItem1;
	private Tree tree1;
	private Button button7;
	private Button button6;
	private Button button5;
	private Button button4;
	private Button button3;
	private Button button2;
	private Button button1;
	public static Text text7;
	private List list2;
	private Text text6;
	private Text text5;
	private Text text4;
	private Text text3;
	private Text text2;
	private Label label6;
	private Label label5;
	private Label label4;
	private Label label3;
	private Label label2;
	private Text text1;
	
	
	
	private RunOpc ro ;
	private Label label8;
	private Text text9;
	private Label label7;
	private Text text8;

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}

	public NewSWTApp(Composite parent, int style) {
		super(parent, style);
		initGUI();
	}
	
	/**
	* Initializes the GUI.
	*/
	private void initGUI() {
		try {
			this.setSize(1146, 748);
			FormLayout thisLayout = new FormLayout();
			this.setLayout(thisLayout);
			{
				FormData tree1LData = new FormData();
				tree1LData.left =  new FormAttachment(0, 1000, 12);
				tree1LData.top =  new FormAttachment(0, 1000, 280);
				tree1LData.width = 240;
				tree1LData.height = 434;
				tree1 = new Tree(this, SWT.NONE);
				tree1.setLayoutData(tree1LData);
				{
					treeItem1 = new TreeItem(tree1, SWT.NONE);
					treeItem1.setText("Root");
				}
				tree1.addListener(SWT.MouseDoubleClick, new Listener() {
					@Override
					public void handleEvent(Event arg0) {
						// TODO Auto-generated method stub
						TreeItem tised = tree1.getSelection()[0];
						TreeItem tisedp = tised.getParentItem();
						if (tisedp != null&&tisedp.getText().indexOf("Root")<0) {
							int index = ro.getHashmap().get(
									tisedp.getText() + "." + tised.getText());
							if(index>=0){
								Object[] is = ro.getItems().get(index).toArray();
								list1.removeAll();
								for (int k = 0; k <= is.length - 1; k++) {
									list1.add(is[k].toString(), k);
								}
							}
						}
					}
				});
			}
			{
				button4 = new Button(this, SWT.PUSH | SWT.CENTER);
				FormData button4LData = new FormData();
				button4LData.left =  new FormAttachment(0, 1000, 506);
				button4LData.top =  new FormAttachment(0, 1000, 271);
				button4LData.width = 24;
				button4LData.height = 25;
				button4.setLayoutData(button4LData);
				button4.setText(">");
				button4.addMouseListener(new MouseAdapter() {
					public void mouseUp(MouseEvent evt) {
						button4MouseUp(evt);
					}
				});
			}
			{
				button3 = new Button(this, SWT.PUSH | SWT.CENTER);
				FormData button3LData = new FormData();
				button3LData.left =  new FormAttachment(0, 1000, 212);
				button3LData.top =  new FormAttachment(0, 1000, 241);
				button3LData.width = 57;
				button3LData.height = 25;
				button3.setLayoutData(button3LData);
				button3.setText("\u4fdd\u5b58");
				button3.addMouseListener(new MouseAdapter() {
					public void mouseUp(MouseEvent evt) {
						button3MouseUp(evt);
					}
				});
			}
			{
				button2 = new Button(this, SWT.PUSH | SWT.CENTER);
				FormData button2LData = new FormData();
				button2LData.left =  new FormAttachment(0, 1000, 111);
				button2LData.top =  new FormAttachment(0, 1000, 241);
				button2LData.width = 57;
				button2LData.height = 25;
				button2.setLayoutData(button2LData);
				button2.setText("\u5f97\u5230\u6570\u636e");
				button2.addMouseListener(new MouseAdapter() {
					public void mouseUp(MouseEvent evt) {
						button2MouseUp(evt);
					}
				});
			}
			{
				button1 = new Button(this, SWT.PUSH | SWT.CENTER);
				FormData button1LData = new FormData();
				button1LData.left =  new FormAttachment(0, 1000, 12);
				button1LData.top =  new FormAttachment(0, 1000, 241);
				button1LData.width = 57;
				button1LData.height = 25;
				button1.setLayoutData(button1LData);
				button1.setText("\u5f97\u5230\u7ed3\u6784");
				button1.addMouseListener(new MouseAdapter() {
					public void mouseUp(MouseEvent evt) {
						button1MouseUp(evt);
					}
				});
			}
			{
				text7 = new Text(this, SWT.MULTI | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL);
				FormData text7LData = new FormData();
				text7LData.left =  new FormAttachment(0, 1000, 762);
				text7LData.top =  new FormAttachment(0, 1000, 21);
				text7LData.width = 359;
				text7LData.height = 710;
				text7.setLayoutData(text7LData);
				text7.setText("");
			}
			{
				label2 = new Label(this, SWT.NONE);
				FormData label2LData = new FormData();
				label2LData.left =  new FormAttachment(0, 1000, 46);
				label2LData.top =  new FormAttachment(0, 1000, 48);
				label2LData.width = 73;
				label2LData.height = 15;
				label2.setLayoutData(label2LData);
				label2.setText("OPC\u670d\u52a1\u540d\uff1a");
			}
			{
				text1 = new Text(this, SWT.NONE);
				FormData text1LData = new FormData();
				text1LData.left =  new FormAttachment(0, 1000, 131);
				text1LData.top =  new FormAttachment(0, 1000, 21);
				text1LData.width = 138;
				text1LData.height = 15;
				text1.setLayoutData(text1LData);
				text1.setText("localhost");
			}
			{
				label1 = new Label(this, SWT.NONE);
				FormData label1LData = new FormData();
				label1LData.left =  new FormAttachment(0, 1000, 22);
				label1LData.top =  new FormAttachment(0, 1000, 21);
				label1LData.width = 97;
				label1LData.height = 15;
				label1.setLayoutData(label1LData);
				label1.setText("OPC\u670d\u52a1\u5668\u5730\u5740\uff1a");
			}
			{
				FormData list1LData = new FormData();
				list1LData.left =  new FormAttachment(0, 1000, 319);
				list1LData.top =  new FormAttachment(0, 1000, 21);
				list1LData.width = 152;
				list1LData.height = 710;
				list1 = new List(this, SWT.MULTI | SWT.V_SCROLL);
				list1.setLayoutData(list1LData);
			}
			{
				label3 = new Label(this, SWT.NONE);
				FormData label3LData = new FormData();
				label3LData.left =  new FormAttachment(0, 1000, 12);
				label3LData.top =  new FormAttachment(0, 1000, 75);
				label3LData.width = 107;
				label3LData.height = 15;
				label3.setLayoutData(label3LData);
				label3.setText("\u6570\u636e\u5e93\u670d\u52a1\u5668\u5730\u5740\uff1a");
			}
			{
				label4 = new Label(this, SWT.NONE);
				FormData label4LData = new FormData();
				label4LData.left =  new FormAttachment(0, 1000, 58);
				label4LData.top =  new FormAttachment(0, 1000, 102);
				label4LData.width = 61;
				label4LData.height = 15;
				label4.setLayoutData(label4LData);
				label4.setText("\u6570\u636e\u5e93\u540d\uff1a");
			}
			{
				label5 = new Label(this, SWT.NONE);
				FormData label5LData = new FormData();
				label5LData.left =  new FormAttachment(0, 1000, 34);
				label5LData.top =  new FormAttachment(0, 1000, 155);
				label5LData.width = 85;
				label5LData.height = 15;
				label5.setLayoutData(label5LData);
				label5.setText("\u6570\u636e\u5e93\u7528\u6237\u540d\uff1a");
			}
			{
				label6 = new Label(this, SWT.NONE);
				FormData label6LData = new FormData();
				label6LData.left =  new FormAttachment(0, 1000, 46);
				label6LData.top =  new FormAttachment(0, 1000, 182);
				label6LData.width = 73;
				label6LData.height = 15;
				label6.setLayoutData(label6LData);
				label6.setText("\u6570\u636e\u5e93\u5bc6\u7801\uff1a");
			}
			{
				text2 = new Text(this, SWT.NONE);
				FormData text2LData = new FormData();
				text2LData.left =  new FormAttachment(0, 1000, 131);
				text2LData.top =  new FormAttachment(0, 1000, 48);
				text2LData.width = 138;
				text2LData.height = 15;
				text2.setLayoutData(text2LData);
				text2.setText("SUPCON.JXServer.1");
			}
			{
				text3 = new Text(this, SWT.NONE);
				FormData text3LData = new FormData();
				text3LData.left =  new FormAttachment(0, 1000, 131);
				text3LData.top =  new FormAttachment(0, 1000, 75);
				text3LData.width = 138;
				text3LData.height = 15;
				text3.setLayoutData(text3LData);
				text3.setText("localhost");
			}
			{
				text4 = new Text(this, SWT.NONE);
				FormData text4LData = new FormData();
				text4LData.left =  new FormAttachment(0, 1000, 131);
				text4LData.top =  new FormAttachment(0, 1000, 102);
				text4LData.width = 138;
				text4LData.height = 15;
				text4.setLayoutData(text4LData);
				text4.setText("wushuichang");
			}
			{
				text5 = new Text(this, SWT.NONE);
				FormData text5LData = new FormData();
				text5LData.left =  new FormAttachment(0, 1000, 131);
				text5LData.top =  new FormAttachment(0, 1000, 155);
				text5LData.width = 138;
				text5LData.height = 15;
				text5.setLayoutData(text5LData);
				text5.setText("root");
			}
			{
				text6 = new Text(this, SWT.NONE);
				FormData text6LData = new FormData();
				text6LData.left =  new FormAttachment(0, 1000, 131);
				text6LData.top =  new FormAttachment(0, 1000, 182);
				text6LData.width = 138;
				text6LData.height = 15;
				text6.setLayoutData(text6LData);
				text6.setEchoChar('*');
				text6.setText("root");
			}
			{
				FormData list2LData = new FormData();
				list2LData.left =  new FormAttachment(0, 1000, 552);
				list2LData.top =  new FormAttachment(0, 1000, 21);
				list2LData.width = 152;
				list2LData.height = 710;
				list2 = new List(this, SWT.MULTI | SWT.V_SCROLL);
				list2.setLayoutData(list2LData);
			}
			{
				button5 = new Button(this, SWT.PUSH | SWT.CENTER);
				FormData button5LData = new FormData();
				button5LData.left =  new FormAttachment(0, 1000, 506);
				button5LData.top =  new FormAttachment(0, 1000, 308);
				button5LData.width = 24;
				button5LData.height = 25;
				button5.setLayoutData(button5LData);
				button5.setText(">>");
				button5.addMouseListener(new MouseAdapter() {
					public void mouseUp(MouseEvent evt) {
						button5MouseUp(evt);
					}
				});
			}
			{
				button6 = new Button(this, SWT.PUSH | SWT.CENTER);
				FormData button6LData = new FormData();
				button6LData.left =  new FormAttachment(0, 1000, 506);
				button6LData.top =  new FormAttachment(0, 1000, 345);
				button6LData.width = 24;
				button6LData.height = 25;
				button6.setLayoutData(button6LData);
				button6.setText("<");
				button6.addMouseListener(new MouseAdapter() {
					public void mouseUp(MouseEvent evt) {
						button6MouseUp(evt);
					}
				});
			}
			{
				button7 = new Button(this, SWT.PUSH | SWT.CENTER);
				FormData button7LData = new FormData();
				button7LData.left =  new FormAttachment(0, 1000, 506);
				button7LData.top =  new FormAttachment(0, 1000, 382);
				button7LData.width = 24;
				button7LData.height = 25;
				button7.setLayoutData(button7LData);
				button7.setText("<<");
				button7.addMouseListener(new MouseAdapter() {
					public void mouseUp(MouseEvent evt) {
						button7MouseUp(evt);
					}
				});
			}
			{
				text8 = new Text(this, SWT.NONE);
				FormData text8LData = new FormData();
				text8LData.left =  new FormAttachment(0, 1000, 131);
				text8LData.top =  new FormAttachment(0, 1000, 128);
				text8LData.width = 138;
				text8LData.height = 15;
				text8.setLayoutData(text8LData);
				text8.setText("3306");
			}
			{
				label7 = new Label(this, SWT.NONE);
				FormData label7LData = new FormData();
				label7LData.left =  new FormAttachment(0, 1000, 34);
				label7LData.top =  new FormAttachment(0, 1000, 128);
				label7LData.width = 85;
				label7LData.height = 15;
				label7.setLayoutData(label7LData);
				label7.setText("\u6570\u636e\u5e93\u7aef\u53e3\u53f7\uff1a");
			}
			{
				text9 = new Text(this, SWT.NONE);
				text9.setText("300");
				FormData text9LData = new FormData();
				text9LData.left =  new FormAttachment(0, 1000, 131);
				text9LData.top =  new FormAttachment(0, 1000, 209);
				text9LData.width = 138;
				text9LData.height = 15;
				text9.setLayoutData(text9LData);
			}
			{
				label8 = new Label(this, SWT.NONE);
				FormData label8LData = new FormData();
				label8LData.left =  new FormAttachment(0, 1000, 10);
				label8LData.top =  new FormAttachment(0, 1000, 209);
				label8LData.width = 107;
				label8LData.height = 15;
				label8.setLayoutData(label8LData);
				label8.setText("\u91c7\u6837\u65f6\u95f4\u5dee\uff08\u79d2\uff09\uff1a");
			}
			this.layout();
			readini();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	* Auto-generated main method to display this 
	* org.eclipse.swt.widgets.Composite inside a new Shell.
	*/
	public static void main(String[] args) {
		Display display = Display.getDefault();
		Shell shell = new Shell(display,SWT.CLOSE | SWT.MIN);
		shell.setText("OPCConfig");
		shell.setImage(new Image(display, "res/1.jpg"));
		NewSWTApp inst = new NewSWTApp(shell, SWT.NULL);
		Point size = inst.getSize();
		shell.setLayout(new FillLayout());
		shell.layout();
		if(size.x == 0 && size.y == 0) {
			inst.pack();
			shell.pack();
		} else {
			Rectangle shellBounds = shell.computeTrim(0, 0, size.x, size.y);
			shell.setSize(shellBounds.width, shellBounds.height);
		}
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}
	
	private void button1MouseUp(MouseEvent evt) {
		treeItem1.removeAll();
		ro = new RunOpc(null, text1.getText().trim(), text2.getText().trim());
		ro.GetStructure();
		Object[] bss0 = ro.getBss0().toArray();
		for(int i = 0;i<= bss0.length-1;i++){
			TreeItem item1 = new TreeItem (treeItem1, 0);  
			item1.setText (bss0[i].toString());  
			Object[] bss1 = ro.getBss1().get(i).toArray();
			for(int j = 0;j<= bss1.length-1;j++){
				TreeItem item2 = new TreeItem (item1, 0);  
				item2.setText (bss1[j].toString());  
				
			}
		}
		treeItem1.setExpanded(true);
		//TODO add your code for button1.mouseUp
	}
	
	private void button4MouseUp(MouseEvent evt) {
		for(String s:list1.getSelection()){
			if(list2.indexOf(s)<0){
				list2.add(s);
			}
		}
		//TODO add your code for button4.mouseUp
	}
	
	private void button5MouseUp(MouseEvent evt) {
		for(String s: list1.getItems()){
			if(list2.indexOf(s)<0){
				list2.add(s);
			}
			
		}
		//TODO add your code for button5.mouseUp
	}
	
	private void button6MouseUp(MouseEvent evt) {
		list2.remove(list2.getSelectionIndices());
		//TODO add your code for button5.mouseUp
	}
	
	private void button7MouseUp(MouseEvent evt) {
		list2.removeAll();
		//TODO add your code for button5.mouseUp
	}
	
	private void button2MouseUp(MouseEvent evt) {
		text7.setText("");
		HashMap hm = ro.getHashmap();
		Set ks = hm.keySet();
		ArrayList<String> ss = new ArrayList();
		for(String s:list2.getItems()){
			ss.add(s);
		}
		for(Object o:ks){
			new Thread(new RunOpc(o.toString(),ss, ro.getHost(), ro.getOpcname())).start();
		}
		//TODO add your code for button5.mouseUp
	}
	
	private void button3MouseUp(MouseEvent evt) {
		button3.setEnabled(false);
		saveini();
		button3.setEnabled(true);
		//TODO add your code for button5.mouseUp
	}
	
	public static void receiveStr(final String str) {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				text7.append(str);
			}
		});
	}
	
	private void readini() {
		try {
			FileReader read = new FileReader("opcConfig.ini");
			BufferedReader br = new BufferedReader(read);
			String row;
			while ((row = br.readLine()) != null) {
				if (row.trim().startsWith("host")) {
					String[] hs = row.trim().split("=");
					text1.setText(hs[1].trim());
				}
				if (row.trim().startsWith("name")) {
					String[] ns = row.trim().split("=");
					text2.setText(ns[1].trim());
				}
				if (row.trim().startsWith("dbname")) {
					String[] ns = row.trim().split("=");
					text4.setText(ns[1].trim());
				}
				if (row.trim().startsWith("dbhost")) {
					String[] ns = row.trim().split("=");
					text3.setText(ns[1].trim());
				}
				if (row.trim().startsWith("dbport")) {
					String[] ns = row.trim().split("=");
					text8.setText(ns[1].trim());
				}
				if (row.trim().startsWith("dbuser")) {
					String[] ns = row.trim().split("=");
					text5.setText(ns[1].trim());
				}
				if (row.trim().startsWith("dbpsw")) {
					String[] ns = row.trim().split("=");
					text6.setText(ns[1].trim());
				}
				if (row.trim().startsWith("timespan")) {
					String[] ns = row.trim().split("=");
					text9.setText(ns[1].trim());
				}
				if (row.trim().indexOf("=")<0) {
					list2.add(row.trim());
				}
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void saveini(){
		try {
			File file = new File("opcConfig.ini");
			if (file.exists()) {
				file.delete();
				if (file.createNewFile()) {
					System.out.println("Config file create success!");
				} else {
					System.out.println("Config file create failed!");
				}
			} else {
				if (file.createNewFile()) {
					System.out.println("Config file create success!");
				} else {
					System.out.println("Config file create failed!");
				}

			}

			BufferedWriter output = new BufferedWriter(new FileWriter(file));
			output.write("host="+text1.getText()+"\r\n");
			output.write("name="+text2.getText()+"\r\n");
			output.write("dbhost="+text3.getText()+"\r\n");
			output.write("dbname="+text4.getText()+"\r\n");
			output.write("dbuser="+text5.getText()+"\r\n");
			output.write("dbpsw="+text6.getText()+"\r\n");
			output.write("dbport="+text8.getText()+"\r\n");
			output.write("timespan="+text9.getText()+"\r\n");
			for(String s : list2.getItems()){
				output.write(s+"\r\n");
			}
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
