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
public class ThreadAckleyMaxFunction extends FitnessFunction implements
		APGAFunction{
	private final static String CVS_REVISION = "$Revision: 1.6 $";

	public static int counts = 0;
	public double evaluate(IChromosome a_subject) {

		return -1;
	}

	@Override
	public Double[] excute(Matrix data, int nIterateCount) {
		// TODO Auto-generated method stub
		if (data == null)
			return null;
		int time_delay = 0;
		int m = data.getM();
		int n = data.getN();
		Double[] results = new Double[m];
		Thread[] ackleys = new Thread[m];
		for (int i = 0; i <= m - 1; i++) {
			ackleys[i] = new AckleyFunction(time_delay, data, i, n,results);
			ackleys[i].start();
		}
		for (int i = 0; i <= m - 1; i++) {
			try {
				ackleys[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		counts += m;
		return results;
	}

	class AckleyFunction extends Thread {
		
		private int time_delay;
		private Matrix data;
		private int i;
		private int n;
		private Double[] results;

	    public AckleyFunction(int time_delay, Matrix data, int i, int n,
				Double[] results) {
			super();
			this.time_delay = time_delay;
			this.data = data;
			this.i = i;
			this.n = n;
			this.results = results;
		}

		public void run() {
	    	try {
                sleep(time_delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

			double total = 0;
			double totalcos = 0;
			for (int j = 0; j <= n - 1; j++) {
				total += (data.data[i][j] * data.data[i][j]);
				totalcos += (Math.cos(2 * Math.PI * data.data[i][j]));
			}
			results[i] = 1000	* (20 * Math.exp(-0.2 * Math.sqrt(total / n)) + Math
							.exp(totalcos / n)) / (20 + Math.E);
		
	    }
		
	}
}
