package experiments.ui;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
/**
 * 基因算法参数设置
 * @author GEO
 *
 */
public class APGASettings implements IMethodProperty {

	/**
	 * 提交结果
	 */
	private int result;
	/**
	 * 交叉概率1
	 */
	private double Pc1;
	/**
	 * 交叉概率2
	 */
	private double Pc2;
	/**
	 * 变异概率
	 */
	private double Pm;
	/**
	 * 温度系数
	 */
	private double K;
	/**
	 * 降温系数
	 */
	private double Pt;
	/**
	 * 内部迭代次数
	 */
	private int NG;

	@Override
	public void setProperty() {
		// TODO Auto-generated method stub
		Display display = Display.getDefault();
		final Shell shell = new Shell(display);
		APGASettingsDialog apga = new APGASettingsDialog(shell);

		Object result = apga.open();
		if (result == null) {
			return;
		}
		if (result.equals(0)) {
			this.result = 0;
			System.out.println("你点击的是取消按钮");
		} else if (result.equals(1)) {
			this.result = 1;
			System.out.println("你点击的 确定是按钮");
            Pc1 = Double.parseDouble(apga.Pc1);
            Pc2 = Double.parseDouble(apga.Pc2);
            Pm = Double.parseDouble(apga.Pm);
            Pt = Double.parseDouble(apga.Pt);
            K = Double.parseDouble(apga.K);
			NG = Integer.parseInt(apga.NG);
		}
	}

	/**
	 * @return 提交结果
	 */
	public int getResult() {
		return this.result;
	}

	/**
	 * @return 交叉概率1
	 */
	public double getPc1() {
		return this.Pc1;
	}

	/**
	 * @return 交叉概率2
	 */
	public double getPc2() {
		return this.Pc2;
	}

	/**
	 * @return 变异概率
	 */
	public double getPm() {
		return this.Pm;
	}

	/**
	 * @return 温度系数
	 */
	public double getK() {
		return this.K;
	}

	/**
	 * @return 降温系数
	 */
	public double getPt() {
		return this.Pt;
	}

	/**
	 * @return 内部迭代次数
	 */
	public int getNG() {
		return this.NG;
	}
}
