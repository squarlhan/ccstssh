package test;

import java.util.ArrayList;
import java.util.List;

import kmpso.KMPSO;
import pso.Particle;
import pso.Swarm;

public class Example {

	// -------------------------------------------------------------------------
	// Main
	// -------------------------------------------------------------------------
	public static void main(String[] args) {
		
		  
		
		   int max_gen = 200;
		   int numofparticals = 40;
		   int dimention = 30;
		   double intertia = 0.9; 
		   double velocity = 0.6;
		   double p_lamda = 0.8;
		   double p_extra = 0.02;
		   int kmeans_max = 100;
		   int kmeans_num = 3;
		   double lamda = 0.8;
		   
		   List<List<Double>> scopes = new ArrayList();
		   for(int i=0;i<=dimention-1;i++){
			   List<Double> scope = new ArrayList();
			   scope.add(-5.12);
			   scope.add(5.12);
			   scopes.add(scope);
		   }
		
		   KMPSO kmpso = new KMPSO();
		   kmpso.Calculate(max_gen, numofparticals, dimention, intertia,velocity, scopes, new CosMaxFunction(), p_lamda, p_extra, kmeans_max, kmeans_num, lamda);
			
		}
}
