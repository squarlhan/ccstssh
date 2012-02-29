package test;

import java.util.List;

import pso.FitnessFunction;

public class WeiMaxFunction 
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
        double mydata = position[i];
        double y = 0;
        if(Math.abs(mydata)<0.5){
        	y = mydata;
        }else{
        	y = Math.round(2*mydata)/2;
        }
    	total +=  (y*y-10*Math.cos(2*y*Math.PI)+10);
      
    }
    counts ++;
    double result = n*40.25-total;
    result = 1000*(result)/(n*40.25);
    return result;
  }

}

