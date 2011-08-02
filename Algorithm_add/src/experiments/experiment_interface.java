package experiments;

import java.util.Vector;

/**
 * 算法接口
 * @author wxp
 *
 */
public interface experiment_interface {
	public abstract Matrix experiment_design(String[] type, double[] xmin,
			double[] xmax, int nvar, int nsample, double[] mean, double[] exp);
	
	/**
	 * 初始化
	 * @param max 最大值 
	 * @param min 最小值
	 * @param nvar 个数
	 */
	public abstract void initDistrib(Vector<Double> max,Vector<Double> min,int nvar);
	
	/**
	 * 设置不确定参数
	 * @param s
	 */
	public abstract void set_uncertainty_parameters(String s);

    /**
     * 设置响应参数
     * @param s
     */
	public abstract void set_response_parameters(String s);
    /**
     * 设置观测值参数
     * @param s
     */
	public abstract void set_observe_parameters(String s);

	/**
	 * 设置模拟结果
	 * @param s
	 * @param ad
	 * @param d
	 */
	public abstract void set_simulate_result(String s, double ad[], double d);

	/**
	 * 获取结果
	 * @return
	 */
	public abstract Matrix get_result();

	/**
	 * 检查状态
	 * @return 
	 */
	public abstract int check_state();

	/**
	 * 设置迭代次数
	 * @param i 迭代次数
	 */
	public abstract void setIterative(int i);
}

