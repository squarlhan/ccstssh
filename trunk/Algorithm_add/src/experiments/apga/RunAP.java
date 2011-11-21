package experiments.apga;

import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgap.Configuration;
import org.jgap.FitnessFunction;
import org.jgap.Population;

import experiments.Matrix;

import affinitymain.InteractionData;
import affinitymain.RunAlgorithm;
import algorithm.abs.AffinityPropagationAlgorithm.AffinityConnectingMethod;

public class RunAP {

	public void run(Population a_pop, Configuration a_conf, APGA obj,
			APGAFunction fitness, int ap_num, double ap_lamda,
			double fit_lamda, double cutoff, double extra) {
		// runimplafter(a_pop, a_conf, obj, fitness, ap_num, ap_lamda,
		// fit_lamda, cutoff, extra, output);
		runimpl(a_pop, a_conf, obj, fitness, ap_num, ap_lamda, fit_lamda,
				cutoff, extra);
	}

	private void runimpl(Population a_pop, Configuration a_conf, APGA obj, APGAFunction fitness, int ap_num, double ap_lamda, double fit_lamda, double cutoff, double extra) {

		double[][] chromatrix = pop2matrix(a_pop).data;
		double[][] dis = EucDistance.calcEucMatrix(chromatrix);
		Collection<InteractionData> inputs = EucDistance
				.transEucMatrix(dis);
		Double lambda = ap_lamda;
		Integer iterations = ap_num;
		clustObjectFun cof = new clustObjectFun();
		Integer convits = null;
		Double preferences = dis[0][0];

		String kind = "clusters";
		AffinityConnectingMethod connMode = AffinityConnectingMethod.ORIGINAL;
		boolean takeLog = false;
		boolean refine = true;
		Integer steps = null;

		RunAlgorithm alg = new RunAlgorithm(inputs, lambda, iterations,
				convits, preferences, kind);
		alg.setTakeLog(takeLog);
		alg.setConnMode(connMode);
		alg.setSteps(steps);
		alg.setRefine(refine);

		alg.setParemeters();
		List<Integer> results = alg.run();
		if(results==null||results.size()==0){
			for(int i = 0; i<= a_pop.size()-1; i++){
				results.add(i);
			}
			System.err.println("Cluster Error, 0 result!");	
		}
		List<Double> objests = cof.calcFittnessValue(a_pop, obj, fitness, results, dis, fit_lamda, cutoff, extra);
		// for (int i = 0; i < currentPopSize; i++) {
		// IChromosome chrom = a_pop.getChromosome(i);
		// System.out.print(chrom.getFitnessValue()+";");
		// }
		// System.out.print("\n");

	}

	private void runimplafter(Population a_pop, Configuration a_conf, APGA obj, APGAFunction fitness, int ap_num, double ap_lamda, double fit_lamda, double cutoff, double extra) {

		double[][] chromatrix = pop2matrixafter(a_pop, a_conf).data;
		double[][] dis = EucDistance.calcEucMatrix(chromatrix);
		double[][] fullchromatrix = pop2matrix(a_pop).data;
		double[][] fulldis = EucDistance.calcEucMatrix(fullchromatrix);
		Collection<InteractionData> inputs = EucDistance.transEucMatrix(dis);
		Double lambda = ap_lamda;
		Integer iterations = ap_num;
		clustObjectFun cof = new clustObjectFun();
		Integer convits = null;
		Double preferences = dis[0][0];

		String kind = "clusters";
		AffinityConnectingMethod connMode = AffinityConnectingMethod.ORIGINAL;
		boolean takeLog = false;
		boolean refine = true;
		Integer steps = null;

		RunAlgorithm alg = new RunAlgorithm(inputs, lambda, iterations,
				convits, preferences, kind);
		alg.setTakeLog(takeLog);
		alg.setConnMode(connMode);
		alg.setSteps(steps);
		alg.setRefine(refine);

		alg.setParemeters();

		int oldpopsize = a_conf.getPopulationSize();
		List<Integer> tempresults = alg.run();
		Set<Integer> temp = new HashSet();
		temp.addAll(tempresults);
		List<Integer> results = new ArrayList();
		if (tempresults == null || tempresults.size() == 0) {
			for (int i = 0; i <= a_pop.size() - 1; i++) {
				results.add(i);
			}
			System.err.println("Cluster Error, 0 result!");
		} else {
			for (int result : tempresults) {
				results.add(result + oldpopsize);
			}
			for (int i = oldpopsize - 1; i >= 0; i--) {
				results.add(0, i);
			}
		}
		List<Double> objests = cof.calcFittnessValue(a_pop, obj, fitness,
				results, fulldis, fit_lamda, cutoff, extra);
		// try {
		// for (IChromosome mychrom : a_pop.getChromosomes()) {
		// IChromosome mychrom1 = (IChromosome) mychrom.clone();
		// double a1 = fitness.evaluate(mychrom1);
		// double a2 = mychrom.getFitnessValueDirectly();
		// output.write(Math.abs(a1-a2) + "\t");
		// }
		// output.write("\n");
		// output.flush();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

	}

	private Matrix pop2matrix(Population pop){
		  if(pop==null){
			  return null;
		  }
		  int m = pop.size();
		  int n = pop.getChromosome(0).size();
		  Matrix result = new Matrix(m,n);
		  for(int i = 0; i<=m-1;i++){
			  for(int j = 0; j<=n-1;j++){
				  result.data[i][j] = (Double)pop.getChromosome(i).getGene(j).getAllele();
			  }
		  }
		  return result;
	  }
	
	private Matrix pop2matrixafter(Population pop, Configuration a_conf) {
		int oldpopsize = a_conf.getPopulationSize();
		if (pop.size() <= oldpopsize) {
			return null;
		}
		int m = pop.size() - oldpopsize;
		int n = pop.getChromosome(0).size();
		 Matrix result = new Matrix(m,n);
		for (int i = 0; i <= m - 1; i++) {
			for (int j = 0; j <= n - 1; j++) {
				result.data[i][j] = (Double) pop.getChromosome(i + oldpopsize)
						.getGene(j).getAllele();
			}
		}
		return result;
	}

}
