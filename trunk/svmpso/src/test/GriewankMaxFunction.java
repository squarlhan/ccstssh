package test;

import java.util.List;

import pso.FitnessFunction;

public class GriewankMaxFunction 
extends FitnessFunction{
	  public static int counts = 0;
	  public double evaluate(double position[]) {
		  double total = 0;
		    double prod = 0;
		    int time_delay = 0;
		    try {
				Thread.sleep(time_delay);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	int n = position.length;
	  for (int i = 0; i < n; i++) {
	    	
	    	total+=(Math.pow(position[i], 2));
			prod*=(Math.cos(position[i]/Math.sqrt(i+1)));
	      
	    }
	    counts ++;
	    double result = (2701-1-total/4000+prod)<=0?1:2701-1-total/4000+prod;
	    result = 1000*(result)/(2701);
	    return result;
	  }
}
