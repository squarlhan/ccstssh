package kmpso;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pso.FitnessFunction;
import pso.Particle;
import pso.Swarm;
import test.MaxFunction;
import test.MaxParticle;

public class KMPSO {
	
	private int progress = 0;
	private int nIterateCount=0;
	private List<Particle> bestPop = null;
	private List<Particle> localPop = null;
	private List<Particle> localWorst = null;
	private List<String> patterns = new ArrayList();
	private List<String> fitnessvalues = new ArrayList();
	
	
	
	public int getProgress() {
		return progress;
	}



	public void setProgress(int progress) {
		this.progress = progress;
	}



	public int getnIterateCount() {
		return nIterateCount;
	}



	public void setnIterateCount(int nIterateCount) {
		this.nIterateCount = nIterateCount;
	}



	public List<Particle> getBestPop() {
		return bestPop;
	}



	public void setBestPop(List<Particle> bestPop) {
		this.bestPop = bestPop;
	}



	public List<Particle> getLocalPop() {
		return localPop;
	}



	public void setLocalPop(List<Particle> localPop) {
		this.localPop = localPop;
	}



	public List<Particle> getLocalWorst() {
		return localWorst;
	}



	public void setLocalWorst(List<Particle> localWorst) {
		this.localWorst = localWorst;
	}



	public List<String> getPatterns() {
		return patterns;
	}



	public void setPatterns(List<String> patterns) {
		this.patterns = patterns;
	}



	public List<String> getFitnessvalues() {
		return fitnessvalues;
	}



	public void setFitnessvalues(List<String> fitnessvalues) {
		this.fitnessvalues = fitnessvalues;
	}



	/**
	 * 
	 * @param max_gen 算法迭代次数
	 * @param numofparticals 粒子总数
	 * @param dimention 每个粒子维数，有几位实数编码
	 * @param intertia 惯性
	 * @param velocity 速度
	 * @param scopes 每一维的取值范围
	 * @param fitness 适应度计算
	 * @param p_lamda 模式估计lamda
	 * @param p_extra 模式估计增值
	 * @param kmeans_max 聚类算法迭代次数
	 * @param kmeans_num 聚类个数
	 * @param lamda 适应度估计的lamda
	 */
	public void Calculate(int max_gen, int numofparticals, int dimention, double intertia, 
			double velocity, 	List<List<Double>> scopes, FitnessFunction fitness, 
			double p_lamda, double p_extra,	int kmeans_max, double kmeans_num, double lamda, BufferedWriter output){
		

//		System.out.println("Begin: MaxTest 1\n");
		long startTime = System.currentTimeMillis();
		// Create a swarm (using 'MyParticle' as sample particle and
		// 'MyFitnessFunction' as finess function)
		Swarm swarm = new Swarm(numofparticals, new MaxParticle(dimention), fitness);

		// Set position (and velocity) constraints. I.e.: where to look for
		// solutions
		double[] lowers = new double[dimention];
		double[] uppers = new double[dimention];
		for(int i = 0; i<= dimention-1; i++){
			lowers[i] = scopes.get(i).get(0);
			uppers[i] = scopes.get(i).get(1);
		}
		swarm.setInertia(intertia);
		swarm.setMaxPosition(uppers);
		swarm.setMinPosition(lowers);
		swarm.setMaxMinVelocity(velocity);

		int numberOfIterations = max_gen;

		for (int i = 0; i < numberOfIterations; i++){
			progress++;
			swarm.evolve(this, p_lamda, p_extra,	kmeans_max, kmeans_num, lamda);
		}
			

		// Print en results
		System.out.println(swarm.toStringStats());
		long endTime = System.currentTimeMillis();
		System.out.println("运行时间 " + (endTime - startTime) + "ms");
		try {
			output.write(swarm.getBestFitness()+"\t");
			output.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println("Fitness count: " + MaxFunction.counts);
//		System.out.println("End: Example 1");
	
		
	}

}
