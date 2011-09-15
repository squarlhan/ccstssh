package experiments.ga;

import org.jgap.IChromosome;

public class TestTest {

	double[][] vector_decoder(IChromosome chrom){
		int csize = chrom.size();
		if(csize<6){
			System.err.println("wrong chromosome size input!");
			return null;
		}
		double[] input = new double[6];
		for(int i = 0;i<=2;i++){
			double genevalue = (Double) chrom.getGene(i).getAllele();
			input[i] = 30+genevalue*(150-30);
		}
		input[3] = (Double) chrom.getGene(3).getAllele();
		double max = 1;
		double min = input[3]/4;
		if(4*input[3]<1)max = 4*input[3];
		input[4] = (Double) chrom.getGene(4).getAllele();
		input[4] = min+input[4]*(max-min);
		double vmax = input[3];
		double vmin = input[4];
		if(input[4]>input[3]){
			vmax = input[4];
			vmin = input[3];
		}
		min = vmax/4;
		if(4*vmin<1)max = 4*vmin;
		input[5] = (Double) chrom.getGene(5).getAllele();
		input[5] = min+input[5]*(max-min);
		
		
		
		double[][] results = new double[3][3];
		results[0][0] = input[3];
		results[0][1] = 0;
		results[0][2] = 0;
		results[1][0] = input[4]*Math.cos(input[0]);
		results[1][1] = input[4]*Math.sin(input[0]);
		results[1][2] = 0;
		results[2][0] = input[5]*Math.cos(input[2]);
		results[2][2] = input[5]*Math.sqrt(Math.pow(Math.sin(input[1]), 2)
				-Math.pow((Math.cos(input[2])-Math.cos(input[0])*Math.cos(input[1]))/Math.sin(input[0]), 2));
		results[2][1] = Math.sqrt(Math.pow(input[5], 2)-Math.pow(results[2][0], 2)-Math.pow(results[2][2], 2));		
		
		return results;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
