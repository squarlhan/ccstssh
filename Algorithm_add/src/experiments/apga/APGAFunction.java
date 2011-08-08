package experiments.apga;

import experiments.Matrix;

/**
 * 执行基因算法的模拟, 事实上和GAFunction是一样的
 * @author
 * 
 */
public interface APGAFunction {
	Double[] excute(Matrix data, int nIterateCount);
}
