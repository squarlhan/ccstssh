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
import experiments.apga.APGAFunction;

/**
 * Fitness function for our example. See evaluate(...) method for details.
 *
 * @author Neil Rotstan
 * @author Klaus Meffert
 * @since 2.0
 */
public class Penalized2MaxFunction 
    extends FitnessFunction implements  APGAFunction{
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
  
    return -1;
  }

@Override
public Double[] excute(Matrix data, int nIterateCount) {
	// TODO Auto-generated method stub
	int time_delay = 0;
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
		double total1 = 0;
		for(int j = 0; j<=n-1; j++){
			double mydata = data.data[i][j];    	
	    	if(j<n-1){
	    		double mydata1 = data.data[i][j+1];    	
	    		total+=((mydata-1)*(mydata-1)*(1+Math.sin(3*Math.PI*mydata1)*Math.sin(3*Math.PI*mydata1)));
	    	}
	    	total1+=u(mydata,5,100,4);
	      
	    }
	    double mydata0 = data.data[i][0];    	
	    double mydatam = data.data[i][n-1]; 
	    results[i] = 0.1*(Math.sin(3*Math.PI*mydata0)*Math.sin(3*Math.PI*mydata0)+total+(mydatam-1)*(mydatam-1))+total1;
		results[i] = 1999999999-results[i];
		if(results[i]<=0)results[i]= 1.0;
		results[i] = 1000*(results[i])/(1999999999);
	}
	counts += m;
	return results;
}

private double u(double x, int a, int k, int m){
	  double result = 0;
	  if (x>a){
		  result = k*Math.pow(x-1, m);
	  }else if (x<-1*a){
  	  result = k*Math.pow(-1*x-a, m);
	  }else{
  	  result= 0;
	  }
	  return result;
}
}
