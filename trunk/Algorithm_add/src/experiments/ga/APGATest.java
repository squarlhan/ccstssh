package experiments.ga;

import java.text.DecimalFormat;

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

public class APGATest {
	
	private int nIterateCount=0;
	
	
	public int getnIterateCount() {
		return nIterateCount;
	}

	public void setnIterateCount(int nIterateCount) {
		this.nIterateCount = nIterateCount;
	}

	/**
	 * 基因算法优化
	 * @param fitness 目标函数
	 * @param Pc      交叉概率
	 * @param pc1    交叉概率1
	 * @param Pm      变异概率 用来指定ap算法迭代次数
	 * @param T       ap算法的lamda，决定收敛速度，越大越慢
	 * @param Pt      ap算法后，估算适应度的lamda
	 * @param consValue 不确定参数取值范围矩阵 2行N列
	 * @param lastPos   初始种群 M行N列
	 * @param pBest     适应度 1行M列
	 * @param NG        基因算法内部优化迭代次数
	 * @return Object[] 新种群，更新后的适应度，不确定参数取值范围矩阵
	 */
	public Object[] Calculate(GAFunction fitness, double Pc, double pc1,
			double Pm, double T, double Pt, Matrix consValue, Matrix lastPos,
			Matrix pBest, int NG) {
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
		int popSize = lastPos.getM();
		int chromeSize = lastPos.getN();
		Matrix pBest_ga = new Matrix(lastPos.data);
		
		int numEvolutions = NG;
		Configuration gaConf = new DefaultConfiguration();
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
			genotype.evolve(this, fitness, (int)Pm, T, Pt);
			// Print progress.
			// ---------------
			if (percentEvolution > 0 && i % percentEvolution == 0) {
				progress++;
				IChromosome fittest = genotype.getFittestChromosome();
				double best_fitness = fittest.getFitnessValue();
				System.out.println("Currently fittest Chromosome has fitness "+ best_fitness);
				// if (fitness >= maxFitness) {
				// break;
				// }
			}
		}
		// Print summary.
		// --------------
		IChromosome fittest = genotype.getFittestChromosome();
		System.out.println("Fittest Chromosome has fitness "+ fittest.getFitnessValue());
		DecimalFormat myformat = new DecimalFormat("#0.00");
		for (int i = 0; i < chromeSize; i++) {
			System.out.print(myformat.format(fittest.getGene(i).getAllele())+"	");
		}
		System.out.println();
		
		//处理返回结果
		for(int i = 0; i<=popSize-1; i++){
        	pBest.data[0][i] = genotype.getPopulation().getChromosome(i).getFitnessValue();
		}
		pBest_ga = pop2matrix(genotype.getPopulation());
		return new Object[] { pBest_ga, pBest, consValue };

	}// end of this math

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
	
	public  void runapgaexample() {
		long startTime=System.currentTimeMillis();
		int numEvolutions = 200;
		Configuration gaConf = new DefaultConfiguration();
		gaConf.setPreservFittestIndividual(true);
		gaConf.setKeepPopulationSizeConstant(false);
		Genotype genotype = null;
		int chromeSize = 2;
		double maxFitness = 2 * Math.pow(5.12, 2.0);
		try {
			//构建基因(Gene)
			Gene[] sampleGenes = new DoubleGene[chromeSize];//基因长度
			 for (int i = 0; i < sampleGenes.length; i++) {					    
					sampleGenes[i] = new DoubleGene(gaConf, -5.12, 5.12);
			 }
			// 构建染色体(Chromosome)
			 IChromosome sampleChromosome = new Chromosome(gaConf, sampleGenes);
			gaConf.setSampleChromosome(sampleChromosome);

			gaConf.setPopulationSize(40);	
			gaConf.setFitnessFunction(new MaxFunction());
			genotype = Genotype.randomInitialGenotype(gaConf);
			//genotype.getConfiguration().setFitnessFunction(new APFunction(genotype));
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
			System.exit(-2);
		}
		int progress = 0;
		int percentEvolution = numEvolutions / 10;
		for (int i = 0; i < numEvolutions; i++) {
			
			
			//Start GA
			genotype.evolve(this,  new MaxFunction(), 100, 0.7, 0.7);
			// Print progress.
			// ---------------
			if (percentEvolution > 0 && i % percentEvolution == 0) {
				progress++;
				IChromosome fittest = genotype.getFittestChromosome();
				double fitness = fittest.getFitnessValue();
				System.out.println("Currently fittest Chromosome has fitness "
						+ fitness);

				System.out.println("counts:  "+ nIterateCount);
				// if (fitness >= maxFitness) {
				// break;
				// }
			}
		}
		// Print summary.
		// --------------
		IChromosome fittest = genotype.getFittestChromosome();
		System.out.println("Fittest Chromosome has fitness "
				+ (maxFitness-fittest.getFitnessValue()));
		DecimalFormat myformat = new DecimalFormat("#0.00");
		for (int i = 0; i < 2; i++) {

			System.out.println(myformat.format(fittest.getGene(i).getAllele()));
		}
		long endTime=System.currentTimeMillis();
	    System.out.println("运行时间： "+(endTime-startTime)+"ms");
	}
}
