package svmpso;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pso.FitnessFunction;
import pso.Particle;
import pso.Swarm;
import test.MaxFunction;
import test.MaxParticle;

public class SVMPSO {
	
	private int progress = 0;
	private int nIterateCount=0;
	private List<Particle> svmin_history = null;
	private List<Particle> svmin_local = null;
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


	public List<Particle> getSvmin_history() {
		return svmin_history;
	}



	public void setSvmin_history(List<Particle> svmin_history) {
		this.svmin_history = svmin_history;
	}



	public List<Particle> getSvmin_local() {
		return svmin_local;
	}



	public void setSvmin_local(List<Particle> svmin_local) {
		this.svmin_local = svmin_local;
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
	 * @param gamma      svm的gamma
	 * @param c    svm罚分
	 *  @param percent    群体中有多少是真实算的用来加入到训练集
	 */
	public void Calculate(int max_gen, int numofparticals, int dimention, double intertia, 
			double velocity, 	List<List<Double>> scopes, FitnessFunction fitness, 
			double gamma, double c,	int percent, BufferedWriter output){
		
		svmin_history = new ArrayList();
		svmin_local = new ArrayList();
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
			swarm.evolve(this, gamma, c,percent, output);
		}
			

		// Print en results
		System.out.println(swarm.toStringStats());
		long endTime = System.currentTimeMillis();
		System.out.println("运行次数 " + MaxFunction.counts);
		System.out.println("运行时间 " + (endTime - startTime) + "ms");
		try {
//			output.write(swarm.getBestFitness()+"\t");
			output.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println("Fitness count: " + MaxFunction.counts);
//		System.out.println("End: Example 1");
	
		
	}

}
