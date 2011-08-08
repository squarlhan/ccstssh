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

			

				long startTime = System.currentTimeMillis();
				int numEvolutions = 80;
				Configuration gaConf = new DefaultConfiguration();

				gaConf.setPreservFittestIndividual(true);
				gaConf.setKeepPopulationSizeConstant(false);
				Genotype genotype = null;			
				
				int chromeSize = 30;
//				double maxFitness =chromeSize * 111;
				double maxFitness = chromeSize * Math.pow(10, 2.0);
				try {
					//构建基因(Gene)
					Gene[] sampleGenes = new Gene[chromeSize];//基因长度2
					 for (int i = 0; i < sampleGenes.length; i++) {					    
							sampleGenes[i] = new DoubleGene(gaConf, -10, 10);
					 }
					// 构建染色体(Chromosome)
					 IChromosome sampleChromosome = new Chromosome(gaConf, sampleGenes);
					 gaConf.setSampleChromosome(sampleChromosome);
					gaConf.setPopulationSize(40);
					gaConf.setFitnessFunction(new SimpleMaxFunction());
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
				DecimalFormat myformat = new DecimalFormat("#0.00");
				for (int i = 0; i < chromeSize; i++) {

					//System.out.println(myformat.format(((DoubleGene)fittest.getGene(i)).doubleValue()));
					System.out.print(myformat.format(fittest.getGene(i).getAllele())+"	");
				}
				System.out.println();
				long endTime = System.currentTimeMillis();
				System.out.println("运行时间 " + (endTime - startTime) + "ms");
				System.out.println("sum counts:  "+ SimpleMaxFunction.counts);
				
				
			
	}
}
