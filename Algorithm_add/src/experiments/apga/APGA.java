package experiments.apga;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.Population;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.DoubleGene;

import experiments.Matrix;
import experiments.ga.GAFunction;
import experiments.ga.MaxFunction;

public class APGA {
private int nIterateCount=0;
private Population bestPop = null;
private List<String> patterns = new ArrayList();
private List<String> fitnessvalues = new ArrayList();

	
	
	public List<String> getFitnessvalues() {
	return fitnessvalues;
}

public void setFitnessvalues(List<String> fitnessvalues) {
	this.fitnessvalues = fitnessvalues;
}

	public List<String> getPatterns() {
	return patterns;
}

public void setPatterns(List<String> patterns) {
	this.patterns = patterns;
}

	public Population getBestPop() {
	return bestPop;
}

public void setBestPop(Population bestPop) {
	this.bestPop = bestPop;
}

	public int getnIterateCount() {
		return nIterateCount;
	}

	public void setnIterateCount(int nIterateCount) {
		this.nIterateCount = nIterateCount;
	}

	/**
	 * 基因算法优化
	 * @param fitness 目标函数
	 * @param Pc      交叉概率 用来指定模式发现的概率，0到1之间
	 * @param pc1    交叉概率1 用来指定模式发现的增幅，0到1之间
	 * @param Pm      变异概率 用来指定ap算法迭代次数
	 * @param T       ap算法的lamda，决定收敛速度，越大越慢 0.5到0.95之间
	 * @param Pt      ap算法后，估算适应度的lamda 0到1之间
	 * @param consValue 不确定参数取值范围矩阵 2行N列
	 * @param lastPos   初始种群 M行N列
	 * @param pBest     适应度 1行M列
	 * @param NG        基因算法内部优化迭代次数
	 * @return Object[] 新种群，更新后的适应度，不确定参数取值范围矩阵
	 */
	public Object[] Calculate(APGAFunction fitness, double Pc, double pc1,
			double Pm, double T, double Pt, Matrix consValue, Matrix lastPos,
			Matrix pBest, int NG, BufferedWriter output) {
		//确认所有矩阵的大小正确
		if(consValue==null||consValue.getM()!=2||consValue.getN()<1){
			System.out.println("consValue   M:" + consValue.getM() + " N:"+ consValue.getN());
			System.out.println("Invalid Matrix consValue!");
			return null;
		}
		if(pBest!=null && lastPos!=null){
		    if (pBest.getM() != 1|| pBest.getN() != lastPos.getM()||lastPos.getN()!=consValue.getN()) {
			    // Matrix p must be 1 row!
			    System.out.println("pBest   M:" + pBest.getM() + " N :"+ pBest.getN());
			    System.out.println("lastPos   M:" + lastPos.getM() + " N:"+ lastPos.getN());
			    System.out.println("Invalid Matrix pBest or lastPos!");
			    return null;
		    }
		}
		if(Pc>=1||Pc<=0){
			System.out.println("Pc Should be between 0 and 1");
		    return null;
		}
		if(pc1>=1||pc1<0){
			System.out.println("pc1 Should be between 0 and 1");
		    return null;
		}
		if(Pt>=1||Pt<=0){
			System.out.println("Pt Should be between 0 and 1");
		    return null;
		}
		if(T>=0.95||T<=0.5){
			System.out.println("T Should be between 0.5 and 0.95");
		    return null;
		}
		
		
		int popSize = lastPos.getM();
		int chromeSize = lastPos.getN();
		Matrix pBest_ga = new Matrix(lastPos.data);
		
		int numEvolutions = NG;
		Configuration gaConf = new DefaultConfiguration();
		gaConf.reset();
		gaConf.setPreservFittestIndividual(true);
		gaConf.setKeepPopulationSizeConstant(false);
		Genotype genotype = null;
		
		
		
		try {
			//构建基因(Gene)
			Gene[] sampleGenes = new DoubleGene[chromeSize];//基因长度
			 for (int i = 0; i < sampleGenes.length; i++) {					    
					sampleGenes[i] = new DoubleGene(gaConf, consValue.data[0][i],consValue.data[1][i]);
			 }
			// 构建染色体(Chromosome)
			 IChromosome sampleChromosome = new Chromosome(gaConf, sampleGenes);
			gaConf.setSampleChromosome(sampleChromosome);
			
			gaConf.setPopulationSize(popSize);	
			gaConf.setFitnessFunction(new MaxFunction());
			genotype = Genotype.randomInitialGenotype(gaConf);
			
			bestPop = new Population(gaConf);
			
			if(lastPos!=null){		
                for(int i = 0; i<=popSize-1; i++){
                	//DoubleGene[] tempGenes = new DoubleGene[chromeSize];
                	for(int j = 0; j<=chromeSize-1;j++ ){
                		genotype.getPopulation().getChromosome(i).getGene(j) .setAllele(lastPos.data[i][j]) ;
                	}
                //	IChromosome tempchrom = new Chromosome(gaConf, tempGenes);
               // 	genotype.getPopulation().setChromosome(i, tempchrom);
                }
			}
			if(lastPos!=null&&pBest!=null){
				for(int i = 0; i<=popSize-1; i++){
					genotype.getPopulation().getChromosome(i).setFitnessValue(pBest.data[0][i]);
				}
			}
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
			System.exit(-2);
		}
		int progress = 0;
		int percentEvolution = numEvolutions / 10;
		for (int i = 0; i < numEvolutions; i++) {
        	//Start GA
			genotype.evolve(this, fitness, (int)Pm, T, Pt, Pc, pc1);
			// Print progress.
			
			Population temppop = genotype.getPopulation();
			
			// ---------------
			if (percentEvolution > 0 && i % percentEvolution == 0) {
				progress++;
				IChromosome fittest = null;
				if(bestPop.size()>popSize/2){
					fittest = bestPop.determineFittestChromosome();
				}else{
					fittest = genotype.getFittestChromosome();
				}			
				double best_fitness = fittest.getFitnessValueDirectly();
				System.out.println("Currently fittest Chromosome has fitness "+ best_fitness);
				// if (fitness >= maxFitness) {
				// break;
				// }
			}
		}
		// Print summary.
		// --------------
		IChromosome fittest = null;
		if(bestPop.size()>0){
			fittest = bestPop.determineFittestChromosome();
		}else{
			fittest = genotype.getFittestChromosome();
		}			
		System.out.println("Fittest Chromosome has fitness "+ fittest.getFitnessValueDirectly());
		try {
			output.write(fittest.getFitnessValueDirectly() + "\n");

			DecimalFormat myformat = new DecimalFormat("#0.00");
			for (int i = 0; i < chromeSize; i++) {
				System.out.print(myformat
						.format(fittest.getGene(i).getAllele()) + "	");
				output.write(myformat.format(fittest.getGene(i).getAllele())
						+ "	");
			}
			System.out.println();
			output.write("\n");
			System.out.println("sum counts: " + MaxFunction.counts);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//处理返回结果
		for(int i = 0; i<=bestPop.size()-1; i++){
        	pBest.data[0][i] = bestPop.getChromosome(i).getFitnessValueDirectly();
		}
		pBest_ga = pop2matrix(bestPop);
		//printsth("patterns.txt", patterns);
		//printsth("values.txt", fitnessvalues);
		return new Object[] { pBest_ga, pBest, consValue };

	}// end of this math

	private void printsth(String filename, List<String> contents){
		try {
			File result = new File(filename);
			if (result.exists()) {
				result.delete();
				if (result.createNewFile()) {
					System.out.println("result file create success!");
				} else {
					System.out.println("result file create failed!");
				}
			} else {
				if (result.createNewFile()) {
					System.out.println("result file create success!");
				} else {
					System.out.println("result file create failed!");
				}

			}

			BufferedWriter output = new BufferedWriter(new FileWriter(result));
			
			for(String pattern : contents){
				output.write(pattern+"\n");
			}
			
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
}
