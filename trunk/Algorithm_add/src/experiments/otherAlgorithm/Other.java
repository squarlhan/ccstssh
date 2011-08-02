package experiments.otherAlgorithm;

import experiments.Matrix;
import experiments.ga.GAFunction;

public class Other {
	/**
	 * 该方法中GAFunction finess参数必须要有，负责调用模拟器运行，其它参数视算法需要传入的参数而定，参考GA算法中的该方法
	 *该方法是调用算法的入口方法，有该方法调用算法来优化出一批参数值，然后送往模拟器运行
	 * @param fitness
	 * @param Pc
	 * @param pc1
	 * @param Pm
	 * @param T
	 * @param Pt
	 * @param consValue
	 * @param lastPos
	 * @param pBest
	 * @param NG
	 * @return
	 */
	public Object[] Calculate(GAFunction fitness, double Pc, double pc1,
			double Pm, double T, double Pt, Matrix consValue, Matrix lastPos,
			Matrix pBest, int NG){
		// 调用算法，调用模拟器，返回模拟器运行完的目标函数值，参考GA算法中的该方法的使用
	}
}
