package test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pso.Particle;
import pso.Swarm;

public class PureTest {

	// -------------------------------------------------------------------------
	// Main
	// -------------------------------------------------------------------------
	public static void main(String[] args) {
		
		  
		
		   int max_gen = 200;
		   int numofparticals = 40;
		   int dimention = 30;
		   
		   
		   List<List<Double>> scopes5 = new ArrayList();
	        List<List<Double>> scopes30 = new ArrayList();
	        List<List<Double>> scopes32 = new ArrayList();
	        List<List<Double>> scopes50 = new ArrayList();
	        List<List<Double>> scopes100 = new ArrayList();
	        List<List<Double>> scopes500 = new ArrayList();
	        List<List<Double>> scopes512 = new ArrayList();
	        List<List<Double>> scopes600 = new ArrayList();
			for(int i = 0; i<=dimention-1;i++ ){
				List<Double> sp5 = new ArrayList();
				List<Double> sp30 = new ArrayList();
			    List<Double> sp32 = new ArrayList();
			    List<Double> sp50 = new ArrayList();
			    List<Double> sp100 = new ArrayList();
			    List<Double> sp500 = new ArrayList();
			    List<Double> sp512 = new ArrayList();
			    List<Double> sp600 = new ArrayList();
				
			    sp5.add(-0.5);
			    sp5.add(0.5);
			    scopes5.add(sp5);
			    sp30.add(-30.0);
			    sp30.add(30.0);
			    scopes30.add(sp30);
			    sp32.add(-32.0);
			    sp32.add(32.0);
			    scopes32.add(sp32);
			    sp50.add(-50.0);
			    sp50.add(50.0);
			    scopes50.add(sp50);
			    sp100.add(-100.0);
			    sp100.add(100.0);
			    scopes100.add(sp100);
			    sp500.add(-500.0);
			    sp500.add(500.0);
			    scopes500.add(sp500);
			    sp512.add(-5.12);
			    sp512.add(5.12);
			    scopes512.add(sp512);
			    sp600.add(-600.0);
			    sp600.add(600.0);
			    scopes600.add(sp600);
			}
			try {
				String prefix = " ";
				List<File> results = new ArrayList();
				
				File result0 = new File(prefix+"x.txt");
				File result1 = new File(prefix+"cos.txt");
				File result2 = new File(prefix+"ackley.txt");
				File result3 = new File(prefix+"quar.txt");
				File result4 = new File(prefix+"step.txt");
				File result5 = new File(prefix+"rosen.txt");
				File result6 = new File(prefix+"sch.txt");
				File result7 = new File(prefix+"gri.txt");
				File result8 = new File(prefix+"pen1.txt");
				File result9 = new File(prefix+"pen2.txt");
				File result10 = new File(prefix+"wei.txt");
				File result11 = new File(prefix+"non.txt");
				results.add(result0);
				results.add(result1);
				results.add(result2);
				results.add(result3);
				results.add(result4);
				results.add(result5);
				results.add(result6);
				results.add(result7);
				results.add(result8);
				results.add(result9);
				results.add(result10);
				results.add(result11);
				
				BufferedWriter[] output = new BufferedWriter[results.size()];
				
				for(int i = 0; i<= results.size()-1;i++){
					if (results.get(i).exists()) {
						results.get(i).delete();
						if (results.get(i).createNewFile()) {
							System.out.println("result"+i+" file create success!");
						} else {
							System.out.println("result"+i+" file create failed!");
						}
					} else {
						if (results.get(i).createNewFile()) {
							System.out.println("result"+i+" file create success!");
						} else {
							System.out.println("result"+i+" file create failed!");
						}

					}
					output[i] = new BufferedWriter(new FileWriter(results.get(i)));
				}
		   double intertia = 0.9; 
		   double velocity = 0.9;
		   PurePso purepso = new PurePso();
			for(double v = 0.5; v <= 2.05; v += 0.05) {
				for (double i = 0.5; i <= 1.05; i += 0.05){
					purepso.Calculate(max_gen, numofparticals, dimention,i, v,scopes100, new MaxFunction(), output[0]);
					purepso.Calculate(max_gen, numofparticals, dimention,i,v, scopes512, new CosMaxFunction(), output[1]);
					purepso.Calculate(max_gen, numofparticals, dimention,i,v, scopes32,	new AckleyMaxFunction(), output[2]);
					purepso.Calculate(max_gen, numofparticals, dimention,i,v, scopes100,new QuardircMaxFunction(), output[3]);
					purepso.Calculate(max_gen, numofparticals, dimention,i,v, scopes100,new StepMaxFunction(), output[4]);
					purepso.Calculate(max_gen, numofparticals, dimention,i,v, scopes30,	new RosenbrockMaxFunction(), output[5]);
					purepso.Calculate(max_gen, numofparticals, dimention,i,v, scopes500,new SchwefelMaxFunction(), output[6]);				
					purepso.Calculate(max_gen, numofparticals, dimention,i,v, scopes600,new GriewankMaxFunction(), output[7]);				
					purepso.Calculate(max_gen, numofparticals, dimention,i,v, scopes50,	new PenalizedMaxFunction(), output[8]);
					purepso.Calculate(max_gen, numofparticals, dimention,i,v, scopes50,	new Penalized2MaxFunction(), output[9]);
					purepso.Calculate(max_gen, numofparticals, dimention,i,v, scopes512, new WeiMaxFunction(), output[10]);
					purepso.Calculate(max_gen, numofparticals, dimention,i,v, scopes5, new NonMaxFunction(), output[11]);
					 for (BufferedWriter op : output) {
						   op.write("\n");
						   op.flush();
						   }
				}
			}
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
