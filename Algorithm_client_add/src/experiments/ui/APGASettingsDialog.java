package experiments.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * 基因算法参数设置对话框
 * @author GEO
 *
 */
public class APGASettingsDialog extends Dialog {

	private Text N_text;
	private Text Pt_text;
	private Text K_text;
	private Text Pm_text;
	private Text Pc1_text;
	protected Object result;
	protected Shell shell;
	public String Pc1;// 交叉概率1
	public String Pc2;// 交叉概率2
	public String Pm;// 变异概率
	public String K;// 温度系数
	public String Pt;// 降温系数
	public String NG;// 内部迭代次数

	private double pc1Int;
	private double pc2Int;
	private double pmInt;
	private double kInt;
	private double ptInt;
	private int ngInt;
	private boolean flag = false;

	
	public void setFlag(boolean flag)
	{
		this.flag = flag;
	}
	public int getNgInt() {
		return ngInt;
	}

	public void setNgInt(int ngInt) {
		this.ngInt = ngInt;
	}

	public double getPc1Int() {
		return pc1Int;
	}

	public void setPc1Int(double pc1Int) {
		this.pc1Int = pc1Int;
	}

	public double getPc2Int() {
		return pc2Int;
	}

	public void setPc2Int(double pc2Int) {
		this.pc2Int = pc2Int;
	}

	public double getPmInt() {
		return pmInt;
	}

	public void setPmInt(double pmInt) {
		this.pmInt = pmInt;
	}

	public double getKInt() {
		return kInt;
	}

	public void setKInt(double int1) {
		kInt = int1;
	}

	public double getPtInt() {
		return ptInt;
	}

	public void setPtInt(double ptInt) {
		this.ptInt = ptInt;
	}

	/**
	 * Create the dialog
	 * 
	 * @param parent
	 * @param style
	 */
	public APGASettingsDialog(Shell parent, int style) {
		super(parent, style);
	}

	/**
	 * Create the dialog
	 * 
	 * @param parent
	 */
	public APGASettingsDialog(Shell parent) {
		this(parent, SWT.NONE);
	}

	/**
	 * Open the dialog
	 * 
	 * @return the result
	 */
	public Object open() {
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		return result;
	}

