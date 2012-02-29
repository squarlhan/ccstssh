package apso;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import pso.Particle;
import pso.Swarm;
import test.MaxParticle;

import affinitymain.InteractionData;
import affinitymain.RunAlgorithm;
import algorithm.abs.AffinityPropagationAlgorithm.AffinityConnectingMethod;

public class RunAP {
	
	public void run(Swarm runningobj, APSO obj, double p_lamda, double p_extra, int ap_max, double ap_lamda, double lamda, BufferedWriter output){
		runimpl(runningobj, obj, p_lamda, p_extra, ap_max, ap_lamda, lamda, output);
	}
	
	private  void runimpl(Swarm runningobj, APSO obj, double p_lamda, double p_extra, int ap_max, double ap_lamda, double lamda, BufferedWriter output) {
		
		double[][] chromatrix = pop2matrix(runningobj);
		double[][] dis = EucDistance.calcEucMatrix(chromatrix);
		Collection<InteractionData> inputs = EucDistance.transEucMatrix(dis);
		Double lambda = ap_lamda;
		Integer iterations = ap_max;
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
			for(int i = 0; i<= runningobj.size()-1; i++){
				results.add(i);
			}
			System.err.println("Cluster Error, 0 result!");	
		}
		List<Double> objests = cof.calcFittnessValue(runningobj, obj,  results, dis, lamda, p_lamda, p_extra, output);
		 try {
				for (Particle mychrom : runningobj.getParticles()) {
					Particle mychrom1 = new MaxParticle(mychrom.getDimention());
					for(int i = 0; i<= mychrom.getDimention()-1; i++){
						mychrom1.getPosition()[i] = mychrom.getPosition()[i];
					}
					double a1 = runningobj.getFitnessFunction().evaluate(mychrom1);
					double a2 = mychrom.getFitness();
				    output.write(Math.abs(a1-a2) + "\t");
			}
			output.write("\n");
			output.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
  }

	private double[][]  pop2matrix(Swarm runningobj){
    	Particle[] particles = runningobj.getParticles();
  	  if(particles==null){
  		  return null;
  	  }
  	  int m = runningobj.getNumberOfParticles();
  	  int n = particles[0].getDimention();
  	  double[][]  result = new double[m][n];
  	  for(int i = 0; i<=m-1;i++){
  		  for(int j = 0; j<=n-1;j++){
  			result[i][j] = particles[i].getPosition()[j];
  		  }
  	  }
  	  return result;
    }

}
