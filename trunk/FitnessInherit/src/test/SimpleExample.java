/*
 * This file is part of JGAP.
 *
 * JGAP offers a dual license model containing the LGPL as well as the MPL.
 *
 * For licensing information please see the file license.txt included with JGAP
 * or have a look at the top of class org.jgap.Chromosome which representatively
 * includes the JGAP license policy applicable for any file delivered with JGAP.
 */
package test;

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
	
	public void runga(double lamda, int ng, int chromeSize, int popsize, double left, double right, FitnessFunction fitnessfun, BufferedWriter output){
		long startTime = System.currentTimeMillis();
		int numEvolutions = ng;
		Configuration gaConf = new DefaultConfiguration();
		gaConf.reset();
		gaConf.setPreservFittestIndividual(true);
		gaConf.setKeepPopulationSizeConstant(false);
		Genotype genotype = null;			
		
		try {
			//鏋勫缓鍩哄洜(Gene)
			Gene[] sampleGenes = new Gene[chromeSize];//鍩哄洜闀垮害2
			 for (int i = 0; i < sampleGenes.length; i++) {					    
					sampleGenes[i] = new DoubleGene(gaConf, left, right);
			 }
			// 鏋勫缓鏌撹壊锟�Chromosome)
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
			genotype.evolve(lamda, output);
			Population temppop = genotype.getPopulation();
//				for (IChromosome mychrom : temppop.getChromosomes()) {
//					output.write(Math.abs(fitnessfun.evaluate(mychrom)-mychrom.getFitnessValueDirectly()) + "\t");
//				}
//				output.write("\n");
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

			// System.out.println(myformat.format(((DoubleGene)fittest.getGene(i)).doubleValue()));
			System.out.print(myformat.format(fittest.getGene(i).getAllele()) + "	");
//				output.write(myformat.format(fittest.getGene(i).getAllele()) + "	");
		}
		System.out.println();
		try {
			output.write(fittest.getFitnessValue() + "\t");
			output.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//			output.write("\n");
		long endTime = System.currentTimeMillis();
		System.out.println("杩愯鏃堕棿 " + (endTime - startTime) + "ms");
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
			File[] result = {new File("ga_x.txt"), new File("ga_cos.txt"), 
					new File("ga_ackley.txt"),	new File("ga_quar.txt"), 
					new File("ga_step.txt"), new File("ga_rosen.txt"), 
					new File("ga_sch.txt"), new File("ga_gri.txt"),
					new File("ga_pen1.txt"), new File("ga_pen2.txt"),
			        new File("ga_wei.txt"), new File("ga_non.txt"),
			        new File("counts.txt")};
			BufferedWriter[] output = new BufferedWriter[result.length];
			for(int i = 0; i<= result.length-1;i++){
				if (result[i].exists()) {
					result[i].delete();
					if (result[i].createNewFile()) {
						System.out.println("result"+i+" file create success!");
					} else {
						System.out.println("result"+i+" file create failed!");
					}
				} else {
					if (result[i].createNewFile()) {
						System.out.println("result"+i+" file create success!");
					} else {
						System.out.println("result"+i+" file create failed!");
					}

				}
				output[i] = new BufferedWriter(new FileWriter(result[i]));
			}
			
			for(int a=0; a<=49;a++){
				
				se.runga(0.5000005, 200, 30, 40, -100,  100, new SimpleMaxFunction(), output[0]);
				se.runga(0.5, 200, 30, 40, -5.12,  5.12, new CosSimpleMaxFunction(), output[1]);
				se.runga(0.5, 200, 30, 40, -32,  32, new AckleySimpleMaxFunction(), output[2]);
				se.runga(0.5, 200, 30, 40, -100,  100, new QuardircSimpleMaxFunction(), output[3]);
				se.runga(0.5, 200, 30, 40, -100,  100, new StepSimpleMaxFunction(), output[4]);
				se.runga(0.5, 200, 30, 40, -30,  30, new RosenbrockSimpleMaxFunction(), output[5]);
				se.runga(0.5, 200, 30, 40, -500,  500, new SchwefelSimpleMaxFunction(), output[6]);
				se.runga(0.5, 200, 30, 40, -600,  600, new GriewankSimpleMaxFunction(), output[7]);
				se.runga(0.5, 200, 30, 40, -50,  50, new PenalizedSimpleMaxFunction(), output[8]);
				se.runga(0.5, 200, 30, 40, -50,  50, new Penalized2SimpleMaxFunction(), output[9]);
				se.runga(0.5, 200, 30, 40, -5.12,  5.12, new WeiSimpleMaxFunction(), output[10]);
				se.runga(0.5, 200, 30, 40, -0.5,  0.5, new NonSimpleMaxFunction(), output[11]);
				
				output[12].write(SimpleMaxFunction.counts+"\t");
        		output[12].write(CosSimpleMaxFunction.counts+"\t");
        		output[12].write(AckleySimpleMaxFunction.counts+"\t");
        		output[12].write(QuardircSimpleMaxFunction.counts+"\t");
        		output[12].write(StepSimpleMaxFunction.counts+"\t");
        		output[12].write(RosenbrockSimpleMaxFunction.counts+"\t");
        		output[12].write(SchwefelSimpleMaxFunction.counts+"\t");
        		output[12].write(GriewankSimpleMaxFunction.counts+"\t");
        		output[12].write(PenalizedSimpleMaxFunction.counts+"\t");
        		output[12].write(Penalized2SimpleMaxFunction.counts+"\t");
        		output[12].write(WeiSimpleMaxFunction.counts+"\t");
        		output[12].write(NonSimpleMaxFunction.counts+"\t");
        		SimpleMaxFunction.counts = 0;
        		CosSimpleMaxFunction.counts = 0;
        		AckleySimpleMaxFunction.counts = 0;
        		QuardircSimpleMaxFunction.counts = 0;
        		StepSimpleMaxFunction.counts = 0;
        		RosenbrockSimpleMaxFunction.counts = 0;
        		SchwefelSimpleMaxFunction.counts = 0;
        		GriewankSimpleMaxFunction.counts = 0;
        		PenalizedSimpleMaxFunction.counts = 0;
        		Penalized2SimpleMaxFunction.counts = 0;
        		WeiSimpleMaxFunction.counts = 0;
        		NonSimpleMaxFunction.counts = 0;
				for(BufferedWriter op : output){
					op.write("\n");
					op.flush();
				}
			}
			
			for(BufferedWriter op : output){
				op.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
				
				
			
	}
}
