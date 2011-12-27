package experiments;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgap.FitnessFunction;
import org.jgap.IChromosome;
import org.jgap.impl.DoubleGene;

import experiments.apga.APGA;
import experiments.apga.clustObjectFun;

public class TestFET {
	
	public static int falsecount = 0;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		long startTime = 0;
		long endTime = 0;
		APGA a1 = new APGA();
		int m = 40;
		int n = 24;
		int sumcount = 0;
		List<List<Double>> scopes5 = new ArrayList();
		for (int i = 0; i <= n - 1; i++) {
			List<Double> sp5 = new ArrayList();

			sp5.add(1.0);
			sp5.add(2.5);
			scopes5.add(sp5);
		}
		try {
			String prefix = "10_";

			File result = new File(prefix + "ap_fet.txt");
			if (result.exists()) {
				result.delete();
				if (result.createNewFile()) {
					System.out.println("result  file create success!");
				} else {
					System.out.println("result file create failed!");
				}
			} else {
				if (result.createNewFile()) {
					System.out.println("result file create success!");
				} else {
					System.out.println("result file create failed!");
				}
			}
			File popout = new File("10_apcounts.txt");
			if (popout.exists()) {
				popout.delete();
				if (popout.createNewFile()) {
					System.out.println("popout  file create success!");
				} else {
					System.out.println("popout file create failed!");
				}
			} else {
				if (popout.createNewFile()) {
					System.out.println("popout file create success!");
				} else {
					System.out.println("popout file create failed!");
				}
			}
			BufferedWriter output = new BufferedWriter(new FileWriter(result));
			BufferedWriter popoutput = new BufferedWriter(new FileWriter(popout));
			double p_lamda = 0.8;
			double p_lamda0 = 0.0;
			double lamda = 0.8;
			double lamda2 = 0.8;
			double p_extra0 = 0.00;
			double p_extra = 0.01;
			int ap_max = 100;
			double ap_lamda = 0.8;
			DecimalFormat myformat = new DecimalFormat("#0.00");
			// while(lamda2<=1.05){
			for (int bb = 0; bb <= 9; bb++) {
				startTime = System.currentTimeMillis();
				// a1.Calculate(200, m, n, scopes5, new FetMaxFunction(),
				// p_lamda0, p_extra0, ap_max, ap_lamda, lamda2, output);

				IChromosome re = a1.Calculate(100, m, n, scopes5, new FetMaxFunction(),
						p_lamda, p_extra, ap_max, ap_lamda, lamda, output);
				endTime = System.currentTimeMillis();
				output.write("\n");
				output.flush();
				popoutput.write(String.valueOf(endTime - startTime) + "ms \t");
				popoutput.write("True: "+String.valueOf(FetMaxFunction.counts) + "\t");
				popoutput.write("False: "+String.valueOf(falsecount) + "\t");
				popoutput.write("Fitness: "+String.valueOf(re.getFitnessValueDirectly()) + "\t");
				for (int i = 0; i < re.size(); i++) {
					output.write(myformat.format(re.getGene(i).getAllele()) + "\t");
				}
				popoutput.write("\n");
				sumcount+=FetMaxFunction.counts;
				FetMaxFunction.counts = 0;
				falsecount = 0;
				popoutput.flush();
			}
			// for (BufferedWriter op : output) {
			// op.write("\n");
			// op.flush();
			// }
			// lamda2+=0.05;
			// }
			output.close();
			popoutput.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("运行时间 " + (endTime - startTime) + "ms");
		System.out.println("fet计算次数：" + sumcount);

	}

}
