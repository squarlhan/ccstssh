package svmga;

import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.List;

import org.jgap.Configuration;
import org.jgap.FitnessFunction;
import org.jgap.IChromosome;
import org.jgap.Population;

public class GetFit {
	
	public void getfitness(Population a_pop, Configuration a_conf, SVMGA obj,
			FitnessFunction fitness, double gamma, double c, BufferedWriter output){
//确保没有算过适应度的个体的染色体都是false
    	
    	int oldpopsize = a_conf.getPopulationSize();
    	int nowpopsize = a_pop.size();
    	for(int i = oldpopsize;i<nowpopsize;i++){
    		a_pop.getChromosome(i).setIscenter(false);
    	}
		if(obj.getSvmin().size()<200){
			for(IChromosome chrom:a_pop.getChromosomes()){
				chrom.getFitnessValue();
				chrom.setIscenter(true);
				obj.getSvmin().add(chrom);
			}
		}else{
			List<IChromosome> chroms = a_pop.getChromosomes();
			List<IChromosome> pre_chrom = new ArrayList();
			for(int i = 0; i<=chroms.size()-1;i++){
				if(!chroms.get(i).isIscenter()&&i%2==0){
					chroms.get(i).getFitnessValue();
					chroms.get(i).setIscenter(true);
					obj.getSvmin().add(chroms.get(i));					
				}else if(!chroms.get(i).isIscenter()&&i%2==1){
					pre_chrom.add(chroms.get(i));
				}
			}
			SVMTest st = new SVMTest();
			st.predict(st.getmodel(obj.getSvmin(), gamma, c), pre_chrom);
		}
		
	}

}
