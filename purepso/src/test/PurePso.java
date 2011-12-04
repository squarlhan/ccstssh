package test;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

import pso.FitnessFunction;
import pso.Swarm;

public class PurePso {

	/**
	 * 
	 * @param max_gen
	 *            算法迭代次数
	 * @param numofparticals
	 *            粒子总数
	 * @param dimention
	 *            每个粒子维数，有几位实数编码
	 * @param intertia
	 *            惯性
	 * @param velocity
	 *            速度
	 * @param scopes
	 *            每一维的取值范围
	 * @param fitness
	 *            适应度计算
	 */
	public void Calculate(int max_gen, int numofparticals, int dimention,
			double intertia, double velocity, List<List<Double>> scopes,
			FitnessFunction fitness, BufferedWriter output) {
		// System.out.println("Begin: MaxTest 1\n");
		long startTime = System.currentTimeMillis();
		// Create a swarm (using 'MyParticle' as sample particle and
		// 'MyFitnessFunction' as finess function)
		Swarm swarm = new Swarm(numofparticals, new MaxParticle(dimention),
				new CosMaxFunction());

		// Set position (and velocity) constraints. I.e.: where to look for
		// solutions
		double[] lowers = new double[dimention];
		double[] uppers = new double[dimention];
		for (int i = 0; i <= dimention - 1; i++) {
			lowers[i] = scopes.get(i).get(0);
			uppers[i] = scopes.get(i).get(1);
		}
		swarm.setInertia(intertia);
		swarm.setMaxPosition(uppers);
		swarm.setMinPosition(lowers);
		swarm.setMaxMinVelocity(velocity);
		swarm.setMaxMinVelocity(velocity);

		int numberOfIterations = max_gen;

		// Optimize (and time it)
		for (int i = 0; i < numberOfIterations; i++) {
			swarm.evolve();
		}

		// Print en results
		System.out.println(swarm.toStringStats());
		long endTime = System.currentTimeMillis();
		System.out.println("运行时间 " + (endTime - startTime) + "ms");
		try {
			output.write(swarm.getBestFitness()+"\t");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}