package cn.edu.jlu.ccst.opc;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class SystemTrayUtil{
	
	public static void main(String[] args) {
		new SystemTrayUtil().openFrame();
	}
	private SystemTray tray;//ϵͳ���̶���
	private TrayIcon trayIcon;//�����������ͼ��
    private boolean flag;
    private OpcDemo2 od;
    private MenuItem start_service;
    private MenuItem stop_service;
    private MenuItem service_option;
    private MenuItem defaultItem;
	public void openFrame(){
		flag = true;
		od = new OpcDemo2();
		start_service = new MenuItem("��������");
	    stop_service = new MenuItem("ֹͣ����");
	    service_option = new MenuItem("����");
	    defaultItem = new MenuItem("�˳�");
		run();
	}
	
	public void run(){
		Image image = Toolkit.getDefaultToolkit().getImage("./res/0.jpg");//ָ������ͼ���ͼ��
		//�жϸ�ϵͳ�Ƿ�֧��"ϵͳ����"
		if (SystemTray.isSupported()) {
			tray = SystemTray.getSystemTray();
			start_service();
			ActionListener listener = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String cmd = e.getActionCommand();//������̵��¼�����ͼ�굯���˵��Ĳ˵���
					System.out.println(cmd);
					//���ݲ�ͬ�¼����в�ͬ���߼�����
					if(cmd == null){
					}else if(cmd.equals("�˳�")){
						trayIcon.displayMessage("����", "����������ֹ����", TrayIcon.MessageType.WARNING);
						tray.remove(trayIcon);
					}else if(cmd.equals("��������")){
						trayIcon.displayMessage("��Ϣ��", "�������������С���", TrayIcon.MessageType.INFO);
						flag = true;
						start_service();
						start_service.setEnabled(false);
						stop_service.setEnabled(true);
					}else if(cmd.equals("ֹͣ����")){
						trayIcon.displayMessage("��Ϣ��", "��������ֹͣ�С���", TrayIcon.MessageType.INFO);
						stop_service();
						stop_service.setEnabled(false);
						start_service.setEnabled(true);					
					}else if(cmd.equals("����")){
						service_option();
					}
				}
			};
			//���������˵�
			PopupMenu popup = new PopupMenu();
			
			//���½������˵�����뵽�����˵���
			
			start_service.addActionListener(listener);
			popup.add(start_service);
			start_service.setEnabled(false);
			
			stop_service.addActionListener(listener);
			popup.add(stop_service);
			
			service_option.addActionListener(listener);
			popup.add(service_option);
			
			defaultItem.addActionListener(listener);
			popup.add(defaultItem);
			
						
			//����ϵͳ����ͼ��
			trayIcon = new TrayIcon(image, "OPC���ݲɼ�", popup);
		
			trayIcon.addActionListener(listener);
			try {
				tray.add(trayIcon);
			} catch (AWTException e) {
				System.err.println(e);
			}
		} else {
			//ϵͳ��֧������
		}
		
		//������Ա���
		if (trayIcon != null) {
			trayIcon.setImage(image);
		}
	}
	
	private void start_service(){
		int timespan = 20;
		FileReader read;
		try {
			read = new FileReader("opcConfig.ini");
			BufferedReader br = new BufferedReader(read);
			String row;
			while ((row = br.readLine()) != null) {
				if(row.trim().startsWith("timespan")){
					String[] hs = row.trim().split("=");
					timespan = Integer.parseInt(hs[1].trim());
				}
			}
			br.close();
			System.out.println(timespan);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		final Timer timer = new Timer();
        timer.schedule(new TimerTask(){
        	public void run() {
        		if(flag){
        			System.out.println("********");
        			long start = new Date().getTime();
        			od.cleantable();
        			od.fillitemlist();
        			for (String name : od.getItem_names()) {			
        				new Thread(new RunOpc2(name, od.getHost(), od.getOpcname(),
        						od.getSelected(), od.getConn())).start();
        			}
        			long end = new Date().getTime();
        			od.timec = (end - start);
        			System.out.println(od.timec);
        			System.gc();
        		}else{
        			timer.cancel();
        		}
        	}
        }, 1000, timespan*1000);
        
	}
	
	private void stop_service(){
		flag = false;
	}
	
	private void service_option(){
		Display display = Display.getDefault();
		Shell shell = new Shell(display,SWT.CLOSE | SWT.MIN);
		shell.setText("OPCConfig");
		shell.setImage(new org.eclipse.swt.graphics.Image(display, "res/1.jpg"));
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
}
