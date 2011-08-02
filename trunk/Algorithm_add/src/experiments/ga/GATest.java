package experiments.ga;

import experiments.Matrix;

/**
 * @author 
 * 对应基因算法的变量数组，个体适应度，相对适应度，累加适应度
 */
class GenoType {
	/**
	 * 变量数组
	 */
	double[] gene; 
	/**
	 * 个体适应度
	 */
	double fitness; 
	/**
	 * 相对适应度
	 */
	double rfitness;  
	/**
	 * 累加适应度
	 */
	double cfitness; 

	/**
	 * 初始化
	 * @param nvar 参数个数
	 */
	GenoType(int nvar) {
		gene = new double[nvar];
		for (int i = 0; i < nvar; i++)
			gene[i] = 0.0;
		fitness = 0.0;
		rfitness = 0.0;
		cfitness = 0.0;
	};
};

/**
 * @author 
 * 基因算法优化
 */
public class GATest {
	int nIterateCount = 0;
	/**
	 * 变量最大值
	 */
	public double[] Var_max; 
	/**
	 * 变量最小值
	 */
	public double[] Var_min; 
	/**
	 * 不确定参数个数
	 */
	public int Var_num; 
	/**
	 * 种群规模
	 */
	public int Pop_size; 
	/**
	 * 最大代数
	 */
	public int Gen_max; 

	/**
	 * 交叉概率
	 */
	public double Pc; 
	/**
	 * 变异概率
	 */
	public double Pm; 

	/**
	 * 初始温度
	 */
	public double T0; 
	/**
	 * 衰减系数
	 */
	public double arf; 
	/**
	 * 当前温度
	 */
	public double T; 

	/**
	 * 当前世代数
	 */
	public int gen; 
	/**
	 * 最好的个体在种群中的序号
	 */
	public int cur_best; 
	/**
	 * 种群
	 */
	public GenoType[] pop; 
	/**
	 * 新种群
	 */
	public GenoType[] newpop; 
	/**
	 * 对适应度更新
	 */
	public double tempValue;

	
	/**
	 * 初始化参数
	 * @param nvar 不确定参数个数
	 * @param popsize 种群规模
	 * @param xmax 不确定参数最大参数组
	 * @param xmin 不确定参数最小参数组
	 * @param pc 交叉概率
	 * @param pm 变异概率
	 * @param gen_max 基因算法内部迭代次数
	 * @param t0 初始温度
	 * @param arf 退火系数 
	 * @param temp 评价值计算中间值
	 */
	public void Ga_ini(int nvar, int popsize, double[] xmax, double[] xmin,
			double pc, double pm, int gen_max, double t0, double arf,double temp) {
		int j;
        tempValue = 2*temp;
		Var_num = nvar;// 不确定参数个数

		Var_max = new double[nvar];// 最大值
		Var_min = new double[nvar];// 最小值
		Pop_size = popsize;// 种群规模

		for (j = 0; j < nvar; j++) {// 获取 最大，最小值组
			Var_max[j] = xmax[j];
			Var_min[j] = xmin[j];
		}

		Pc = pc;// 交叉概率
		Pm = pm;// 变异概率

		pop = new GenoType[Pop_size + 1];// 种群规模
		newpop = new GenoType[Pop_size + 1];

		for (j = 0; j < Pop_size + 1; j++) {// 二维数组
			pop[j] = new GenoType(Var_num);
			newpop[j] = new GenoType(Var_num);
		}

		Gen_max = gen_max;
		gen = 0;// 代数

		T0 = t0; // 初始温度
		this.arf = arf; // 衰减系数
	}
 
