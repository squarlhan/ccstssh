package test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import apso.APSO;

import pso.Particle;
import pso.Swarm;

public class ApsoTest {

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
				
				File result0 = new File(prefix+"ap_x.txt");
				File result1 = new File(prefix+"ap_cos.txt");
				File result2 = new File(prefix+"ap_ackley.txt");
				File result3 = new File(prefix+"ap_quar.txt");
				File result4 = new File(prefix+"ap_step.txt");
				File result5 = new File(prefix+"ap_rosen.txt");
				File result6 = new File(prefix+"ap_sch.txt");
				File result7 = new File(prefix+"ap_gri.txt");
				File result8 = new File(prefix+"ap_pen1.txt");
				File result9 = new File(prefix+"ap_pen2.txt");
				File result10 = new File(prefix+"ap_wei.txt");
				File result11 = new File(prefix+"ap_non.txt");
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
			double p_lamda = 0.8;
			double p_extra = 0.002;
			int ap_max = 100;
			double ap_lamda = 0.8;
			double lamda = 0.8;
			APSO apso = new APSO();
		   apso.Calculate(max_gen, numofparticals, dimention, intertia,velocity, scopes100, new MaxFunction(), p_lamda, p_extra, ap_max, ap_lamda, lamda, output[0]);
		   apso.Calculate(max_gen, numofparticals, dimention, intertia,velocity, scopes512, new CosMaxFunction(), p_lamda, p_extra, ap_max, ap_lamda, lamda, output[1]);
		   apso.Calculate(max_gen, numofparticals, dimention, intertia,velocity, scopes32, new AckleyMaxFunction(), p_lamda, p_extra, ap_max, ap_lamda, lamda, output[2]);
		   apso.Calculate(max_gen, numofparticals, dimention, intertia,velocity, scopes100, new QuardircMaxFunction(), p_lamda, p_extra, ap_max, ap_lamda, lamda, output[3]);
		   apso.Calculate(max_gen, numofparticals, dimention, intertia,velocity, scopes100, new StepMaxFunction(), p_lamda, p_extra, ap_max, ap_lamda, lamda, output[4]);
		   apso.Calculate(max_gen, numofparticals, dimention, intertia,velocity, scopes30, new RosenbrockMaxFunction(), p_lamda, p_extra, ap_max, ap_lamda, lamda, output[5]);
		   apso.Calculate(max_gen, numofparticals, dimention, intertia,velocity, scopes500, new SchwefelMaxFunction(),p_lamda, p_extra, ap_max, ap_lamda, lamda, output[6]);
		   apso.Calculate(max_gen, numofparticals, dimention, intertia,velocity, scopes600, new GriewankMaxFunction(), p_lamda, p_extra, ap_max, ap_lamda, lamda, output[7]);
		   apso.Calculate(max_gen, numofparticals, dimention, intertia,velocity, scopes50, new PenalizedMaxFunction(), p_lamda, p_extra, ap_max, ap_lamda, lamda, output[8]);
		   apso.Calculate(max_gen, numofparticals, dimention, intertia,velocity, scopes50, new Penalized2MaxFunction(), p_lamda, p_extra, ap_max, ap_lamda, lamda, output[9]); 		   		   
		   apso.Calculate(max_gen, numofparticals, dimention, intertia,velocity, scopes512, new WeiMaxFunction(), p_lamda, p_extra, ap_max, ap_lamda, lamda, output[10]);
		   apso.Calculate(max_gen, numofparticals, dimention, intertia,velocity, scopes5, new NonMaxFunction(), p_lamda, p_extra, ap_max, ap_lamda, lamda, output[11]);
		   for (BufferedWriter op : output) {
			   op.write("\n");
			   op.flush();
			   }
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
