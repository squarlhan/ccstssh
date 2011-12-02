package test;

import java.util.List;



import pso.FitnessFunction;


public class QuardircMaxFunction 
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
    double data = position[i];
	double totala = 0;
	for(int a = 0; a<=i-1; a++){
	    totala+=Math.pow(data, 2.0);
	}
	total+=totala;

}
counts ++;
double result = (1+n)*(n/2)*Math.pow(100, 2.0)-total;
result = 1000*(result)/((1+n)*(n/2)*Math.pow(100, 2.0));
return result; 
}
}