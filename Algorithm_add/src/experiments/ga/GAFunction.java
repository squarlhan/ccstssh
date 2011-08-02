package experiments.ga;

import experiments.Matrix;

/**
 * 执行基因算法的模拟
 * @author
 * 
 */
public interface GAFunction {
	Double[] excute(Matrix data, int nIterateCount);
}
