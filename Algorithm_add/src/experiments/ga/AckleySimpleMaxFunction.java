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

import java.util.List;

import org.jgap.*;
import org.jgap.impl.*;

import experiments.Matrix;

/**
 * Fitness function for our example. See evaluate(...) method for details.
 *
 * @author Neil Rotstan
 * @author Klaus Meffert
 * @since 2.0
 */
public class AckleySimpleMaxFunction 
    extends FitnessFunction implements  GAFunction{
  /** String containing the CVS revision. Read out via reflection!*/
  private final static String CVS_REVISION = "$Revision: 1.6 $";
  
  public static int counts = 0;

  /**
   * This example implementation calculates the fitness value of Chromosomes
   * using BooleanAllele implementations. It simply returns a fitness value
   * equal to the numeric binary value of the bits. In other words, it
   * optimizes the numeric value of the genes interpreted as bits. It should
   * be noted that, for clarity, this function literally returns the binary
   * value of the Chromosome's genes interpreted as bits. However, it would
   * be better to return the value raised to a fixed power to exaggerate the
   * difference between the higher values. For example, the difference
   * between 254 and 255 is only about .04%, which isn't much incentive for
   * the selector to choose 255 over 254. However, if you square the values,
   * you then get 64516 and 65025, which is a difference of 0.8% -- twice
   * as much and, therefore, twice the incentive to select the higher
   * value.
   *
   * @param a_subject the Chromosome to be evaluated
   * @return defect rate of our problem
   *
   * @author Neil Rotstan
   * @author Klaus Meffert
   * @since 2.0
   */
  public double evaluate(IChromosome a_subject) {
    double total = 0;
    double totalcos = 0;
    int time_delay = 0;
    try {
		Thread.sleep(time_delay);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    int n = a_subject.size();
    for (int i = 0; i < n; i++) {
      
    	total +=  (((DoubleGene)a_subject.getGene(i)).doubleValue()*((DoubleGene)a_subject.getGene(i)).doubleValue());
    	totalcos += (Math.cos(2*((DoubleGene)a_subject.getGene(i)).doubleValue()*Math.PI));
      
    }
    counts ++;
    double result = 20*Math.exp(-0.2*Math.sqrt(total/n))+Math.exp(totalcos/n);
    result = 1000*(result)/(20+Math.exp(1));
    return result;
  }

@Override
public Double[] excute(Matrix data, int nIterateCount) {
	int time_delay = 0;
	// TODO Auto-generated method stub
	if(data == null)return null;
	int m = data.getM();
	int n = data.getN();
	Double[] results = new Double[m];
	try {
		Thread.sleep(time_delay*m);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	for(int i = 0; i<=m-1; i++){
		double total = 0;
		for(int j = 0; j<=n-1; j++){
			total+=Math.pow(data.data[i][j], 2.0);
		}
		results[i] = 2*Math.pow(5.12, 2.0)-total;
	}
	counts += m;
	return results;
}
}