	/**
	 * 获得交由模拟器运算的数据
	 * @param lastPos 不确定参数组矩阵
	 * @param pBest   适应度矩阵
	 * @param temp    评价值计算中间值
	 * @return 新的不确定参数组
	 */
	public Matrix newpop_dots(Matrix lastPos, Matrix pBest,double temp) {
		int i, j;
		Matrix s = Matrix.zeros(Pop_size, Var_num);// 全部赋0

		if (gen == 0) {
			if (lastPos != null)// 如果是取已有的结果计算
			{
				double maxValue =  temp -pBest.data[0][0];
				int k = 0;
				for (j = 1; j < Pop_size; j++) {
					if ((temp -pBest.data[0][j]) > maxValue) {
						maxValue =temp -pBest.data[0][j];
						k = j;
					}
				}
				System.out.println("k : " + k);
				System.out.println("lastPos" + lastPos.data[k][0] + "gggg");
				System.out.println(pBest.data[0][0]);
				for (j = 0; j < Pop_size; j++) {// 第0代，取运算结果值

					for (i = 0; i < Var_num; i++) {
						pop[j].gene[i] = lastPos.data[j][i];
					}
				}
				for (i = 0; i < Var_num; i++) {
					System.out.println(i + " : " + lastPos.data[k][i]);
					pop[Pop_size].gene[i] = lastPos.data[k][i];
				}
			} else {// 如果是单独计算
				for (j = 0; j < Pop_size + 1; j++) {// 第0代，随机取值
					for (i = 0; i < Var_num; i++)
						pop[j].gene[i] = Var_min[i] + Math.random()
								* (Var_max[i] - Var_min[i]);
				}
			}
		} else {
			if (gen == 1)// 如果 是第一代
				keep_the_best();// 保存最好个体（传值进来）
			elitist();// 最好个体的处理
			select();// 选择
			crossover();// 交叉
			mutate();// 变异
			renew_pcpm();// 交叉概率、变异概率更新
		}

		for (i = 0; i < Pop_size; i++)
			for (j = 0; j < Var_num; j++)
				s.data[i][j] = pop[i].gene[j];

		gen++;// 代数加1

		return s;
	} 
	
	/**
	 * 适应度尺度变换
	 */
	public void chg_fit() {
		int mem;

		T = T0 * Math.pow(arf, gen);

		double maxf = pop[0].fitness;
		for (mem = 1; mem < Pop_size; mem++) {
			if (pop[mem].fitness > maxf)
				maxf = pop[mem].fitness;
		}

		for (mem = 0; mem < Pop_size; mem++) {
			pop[mem].fitness = Math.exp(-(maxf - pop[mem].fitness) / T);
		}
	}

	/**
	 * 保存最后个体
	 */
	private void keep_the_best() {
		cur_best = 0;
		for (int i = 0; i < Pop_size; i++) {
			if (pop[i].fitness > pop[cur_best].fitness) {
				cur_best = i;// 记录序号
				pop[Pop_size].fitness = pop[i].fitness; // 更新适应度
			}
		}

		// 将最好的个体拷贝到数组的最后项中
		for (int i = 0; i < Var_num; i++)
			pop[Pop_size].gene[i] = pop[cur_best].gene[i]; // 更新基因
	}

