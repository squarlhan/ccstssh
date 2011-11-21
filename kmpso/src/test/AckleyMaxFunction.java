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

import java.util.List;

import pso.FitnessFunction;



/**
 * Fitness function for our example. See evaluate(...) method for details.
 *
 * @author Neil Rotstan
 * @author Klaus Meffert
 * @since 2.0
 */
public class AckleyMaxFunction 
    extends FitnessFunction{
  /** String containing the CVS revision. Read out via reflection!*/
  private final static String CVS_REVISION = "$Revision: 1.6 $";
  
  public static int counts = 0;

  public double evaluate(double position[]) {
    double total = 0;
    double totalcos = 0;
    int time_delay = 0;
    try {
		Thread.sleep(time_delay);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    int n = position.length;
    for (int i = 0; i < n; i++) {
      
    	total +=  position[i]*position[i];
    	totalcos += (Math.cos(2*position[i]*Math.PI));
      
    }
    counts ++;
    double result = 20*Math.exp(-0.2*Math.sqrt(total/n))+Math.exp(totalcos/n);
    result = 1000*(result)/(20+Math.exp(1));
    return result;
  }
}
