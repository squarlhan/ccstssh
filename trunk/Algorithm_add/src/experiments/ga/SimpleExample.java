/*
 * This file is part of JGAP.
 *
 * JGAP offers a dual license model containing the LGPL as well as the MPL.
 *
 * For licensing information please see the file license.txt included with JGAP
 * or have a look at the top of class org.jgap.Chromosome which representatively
 * includes the JGAP license policy applicable for any file delivered with JGAP.
 */
package experiments.ga;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;

import org.jgap.*;
import org.jgap.impl.*;

/**
 * Simple class that demonstrates the basic usage of JGAP.
 * 
 * @author Neil Rotstan
 * @author Klaus Meffert
 * @since 2.0
 */
public class SimpleExample {
	/** String containing the CVS revision. Read out via reflection! */
	private static final String CVS_REVISION = "$Revision: 1.9 $";
	
	public void runga(int ng, int chromeSize, int popsize, FitnessFunction fitnessfun, BufferedWriter output){
		long startTime = System.currentTimeMillis();
		int numEvolutions = ng;
		Configuration gaConf = new DefaultConfiguration();
		gaConf.reset();
		gaConf.setPreservFittestIndividual(true);
		gaConf.setKeepPopulationSizeConstant(false);
		Genotype genotype = null;			
		
		try {
			//构建基因(Gene)
			Gene[] sampleGenes = new Gene[chromeSize];//基因长度2
			 for (int i = 0; i < sampleGenes.length; i++) {					    
					sampleGenes[i] = new DoubleGene(gaConf, -10, 10);
			 }
			// 构建染色体(Chromosome)
			 IChromosome sampleChromosome = new Chromosome(gaConf, sampleGenes);
			 gaConf.setSampleChromosome(sampleChromosome);
			gaConf.setPopulationSize(popsize);
			gaConf.setFitnessFunction(fitnessfun);
			genotype = Genotype.randomInitialGenotype(gaConf);
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
			System.exit(-2);
		}
		int progress = 0;
		int percentEvolution = numEvolutions / 10;
		for (int i = 0; i < numEvolutions; i++) {
			genotype.evolve();
			// Print progress.
			// ---------------
			if (percentEvolution > 0 && i % percentEvolution == 0) {
				progress++;
				IChromosome fittest = genotype.getFittestChromosome();
				double fitness = fittest.getFitnessValue();
				System.out.println("Currently fittest Chromosome has fitness "+ fitness);
			}
		}
		// Print summary.
		// --------------
		IChromosome fittest = genotype.getFittestChromosome();
		System.out.println("Fittest Chromosome has fitness "
				+ (fittest.getFitnessValue()));
		try {
			output.write(fittest.getFitnessValue() + "\n");

			DecimalFormat myformat = new DecimalFormat("#0.00");
			for (int i = 0; i < chromeSize; i++) {

				// System.out.println(myformat.format(((DoubleGene)fittest.getGene(i)).doubleValue()));
				System.out.print(myformat
						.format(fittest.getGene(i).getAllele()) + "	");
				output.write(myformat
						.format(fittest.getGene(i).getAllele()) + "	");
			}
			System.out.println();
			output.write("\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		System.out.println("运行时间 " + (endTime - startTime) + "ms");
		System.out.println("sum counts:  "+ SimpleMaxFunction.counts);
	}

	/**
	 * Starts the example.
	 * 
	 * @param args
	 *            if optional first argument provided, it represents the number
	 *            of bits to use, but no more than 32
	 * 
	 * @author Neil Rotstan
	 * @author Klaus Meffert
	 * @throws IOException 
	 * @since 2.0
	 */
	public static void main(String[] args) throws IOException {

		SimpleExample se = new SimpleExample();	
		
		try {
			File result = new File("ga_x_80.txt");
			if (result.exists()) {
				result.delete();
				if (result.createNewFile()) {
					System.out.println("result3 file create success!");
				} else {
					System.out.println("result3 file create failed!");
				}
			} else {
				if (result.createNewFile()) {
					System.out.println("result3 file create success!");
				} else {
					System.out.println("result3 file create failed!");
				}

			}
			File result2 = new File("ga_cos_80.txt");
			if (result2.exists()) {
				result2.delete();
				if (result2.createNewFile()) {
					System.out.println("result4 file create success!");
				} else {
					System.out.println("result4 file create failed!");
				}
			} else {
				if (result2.createNewFile()) {
					System.out.println("result4 file create success!");
				} else {
					System.out.println("result4 file create failed!");
				}

			}

			BufferedWriter output = new BufferedWriter(new FileWriter(result));
			BufferedWriter output2 = new BufferedWriter(new FileWriter(result2));
			
			for(int a=0; a<=0;a++){
				se.runga(200, 30, 40, new SimpleMaxFunction(), output);
				se.runga(200, 30, 40, new CosSimpleMaxFunction(), output2);
			}
			
			output.close();
			output2.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
				
				
			
	}
}
