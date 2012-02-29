package test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pso.Particle;
import pso.Swarm;
import svmpso.SVMPSO;

public class SvmpsoTest {

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
				String prefix = "";
				List<File> results = new ArrayList();
				
				File result0 = new File(prefix+"km_x.txt");
				File result1 = new File(prefix+"km_cos.txt");
				File result2 = new File(prefix+"km_ackley.txt");
				File result3 = new File(prefix+"km_quar.txt");
				File result4 = new File(prefix+"km_step.txt");
				File result5 = new File(prefix+"km_rosen.txt");
				File result6 = new File(prefix+"km_sch.txt");
				File result7 = new File(prefix+"km_gri.txt");
				File result8 = new File(prefix+"km_pen1.txt");
				File result9 = new File(prefix+"km_pen2.txt");
				File result10 = new File(prefix+"km_wei.txt");
				File result11 = new File(prefix+"km_non.txt");
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
		   double[] intertia = {0.95,0.9,0.95,0.9,0.95,1,0.95,0.9,0.95,0.95,0.9,0.95};
		   double[] velocity = {1,1.45,1.85,1.2,1.4,1.15,1.7,1.3,1.8,1.6,1.1,1.55};
		   SVMPSO svmpso = new SVMPSO();
		   double gamma =0.003;
			double c =300;
			int percent = 4;
		   for(int i = 0; i<=0;i++){
		   svmpso.Calculate(max_gen, numofparticals, dimention, intertia[0],velocity[0], scopes100, new MaxFunction(),gamma, c, percent, output[0]);
//		   svmpso.Calculate(max_gen, numofparticals, dimention, intertia[1],velocity[1], scopes512, new CosMaxFunction(), gamma, c, percent, output[1]);
//		   svmpso.Calculate(max_gen, numofparticals, dimention, intertia[2],velocity[2], scopes32, new AckleyMaxFunction(),gamma, c, percent, output[2]);
//		   svmpso.Calculate(max_gen, numofparticals, dimention, intertia[3],velocity[3], scopes100, new QuardircMaxFunction(), gamma, c, percent, output[3]);
//		   svmpso.Calculate(max_gen, numofparticals, dimention, intertia[4],velocity[4], scopes100, new StepMaxFunction(),gamma, c, percent, output[4]);
//		   svmpso.Calculate(max_gen, numofparticals, dimention, intertia[5],velocity[5], scopes30, new RosenbrockMaxFunction(),gamma, c, percent, output[5]);
//		   svmpso.Calculate(max_gen, numofparticals, dimention, intertia[6],velocity[6], scopes500, new SchwefelMaxFunction(),gamma, c, percent, output[6]);		   
//		   svmpso.Calculate(max_gen, numofparticals, dimention, intertia[7],velocity[7], scopes600, new GriewankMaxFunction(),gamma, c, percent, output[7]);
//		   svmpso.Calculate(max_gen, numofparticals, dimention, intertia[8],velocity[8], scopes50, new PenalizedMaxFunction(),gamma, c, percent, output[8]);	   
//		   svmpso.Calculate(max_gen, numofparticals, dimention, intertia[9],velocity[9], scopes50, new Penalized2MaxFunction(),gamma, c, percent, output[9]);		   		   		   		   
//		   svmpso.Calculate(max_gen, numofparticals, dimention, intertia[10],velocity[10], scopes512, new WeiMaxFunction(),gamma, c, percent, output[10]);
//		   svmpso.Calculate(max_gen, numofparticals, dimention, intertia[11],velocity[11], scopes5, new NonMaxFunction(),gamma, c, percent, output[11]);		
		   for (BufferedWriter op : output) {
			   op.write("\n");
			   op.flush();
			   }
		   }
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
