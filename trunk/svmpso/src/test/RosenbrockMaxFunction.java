package test;

import java.util.List;

import pso.FitnessFunction;

public class RosenbrockMaxFunction 
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
for (int i = 0; i < n-1; i++) {
	total+=(100*Math.pow((position[i+1]
			-Math.pow(position[i], 2)), 2)
			+Math.pow((position[i]-1), 2));
  
}
counts ++;
double result = (1999999999-total)<=0?1:1999999999-total;
result = 1000*(result)/1999999999;
return result;
//return n*Math.pow(10, 2.0)-total; 
}
}
