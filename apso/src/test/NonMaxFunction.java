package test;

import java.util.List;


import pso.FitnessFunction;

public class NonMaxFunction 
extends FitnessFunction{
	public static int counts = 0;
	 public double evaluate(double position[]) {
		 double total = 0;
		    double total2 = 0;
		    double a = 0.5;
		    double b = 3;
		    int kmax = 20;
		    int time_delay = 0;
		    try {
				Thread.sleep(time_delay);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	int n = position.length;
	  for (int i = 0; i < n; i++) {
	    	double total1 = 0;
	        double mydata = position[i];
	        for(int k = 0; k<=kmax;k++){
	        	total1 += (Math.pow(a, k)*Math.cos(2*Math.PI*Math.pow(b, k)*(mydata+0.5)));
	        	total2 += (Math.pow(a, k)*Math.cos(2*Math.PI*Math.pow(b, k)*0.5));
	        }
	    	total +=  total1;
	      
	    }
	    counts ++;
	    double result = n*4-total+total2;
	    result = 1000*(result)/(4*n);
	    return result;
	 }
}
