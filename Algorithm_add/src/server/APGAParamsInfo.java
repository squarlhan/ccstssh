package server;

import java.io.Serializable;

/**
 * GA算法属性结构体
 * 
 * @author wxp
 * 
 */
@SuppressWarnings("serial")
public class APGAParamsInfo implements Serializable {
	
	
	/**
	 *  用来指定模式发现的概率，0到1之间
	 */
	private double Pc1;
	/**
	 * 用来指定模式发现的增幅，0到1之间
	 */
	private double Pc2;
	/**
	 *用来指定ap算法迭代次数
	 */
	private double Pm;
	/**
	 * ap算法的lamda，决定收敛速度，越大越慢 0.5到0.95之间
	 */
	private double K;
	/**
	 * ap算法后，估算适应度的lamda 0到1之间
	 */
	private double Pt;
	/**
	 * 内部迭代次数
	 */
	private int NG;

	public APGAParamsInfo(double pc1, double pc2, double pm, double k, double pt,
			int ng) {
		super();
		Pc1 = pc1;
		Pc2 = pc2;
		Pm = pm;
		K = k;
		Pt = pt;
		NG = ng;
	}

	public APGAParamsInfo() {

	}

	/**
	 * 获取交叉概率1
	 * @return 交叉概率1
	 */
	public double getPc1() {
		return Pc1;
	}

	/**
	 * 设置交叉概率1
	 * @param pc1 交叉概率1
	 */
	public void setPc1(double pc1) {
		Pc1 = pc1;
	}

	/**
	 * 获取交叉概率2
	 * @return
	 */
	public double getPc2() {
		return Pc2;
	}

	/**
	 * 设置交叉概率2
	 * @param pc2
	 */
	public void setPc2(double pc2) {
		Pc2 = pc2;
	}

	/**
	 * 获取变异概率
	 * @return 变异概率
	 */
	public double getPm() {
		return Pm;
	}

	/**
	 * 设置变异概率
	 * @param pm 变异概率
	 */
	public void setPm(double pm) {
		Pm = pm;
	}

	/**
	 * 获取温度系数
	 * @return 温度系数
	 */
	public double getK() {
		return K;
	}

	/**
	 * 设置温度系数
	 * @param k 温度系数
	 */
	public void setK(double k) {
		K = k;
	}

	/**
	 * 获取降温系数
	 * @return 降温系数
	 */
	public double getPt() {
		return Pt;
	}

	/**
	 * 设置降温系数
	 * @param pt 降温系数
 	 */
	public void setPt(double pt) {
		Pt = pt;
	}

	/**
	 * 获取内部迭代次数
	 * @return 内部迭代次数
	 */
	public int getNG() {
		return NG;
	}

	/**
	 * 设置内部迭代次数
	 * @param ng 内部迭代次数
	 */
	public void setNG(int ng) {
		NG = ng;
	}

}