	/**
	 * Create contents of the dialog
	 */
	protected void createContents() {
		shell = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		shell.setSize(425, 289);
		shell.setText(Messages.getString("APGASettingsDialog.0")); //$NON-NLS-1$

		final Label Pc1_label = new Label(shell, SWT.NONE);
		Pc1_label.setText(Messages.getString("APGASettingsDialog.1")); //$NON-NLS-1$
		Pc1_label.setBounds(10, 35, 103, 20);
		if (!flag) {
			Pc1_text = new Text(shell, SWT.BORDER);
			Pc1_text.setText("0.8"); //$NON-NLS-1$
		}
		else
		{
			Pc1_text = new Text(shell, SWT.READ_ONLY);
			Pc1_text.setText(pc1Int+""); //$NON-NLS-1$
		}
		Pc1_text.setBounds(135, 30, 67, 20);
		Pc1_text.addVerifyListener(new VerifyListener() {
			@Override
			public void verifyText(final VerifyEvent e) {

				// TODO Auto-generated method stub
				if (e.text.length() == 1) {
					boolean d = false;
					boolean b = ("0123456789".indexOf(e.text) >= 0); //$NON-NLS-1$
					System.out.println("this place" + Pc1_text.getText()); //$NON-NLS-1$
					if (Pc1_text.getText().contains(".")) //$NON-NLS-1$
					{
						d = ("0123456789.".indexOf(e.text) >= 0); //$NON-NLS-1$
						e.doit = (b && d);
					}
				}
			}
		});

		final Label Pm_label = new Label(shell, SWT.NONE);
		Pm_label.setText(Messages.getString("APGASettingsDialog.11")); //$NON-NLS-1$
		Pm_label.setBounds(10, 90, 104, 20);

		if(!flag)
		{
		Pm_text = new Text(shell, SWT.BORDER);
		Pm_text.setText("0.15"); //$NON-NLS-1$
		}
		else
		{
			Pm_text = new Text(shell, SWT.READ_ONLY);
			Pm_text.setText(pmInt+""); //$NON-NLS-1$
		}
		Pm_text.setBounds(135, 85, 67, 20);
		Pm_text.addVerifyListener(new VerifyListener() {
			@Override
			public void verifyText(final VerifyEvent e) {

				// TODO Auto-generated method stub
				if (e.text.length() == 1) {
					boolean d = false;
					boolean b = ("0123456789".indexOf(e.text) >= 0); //$NON-NLS-1$
					System.out.println("this place" + Pm_text.getText()); //$NON-NLS-1$
					if (Pm_text.getText().contains(".")) //$NON-NLS-1$
					{
						d = ("0123456789.".indexOf(e.text) >= 0); //$NON-NLS-1$
						e.doit = (b && d);
					}
				}
			}
		});

		final Label K_label = new Label(shell, SWT.NONE);
		K_label.setText(Messages.getString("APGASettingsDialog.16")); //$NON-NLS-1$
		K_label.setBounds(206, 35, 104, 20);
        if(!flag)
        {
		K_text = new Text(shell, SWT.BORDER);
		K_text.setText("500"); //$NON-NLS-1$
        }else
        {
        	K_text = new Text(shell, SWT.READ_ONLY);
    		K_text.setText(kInt+""); //$NON-NLS-1$
        }
		K_text.setBounds(316, 32, 67, 20);
		K_text.addVerifyListener(new VerifyListener() {
			@Override
			public void verifyText(final VerifyEvent e) {

				// TODO Auto-generated method stub
				if (e.text.length() == 1) {
					boolean d = false;
					boolean b = ("0123456789".indexOf(e.text) >= 0); //$NON-NLS-1$
					System.out.println("this place" + K_text.getText()); //$NON-NLS-1$
					if (K_text.getText().contains(".")) //$NON-NLS-1$
					{
						d = ("0123456789.".indexOf(e.text) >= 0); //$NON-NLS-1$
						e.doit = (b && d);
					}
				}
			}
		});

		final Label Pt_label = new Label(shell, SWT.NONE);
		Pt_label.setText(Messages.getString("APGASettingsDialog.21")); //$NON-NLS-1$
		Pt_label.setBounds(206, 90, 104, 20);

		if(!flag)
		{
		Pt_text = new Text(shell, SWT.BORDER);
		Pt_text.setText("0.95"); //$NON-NLS-1$
		}
		else
		{
			Pt_text = new Text(shell, SWT.READ_ONLY);
			Pt_text.setText(ptInt+""); //$NON-NLS-1$
		}
		Pt_text.setBounds(316, 87, 67, 20);
		Pt_text.addVerifyListener(new VerifyListener() {
			@Override
			public void verifyText(final VerifyEvent e) {

				// TODO Auto-generated method stub
				if (e.text.length() == 1) {
					boolean d = false;
					boolean b = ("0123456789".indexOf(e.text) >= 0); //$NON-NLS-1$
					System.out.println("this place" + Pt_text.getText()); //$NON-NLS-1$
					if (Pt_text.getText().contains(".")) //$NON-NLS-1$
					{
						d = ("0123456789.".indexOf(e.text) >= 0); //$NON-NLS-1$
						e.doit = (b && d);
					}
				}
			}
		});

		final Button ok_button = new Button(shell, SWT.NONE);
		ok_button.setText(Messages.getString("APGASettingsDialog.26")); //$NON-NLS-1$
		ok_button.setBounds(83, 197, 66, 22);
		ok_button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if(!flag)
				{
				// 点击confirm按钮的操作
				Pc1 = Pc1_text.getText();
				Pc2 = "0.65";
				Pm = Pm_text.getText();
				K = K_text.getText();
				Pt = Pt_text.getText();
				NG = N_text.getText();
				if (Pc1.trim().equals("") || Pc2.trim().equals("") //$NON-NLS-1$ //$NON-NLS-2$
						|| Pm.trim().equals("") || K.trim().equals("") //$NON-NLS-1$ //$NON-NLS-2$
						|| Pt.trim().equals("") || NG.trim().equals("")) { //$NON-NLS-1$ //$NON-NLS-2$
					return;
				}
				result = 1;
				}
				shell.dispose();
			}
		});

		final Button cancelbutton = new Button(shell, SWT.NONE);
		cancelbutton.setText(Messages.getString("APGASettingsDialog.33")); //$NON-NLS-1$
		cancelbutton.setBounds(250, 197, 67, 22);
		cancelbutton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				// 点击confirm按钮的操作

				result = 0;
				shell.dispose();
			}
		});

		final Label N_label = new Label(shell, SWT.NONE);
		N_label.setText(Messages.getString("APGASettingsDialog.34")); //$NON-NLS-1$
		N_label.setBounds(10, 146, 103, 20);

		if(!flag)
		{
		N_text = new Text(shell, SWT.BORDER);
		N_text.setText("500"); //$NON-NLS-1$
		}
		else
		{
			N_text = new Text(shell, SWT.READ_ONLY);
			N_text.setText(ngInt+""); //$NON-NLS-1$
		}
		N_text.setBounds(135, 141, 67, 20);
		N_text.addVerifyListener(new VerifyListener() {
			@Override
			public void verifyText(final VerifyEvent e) {
				// TODO Auto-generated method stub
				if (e.text.length() == 1) {
					boolean b = ("0123456789".indexOf(e.text) >= 0); //$NON-NLS-1$
					e.doit = b;
				}
			}
		});
	}
}
