package test;

import java.util.List;

import pso.FitnessFunction;

public class SchwefelMaxFunction 
extends FitnessFunction{
	 public static int counts = 0;
	  public double evaluate(double position[]) {
	    double total = 0;
	    int time_delay = 0;
	    try {
			Thread.sleep(time_delay);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  int n = position.length;
  for (int i = 0; i < n; i++) {
  	total+=-1*(position[i]*
  			Math.sin(Math.sqrt(Math.abs(position[i]))));
    
  }
  counts ++;
  double result = (838*n-418.9829*n-total)<=0?1:838*n-418.9829*n-total;
  result = 1000*(result)/(838*n);
  return result;
//  return n*Math.pow(10, 2.0)-total; 
}
}
