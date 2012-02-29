package test;

import java.util.List;

import pso.FitnessFunction;

public class StepMaxFunction 
extends  FitnessFunction{
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
       double data = position[i];
       total+=(Math.floor(data+0.5)*Math.floor(data+0.5));
   }
   counts ++;
   double result = n*100*100-total; 
   result = 1000*(result)/(n*100*100);
   return result;
 }

}