	/**
	 * 最好个体处理
	 */
	private void elitist() {
		int i;
		double best, worst; // best and worst fitness values
		int best_mem = 0, worst_mem = 0; // indexes of the best and worst member

		// 找出当前种群中最好的个体和最差的个体
		best = pop[0].fitness;
		worst = pop[0].fitness;
		for (i = 0; i < Pop_size - 1; ++i) {
			if (pop[i].fitness > pop[i + 1].fitness) {
				if (pop[i].fitness >= best) {
					best = pop[i].fitness;
					best_mem = i;
				}
				if (pop[i + 1].fitness <= worst) {
					worst = pop[i + 1].fitness;
					worst_mem = i + 1;
				}
			} else {
				if (pop[i].fitness <= worst) {
					worst = pop[i].fitness;
					worst_mem = i;
				}
				if (pop[i + 1].fitness >= best) {
					best = pop[i + 1].fitness;
					best_mem = i + 1;
				}
			}
		}

		if (best >= pop[Pop_size].fitness) {// 如果当前最好个体比以前最好个体好，更新最好种群
			for (i = 0; i < Var_num; i++)
				pop[Pop_size].gene[i] = pop[best_mem].gene[i];
			pop[Pop_size].fitness = pop[best_mem].fitness;
		} else {// 否则，将上次最好个体覆盖当前最差个体
			for (i = 0; i < Var_num; i++)
				pop[worst_mem].gene[i] = pop[Pop_size].gene[i];
			pop[worst_mem].fitness = pop[Pop_size].fitness;
		}
	}

		
	/**
	 * 选择适应度好的个体
	 */
	private void select() {
		int mem, i, j, k;
		double sum = 0.0;
		double p;

		for (mem = 0; mem < Pop_size; mem++) {
			sum += pop[mem].fitness;
		}
		// 计算相对适应度
		for (mem = 0; mem < Pop_size; mem++) {
			pop[mem].rfitness = pop[mem].fitness / sum;
		}

		// 计算累计适应度
		pop[0].cfitness = pop[0].rfitness;

		for (i = 1; i < Pop_size; i++) {
			double temp;
			temp = pop[i - 1].cfitness + pop[i].rfitness;
			pop[i].cfitness = temp;
		}

		for (i = 0; i < Pop_size; i++) {
			p = Math.random();
			if (p < pop[0].cfitness) {
				for (k = 0; k < Var_num; k++)
					newpop[i].gene[k] = pop[0].gene[k];
			} else {
				for (j = 0; j < Pop_size; j++) {
					if (p >= pop[j].cfitness && p < pop[j + 1].cfitness) {
						for (k = 0; k < Var_num; k++)
							newpop[i].gene[k] = pop[j + 1].gene[k];
						break;
					}
				}
			}
		}

		for (i = 0; i < Pop_size; i++)
			for (k = 0; k < Var_num; k++)
				pop[i].gene[k] = newpop[i].gene[k];
	}

	/**
	 * 交叉
	 */
	private void crossover() {
		int pos, one = 0;
		int first = 0; /* count of the number of posbers chosen */
		double x;

		for (pos = 0; pos < Pop_size; ++pos) {
			x = Math.random();
			if (x < Pc) { // 交叉概率
				++first;
				if (first % 2 == 0) {
					// Cross(one, pos);
					int point; /* crossover point */
					double drand;
					double d1, d2;

					point = (int) (Math.random() * Var_num); // 第point个变量进行交叉操作
					drand = Math.random();
					d1 = drand * pop[one].gene[point] + (1 - drand)
							* pop[pos].gene[point];
					d2 = drand * pop[pos].gene[point] + (1 - drand)
							* pop[one].gene[point];
					pop[one].gene[point] = d1;
					pop[pos].gene[point] = d2;
				} else
					one = pos;
			}
		}
	}

	/**
	 * 变异 
	 */
	private void mutate() {
		int i, j;
		double x;

		for (i = 0; i < Pop_size; i++)
			for (j = 0; j < Var_num; j++) {
				x = Math.random();
				if (x < Pm) {
					// 产生在low和high之间的随机值
					pop[i].gene[j] = Var_min[j] + Math.random()
							* (Var_max[j] - Var_min[j]);
				}
			}
	}

	/**
	 * 交叉概率 变异概率更新
	 */
	private void renew_pcpm() {
		Pc = 1.0 * Math.cos(gen * 3.14159 / (2.0 * Gen_max));
		Pm = 0.15 * Math.cos(gen * 3.14159 / (2.0 * Gen_max));
	}
	
	/**
	 * 适应度更新
	 * @param kvalue 适应度数组
	 */
	public void renew_fit(double[] kvalue) {
		for (int i = 0; i < Pop_size; i++)
			pop[i].fitness = kvalue[i];

		//chg_fit();
	}
	
