package test;

import java.util.List;



import pso.FitnessFunction;
public class Penalized2MaxFunction 
extends FitnessFunction{
	public static int counts = 0;
	  public double evaluate(double position[]) {
	    double total = 0;
	    double total1 = 0;
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
	if(i<n-1){
		double data1 = position[i+1];
		total+=((data-1)*(data-1)*(1+Math.sin(3*Math.PI*data1)*Math.sin(3*Math.PI*data1)));
	}
	total1+=u(data,5,100,4);
  
}
double data0 = position[0]; 
double datam =position[n-1]; 
double result = 0;
result = 0.1*(Math.sin(3*Math.PI*data0)*Math.sin(3*Math.PI*data0)+total+(datam-1)*(datam-1))+total1;
counts ++;
double result1 = (1999999999-result)<=0?1:1999999999-result;
result1 = 1000*(result1)/1999999999;
return result1;
//return n*Math.pow(10, 2.0)-total; 
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