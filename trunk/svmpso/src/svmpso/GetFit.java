package svmpso;

import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.List;

import pso.FitnessFunction;
import pso.Particle;
import pso.Swarm;


public class GetFit {
	
	public void getfitness(Swarm runningobj, SVMPSO obj,int percent, double gamma, double c, BufferedWriter output){
//		getfitnesshistory(a_pop, a_conf, obj, percent, gamma, c, output);
		getfitnesslocal(runningobj, obj, percent, gamma, c, output);
	}
	
	public void getfitnesshistory(Swarm runningobj, SVMPSO obj,  int percent, double gamma, double c, BufferedWriter output){

		FitnessFunction fitness = runningobj.getFitnessFunction();
		if(obj.getSvmin_history().size()<200){
			for(Particle chrom:runningobj.getParticles()){
				chrom.setFitness(fitness.evaluate(chrom), true);
				obj.getSvmin_history().add(chrom);
			}
		}else{
			Particle[] chroms = runningobj.getParticles();
			List<Particle> pre_chrom = new ArrayList();
			for(int i = 0; i<=chroms.length-1;i++){
					if(i%percent==0){
						chroms[i].setFitness(fitness.evaluate(chroms[i]), true);
						obj.getSvmin_history().add(chroms[i]);
					}else{
						pre_chrom.add(chroms[i]);
					}
			}
			SVMTest st = new SVMTest();
			st.predict(st.getmodel(obj.getSvmin_history(), gamma, c), pre_chrom);
		}
		
	}
	
	public void getfitnesslocal(Swarm runningobj, SVMPSO obj, int percent, double gamma, double c, BufferedWriter output){
//确保没有算过适应度的个体的染色体都是false
		SVMTest st = new SVMTest();
		FitnessFunction fitness = runningobj.getFitnessFunction();
		if(obj.getSvmin_history().size()<200){
			for(Particle chrom:runningobj.getParticles()){
				chrom.setFitness(fitness.evaluate(chrom), true);
				if(chrom.getFitness()>1000){
					System.out.println("before!");
				}
				obj.getSvmin_history().add(chrom);
			}
		}else{
			Particle[] chroms = runningobj.getParticles();
			List<Particle> pre_chrom = new ArrayList();
			for(int i = 0; i<=chroms.length-1;i++){
				if(i%percent==0){
					    chroms[i].setFitness(fitness.evaluate(chroms[i]), true);
						if(chroms[i].getFitness()>1000){
							System.out.println("ininin!"+chroms[i].getFitness());
						}
						obj.getSvmin_history().add(chroms[i]);
				}
			}
			obj.setSvmin_local(new ArrayList<Particle>());
			for(int i = obj.getSvmin_history().size()-200;i<= obj.getSvmin_history().size()-1;i++){
				obj.getSvmin_local().add(obj.getSvmin_history().get(i));
			}
			for(int i = 0; i<=chroms.length-1;i++){
				if(i%percent!=0){
						chroms[i].setFitness(st.predict(st.getmodel(obj.getSvmin_local(), gamma, c), chroms[i]), false);
				}
			}
			
//			SVMTest st = new SVMTest();
//			st.predict(st.getmodel(obj.getSvmin_local(), gamma, c), pre_chrom);
		}
		return;
	}

}