	/**
	 * 计算均方差
	 * @return 均方差
	 */
	public double cal_stddev() {
		int i;
		double avg; // 均值
		double stddev; // 均方差
		double sum_square;//平方和
		double square_sum;//均方和
		double sum; //和

		sum = 0.0;
		sum_square = 0.0;

		for (i = 0; i < Pop_size; i++) {
			sum += pop[i].fitness;
			sum_square += pop[i].fitness * pop[i].fitness;
		}

		avg = sum / (double) Pop_size;
		square_sum = avg * avg * Pop_size;
		stddev = Math.sqrt((sum_square - square_sum) / (Pop_size - 1));

		return stddev;
	}

	/**
	 * 基因算法优化
	 * @param fitness 目标函数
	 * @param Pc      交叉概率
	 * @param pc1     交叉概率1
	 * @param Pm      变异概率
	 * @param T       初始温度
	 * @param Pt      降温系数
	 * @param consValue 不确定参数取值范围矩阵
	 * @param lastPos   初始种群
	 * @param pBest     适应度
	 * @param NG        基因算法内部优化迭代次数
	 * @return Object[] 新种群，更新后的适应度，不确定参数取值范围矩阵
	 */
	public Object[] Calculate(GAFunction fitness, double Pc, double pc1,
			double Pm, double T, double Pt, Matrix consValue, Matrix lastPos,
			Matrix pBest, int NG) {
		if (pBest == null || pBest.getM() != 1
				|| pBest.getN() != lastPos.getM()) {
			// Matrix p must be 1 row!
			System.out.println("pBest   M:" + pBest.getM() + " N :"
					+ pBest.getN());
			System.out.println("lastPos   M:" + lastPos.getM() + " N:"
					+ lastPos.getN());
			System.out.println("Invalid Matrix pBest!");
			return null;
		}
		Matrix pBest_ga = new Matrix(lastPos.data);

		double stddev = 0.0, oldstddev = 0.0;
		double[] xmax = new double[consValue.getN()];
		double[] xmin = new double[consValue.getN()];
		double[] kvalue = new double[lastPos.getM()];

		GATest ga = new GATest();
		for (int j = 0; j < consValue.getN(); ++j) {
			xmax[j] = consValue.data[1][j];
			xmin[j] = consValue.data[0][j];
		}
		double temp = pBest.getData()[0][0];
		
		for(int i=1;i<pBest.getN();i++)
		{
			if(temp<pBest.getData()[0][i])
				temp = pBest.getData()[0][i];
		}
		
		ga.Ga_ini(consValue.getN(), lastPos.getM(), xmax, xmin, Pc, Pm, NG+1, T,
				Pt,temp);
		while (ga.gen < ga.Gen_max) {
			System.out.println("代数" + ga.gen);
			pBest_ga = ga.newpop_dots(lastPos, pBest,ga.tempValue);// 迭代运算

			if (ga.gen == 1) {
				System.out.println("这是第0代！");
				System.out.println(pBest.data[0][0]);
				for (int mem = 0; mem < ga.Pop_size; ++mem) {
					kvalue[mem] = ga.tempValue -pBest.data[0][mem];
				}
			} else {
				Double[] newValue = fitness.excute(pBest_ga, nIterateCount);
				nIterateCount += lastPos.getM();
				for (int mem = 0; mem < ga.Pop_size; ++mem) {
					kvalue[mem] = ga.tempValue -newValue[mem];
					pBest.data[0][mem] = ga.tempValue -kvalue[mem];
				}
			}
			oldstddev = stddev;
			ga.renew_fit(kvalue);// 更新适应度
			stddev = ga.cal_stddev();// 计算均方差
			System.out.print(ga.gen + "\t" + stddev + "\n");
			if (stddev + oldstddev < 0.001)// 如果连续三次结果相同，刚退出
				break;
		}

		return new Object[] { pBest_ga, pBest, consValue };

	}// end of this math

	public int GetIterateCount() {
		// TODO Auto-generated method stub
		return nIterateCount;
	}
}
