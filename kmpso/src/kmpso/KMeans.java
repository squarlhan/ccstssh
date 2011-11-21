package kmpso;

import java.util.ArrayList;
import java.util.List;

import org.encog.ml.MLCluster;
import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.ml.data.basic.BasicMLDataPair;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.ml.kmeans.KMeansClustering;
import org.encog.ml.kmeans.KMeansCluster;
import org.encog.ml.kmeans.Centroid;

import pso.Particle;
import pso.Swarm;

public class KMeans {

	public static List<Integer> runKmeans(Swarm runningobj, double cluster_number, int max_gen) {
		Particle[] particles = runningobj.getParticles();
		int currentSize = particles.length;
		int currentPSize = particles[0].getDimention(); 
		// -----------------------------------
		double[][] KmeansDataSet = new double[currentSize][currentPSize];
		for (int i = 0; i < currentSize; i++) {
			for (int j = 0; j < currentPSize; j++) {
				KmeansDataSet[i][j] = particles[i].getPosition()[j];
			}
		}

		final BasicMLDataSet set = new BasicMLDataSet();
		for (final double[] element : KmeansDataSet) {
			set.add(new BasicMLData(element));
		}

		int num =(int)(currentSize/cluster_number);
		final KMeansClustering kmeans = new KMeansClustering(num, set);
		kmeans.iteration(max_gen);
//		System.out.println("Final WCSS: " + kmeans.getWCSS());

		int[] centroidchrom = new int[kmeans.numClusters()];
		int[] chromorderflag = new int[kmeans.numClusters()];
		List<Integer> chromorder = new ArrayList();
		int[] chromflag = new int[currentSize];
		int[] clusterflag = new int[currentSize];
		 int[] cacflag=new int[currentSize];

		for (int k = 0; k < kmeans.getClusters().length; k++) {
			MLCluster cluster = (kmeans.getClusters())[k];
			KMeansCluster kmeanscluster = (KMeansCluster) cluster;
			Centroid centroid = kmeanscluster.getCentroid();
			int minorder = 0;
			double a = 0, MIN = Double.MAX_VALUE;
			for (int i = 0; i < cluster.size(); i++) {
				if ((a = KMeansClustering.calculateEuclideanDistance(centroid,
						cluster.get(i))) < MIN) {
					minorder = i;
					MIN = a;
					// System.out.print(MIN+"  ");
				}
			}
			centroidchrom[k] = minorder;
			/*
			 * System.out.println("  ");
			 * System.out.print(centroidchrom[k]+"  ");
			 * System.out.println("  "); System.out.println("  ");
			 */

		}

		for (int j = 0; j < currentSize; j++) {
			for (int k = 0; k < kmeans.getClusters().length; k++) {
				MLCluster cluster = (kmeans.getClusters())[k];
				KMeansCluster kmeanscluster = (KMeansCluster) cluster;
				Centroid centroid = kmeanscluster.getCentroid();
				for (int i = 0; i < cluster.size(); i++) {
					MLDataSet ds = cluster.createDataSet();
					MLDataPair pair = BasicMLDataPair.createPair(
							ds.getInputSize(), ds.getIdealSize());
					ds.getRecord(i, pair);
					double[] clusterdata = new double[pair.getInputArray().length];
					clusterdata = pair.getInputArray();
					boolean flag = true;
					for (int m = 0; m < clusterdata.length; m++) {
						if (clusterdata[m] != KmeansDataSet[j][m]) {
							flag = false;
							break;
						}
					}
					if(flag==true){
      				 
      				
      				
      				
      				  clusterflag[j]=k; 
      				  chromflag[j]=i;
      				 
      				  break;
      				 }
      			  }    	
				}
			}
		

		for (int i = 0; i < centroidchrom.length; i++) {
			for (int k = 0; k < clusterflag.length; k++) {
				if (i == clusterflag[k]) {
					if (centroidchrom[i] == chromflag[k]) {
						chromorderflag[i] = k;
					}
				}
			}
		}
		for (int i = 0; i < currentSize; i++) {
			chromorder.add(chromorderflag[clusterflag[i]]);
		}
		
		return chromorder;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
