package apso;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import pso.FitnessFunction;
import pso.Particle;
import pso.Swarm;


public class clustObjectFun {
	
	private static List<Integer> results;
	private static List<Double> objects;
	public static int mycount = 0;
    
    /**
     * 维护�?��历史�?��的个体集�?
     * @param chroms 新产生的个体
     * @param obj 算法对象
     * @return
     */
    private static List<Particle> maintain_history_best(List<Particle> chroms, APSO obj, Swarm runningobj){
    		
    	List<Particle> bestPop = obj.getBestPop();
    	int maxsize = runningobj.size();
    	List<Particle> all  = new ArrayList();
		if (bestPop != null && bestPop.size() > 0) {
			for (Particle p : bestPop) {
				all.add(p);
			}
		}
       	
    	if(chroms!=null&&chroms.size()>0){
    		for(Particle chrom:chroms){
    			all.add(chrom);
    		}
    	}else{
    		return bestPop;
    	}
    	
    	
    	
    	int nowsize = all.size();
    	if(nowsize<=maxsize){
    		if (bestPop != null && bestPop.size() > 0) {
    			bestPop.clear();  		    
    		}
    		bestPop =  all;
    	}else{
    		Collections.sort(chroms, new Comparator<Particle>() {
				public int compare(Particle particle1, Particle particle2) {
					return ((Double) particle2.getFitness())
							.compareTo(particle1.getFitness());
				}
			});
    		if (bestPop != null && bestPop.size() > 0) {
    			bestPop.clear();  		    
    		}
    		for(int i=0;i<=maxsize-1;i++){
    			bestPop.add(all.get(i));
    		}
    	}
    	obj.setBestPop(bestPop);
    	return bestPop;
    }
    
    /**
     * 维护�?���?���?��的个体集�?
     * @param chroms 新产生的个体
     * @param obj 算法对象
     * @return
     */
    private static List<Particle> maintain_local_best(List<Particle> chroms, APSO obj, Swarm runningobj){
    		
    	//维护�?���?���?��群体
    	List<Particle> localPop = obj.getLocalPop();
       	int maxsize = runningobj.size();
       	List<Particle> all  = new ArrayList();
       	if (localPop != null && localPop.size() > 0) {
			for (Particle p : localPop) {
				all.add(p);
			}
		}
       	//新的染色体排�?
		if (chroms != null && chroms.size() > 0) {

			Collections.sort(chroms, new Comparator<Particle>() {
				public int compare(Particle particle1, Particle particle2) {
					return ((Double) particle2.getFitness())
							.compareTo(particle1.getFitness());
				}
			});

		}else{
			return localPop;
		}
       	int newsize = chroms.size();
       	int nowsize = all.size();
        int extra = newsize<=10?newsize:10;
       	
       	if(nowsize+extra<=maxsize){
       		for(int i=0;i<=extra-1;i++){
       			all.add(chroms.get(i));
       		}
       		if (localPop != null && localPop.size() > 0) {
       			localPop.clear();  		    
    		}
       		localPop = all;
       	}else{
       		if (localPop != null && localPop.size() > 0) {
       			localPop.clear();  		    
    		}
       	   
       	    	for(int i=0;i<=extra-1;i++){
           			all.add(chroms.get(i));
           		}
       	    	for(int i=nowsize+extra-maxsize;i<=nowsize+extra-1;i++){
       	    		localPop.add( all.get(i));
           		}
       	    
       	}
       	obj.setLocalPop(localPop);
    	return localPop;
    }
    
    /**
     * 维护�?���?���?��的个体集�?
     * @param chroms 新产生的个体
     * @param obj 算法对象
     * @return
     */
    private static List<Particle> maintain_local_worst(List<Particle> chroms, APSO obj, Swarm runningobj){
    		
    	//维护�?���?���?��群体
    	List<Particle> localWorst = obj.getLocalWorst();
       	int maxsize = runningobj.size();
       	List<Particle> all  = new ArrayList();
       	if (localWorst != null && localWorst.size() > 0) {
			for (Particle p : localWorst) {
				all.add(p);
			}
		}
       	//新的染色体排�?
		if (chroms != null && chroms.size() > 0) {

			Collections.sort(chroms, new Comparator<Particle>() {
				public int compare(Particle particle1, Particle particle2) {
					return ((Double) particle1.getFitness())
							.compareTo(particle2.getFitness());
				}
			});

		}else{
			return localWorst;
		}
       	int newsize = chroms.size();
       	int nowsize = all.size();
        int extra = newsize<=10?newsize:10;
       	
       	if(nowsize+extra<=maxsize){
       		for(int i=0;i<=extra-1;i++){
       			all.add(chroms.get(i));
       		}
       		if (localWorst != null && localWorst.size() > 0) {
       			localWorst.clear();  		    
    		}
       		localWorst = all;
       	}else{
       		if (localWorst != null && localWorst.size() > 0) {
       			localWorst.clear();  		    
    		}
       	   
       	    	for(int i=0;i<=extra-1;i++){
           			all.add(chroms.get(i));
           		}
       	    	for(int i=nowsize+extra-maxsize;i<=nowsize+extra-1;i++){
       	    		localWorst.add( all.get(i));
           		}
       	    
       	}
       	obj.setLocalWorst(localWorst);
    	return localWorst;
    }
    
    
    private static List<Integer> getPatternfromPop(List<Particle> pop, double cutoff, APSO obj, Swarm runnongobj){
    	List<Integer> result = new ArrayList();
    	//首先把pop转成二进�?
    	List<List<Integer>> popbin = new ArrayList();
    	int n = 0;
    	for(int i = 0; i<=pop.size()-1; i++){
    		List<Integer> chrombin = new ArrayList();
    		for(int j = 0; j<=pop.get(i).getDimention()-1;j++){
    			chrombin.addAll(doube2binary(runnongobj.getMinPosition()[j],
    					runnongobj.getMaxPosition()[j],
    					10,
    				    pop.get(i).getPosition()[j]));
    		}
    		n = chrombin.size();
    		popbin.add(chrombin);
    	}
    	int m = popbin.size();
    	for(int j=0;j<=n-1;j++){
    		double tempsum = 0;
    		for(int i=0;i<=m-1;i++){
    			tempsum+=popbin.get(i).get(j);
    		}
    		if(tempsum/m>cutoff){
    			result.add(1);
    		}else if(tempsum/m<1-cutoff){
    			result.add(0);
    		}else{
    			result.add(2);
    		}
    	}
    	String patternstr = "";
       	for(int a:result){
       		if(a!=2){
       			patternstr+=a;
       		}else{
       			patternstr+="*";
       		}
       	}
       	obj.getPatterns().add(patternstr);	
    	return result;
    }
    
    
    public static  Double calconeFittnessValue(Particle particle, APSO obj,  FitnessFunction fitness){
    	obj.setnIterateCount(obj.getnIterateCount()+1);
    	return fitness.evaluate(particle);
    }
    
    public static  List<Double> calcFittnessValueDrictely(Swarm runningobj, APSO obj, double cutoff){
    
    	FitnessFunction fitness = runningobj.getFitnessFunction();
    	List<Double> objects = new ArrayList();
    	List<Integer> nullfitset = new  ArrayList();
    	List<Particle> null_particles = new ArrayList();
    	for(int i = 0; i<=runningobj.size()-1; i++){
    		if(runningobj.getParticle(i).getFitness()!=Double.NaN){
    			nullfitset.add(i);
    			null_particles.add(runningobj.getParticle(i));
    			runningobj.getParticle(i).setFitness(fitness.evaluate(runningobj.getParticle(i)), true);
    		}
		}
		for(int i = 0; i<=null_particles.size()-1; i++){
			int j = nullfitset.get(i);
			runningobj.getParticle(j).setIscenter(true);
			null_particles.get(i).setIscenter(true);
		}
		List<Particle> bestPop = maintain_history_best(null_particles, obj, runningobj);
		List<Particle> localPop = maintain_local_best(null_particles, obj, runningobj);
		List<Particle> localWorst = maintain_local_worst(null_particles, obj, runningobj);
        obj.setnIterateCount(obj.getnIterateCount()+null_particles.size());
    	return objects;
    }
    
    public static  List<Double> calcFittnessValue(Swarm runningobj, APSO obj,  List<Integer> results, double[][] datamatrix, double lamda, double cutoff, double extra,BufferedWriter output){
    	
    	FitnessFunction fitness = runningobj.getFitnessFunction();
		Particle[] chrs = runningobj.getParticles();
		for(int i = 0;i<runningobj.size();i++){
			chrs[i].setIscenter(false);
    	}
		List<Double> objects = new ArrayList();
		List<Integer> clac_centers = new ArrayList();
		Set<Integer> centers = new HashSet();
		centers.addAll(results);
		Map<Integer, Double> centerObjects = new HashMap();
		Iterator iter = centers.iterator();
		List<Particle> center_chroms = new ArrayList();
		while (iter.hasNext()) {
			int a = (Integer) iter.next();
			center_chroms.add(chrs[a]);
		    clac_centers.add(a);
			if(!chrs[a].isIscenter()){
				chrs[a].setFitness(fitness.evaluate(chrs[a]), true);
			    }
		}
		if(center_chroms.size()>0){
			obj.setnIterateCount(obj.getnIterateCount() + center_chroms.size());
			for (int i = 0; i <= clac_centers.size() - 1; i++) {
				centerObjects.put(clac_centers.get(i),  chrs[clac_centers.get(i)].getFitness());
				chrs[clac_centers.get(i)].setIscenter(true);
				center_chroms.get(i).setIscenter(true);
			}
		}
		
		List<Particle> bestPop = maintain_history_best(center_chroms, obj, runningobj);
		List<Particle> localPop = maintain_local_best(center_chroms, obj, runningobj);
		List<Particle> localWorst = maintain_local_worst(center_chroms, obj, runningobj);
		
		List<Integer> bestpattern = getPatternfromPop(localPop, cutoff, obj, runningobj);
		List<Integer> worstpattern = getPatternfromPop(localWorst, cutoff, obj, runningobj);

		double dis_max = datamatrix[0][0];
		double dis_min = datamatrix[0][0];
		for(int i=0; i <= datamatrix.length-1; i++){
			for(int j=0; j <= datamatrix.length-1; j++){
				if(datamatrix[i][j]>dis_max)dis_max = datamatrix[i][j];
				if(datamatrix[i][j]<dis_min)dis_min = datamatrix[i][j];
			}
		}
		for(int i=0; i <= datamatrix.length-1; i++){
			for(int j=0; j <= datamatrix.length-1; j++){
				datamatrix[i][j] = 1-(datamatrix[i][j]-dis_min)/(dis_max-dis_min);
			}
		}
		double[] realone = new double[datamatrix.length];
		double[] evalone = new double[datamatrix.length];
		double[] diff = new double[datamatrix.length];
		double diffsum = 0;
		for(int i=0; i <= datamatrix.length-1; i++){
			double tempfit  = 0.0;
			if(i==results.get(i)){
				tempfit = centerObjects.get(i).doubleValue();
			}else{
				
				double s = 1/(1+datamatrix[i][results.get(i)]);
				tempfit = ((1-lamda)*s+lamda)*centerObjects.get(results.get(i));

				if(localPop.size()>=runningobj.size()){

					int b = match(bestpattern, chrs[i], runningobj);
					int w = match(worstpattern, chrs[i], runningobj);
					if(b>=w){
						double temp = 0;
						for(int a = 0; a<= localPop.size()-1;a++){
							temp = temp + ((double)match(bestpattern, localPop.get(a), runningobj)
									/localPop.get(a).getDimention())
									*localPop.get(a).getFitness();
						}
						tempfit = tempfit + extra*Math.exp(-1*obj.getProgress())*
								(1-((double)b/bestpattern.size()))*temp/localPop.size();
					}else{
						double temp = 0;
						for(int a = 0; a<= localWorst.size()-1;a++){
							temp = temp + ((double)match(bestpattern, localWorst.get(a), runningobj)
									/localWorst.get(a).getDimention())
									*localWorst.get(a).getFitness();
						}
						tempfit = tempfit - extra*Math.exp(-1*obj.getProgress())*
								(1-((double)b/bestpattern.size()))*temp/localWorst.size();
					}
					mycount++;
				}
			}
		
//			realone[i] = calconeFittnessValue(chrs[i], obj,  fitness);
//			evalone[i] = tempfit;
//			diff[i] = Math.abs(realone[i]-evalone[i]);
//			diffsum += (diff[i]/realone[i]);
			
			objects.add(tempfit);
			if(chrs[i].getFitness()<0||!chrs[i].isIscenter()){
				chrs[i].setFitness(tempfit, true);
				chrs[i].setIscenter(false);
			}
		}

//     	String fitstr = diffsum/datamatrix.length + "\t" ;
//		obj.getFitnessvalues().add(fitstr);

//		try {
//			for (double dd : diff) {
//			    output.write(dd + "\t");
//		}
//		output.write("\n");
//		output.flush();
//	} catch (IOException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
		return objects;
	}
	
    
    
    private static int match(List<Integer> pattern,  Particle particle, Swarm runningobj){
    	int result = 0;
    	int n = particle.getDimention();
    	List<Integer> particlebin = new ArrayList();
		for(int j = 0; j<=n-1;j++){
			particlebin.addAll(doube2binary(runningobj.getMinPosition()[j],
					runningobj.getMaxPosition()[j],
					10,
					particle.getPosition()[j]));
		}
		int plen = pattern.size();
		for(int i = 0; i<= plen-1; i++){
			if(pattern.get(i) == particlebin.get(i))result ++;
		}
    	return result;
    }
    
  //二进制分三部分，第一位是符号位，接下来几位表示整数部分，�?���?��是小数部�?
  	private static List<Integer> doube2binary2(double min, double max, int p, double num){
  		List<Integer> result = new ArrayList();
  		//首先确定符号�?表示+�?表示-
  		int sign = num<0?0:1;
  		result.add(sign);
  		//确定整数部分
  		int num_int = (int) Math.abs(num);
  		int int_lenth = (int) Math.ceil(Math.log10(Math.max(Math.abs(min), Math.abs(max)))/Math.log10(2));
  		String int_str = Integer.toBinaryString(num_int);
  		while(int_str.length()<int_lenth){
  			int_str = "0"+int_str;
  		}
  		for(int i = 0;i<=int_lenth-1;i++){
  			result.add(Integer.parseInt(int_str.substring(i, i+1)));
  		}
  		//确定小数部分，这个和精度有关系， 比如小数点后�?位，精度�?0�?4位精度为14位， 5位为17, 6位为20�?
  		double pp = Math.pow(2, p);
  		int pp_curser = 10;
  		while(pp/pp_curser>10){
  			pp_curser*=10;
  		}
  		//根据精度截取小数部分
  		double num_float = num-(int)num;
  		int num_float_int = (int) Math.abs(num_float*pp_curser);
  		int float_lenth = (int) Math.ceil(Math.log10(pp_curser)/Math.log10(2));
  		String float_str = Integer.toBinaryString(num_float_int);
  		while(float_str.length()<float_lenth){
  			float_str = "0"+float_str;
  		}
  		for(int i = 0;i<=float_lenth-1;i++){
  			result.add(Integer.parseInt(float_str.substring(i, i+1)));
  		}
  		return result;
  	}
    private static List<Integer> doube2binary(double min, double max, int p, double num){
		List<Integer> result = new ArrayList();
		int rank = (int) ((Math.pow(2, p)-1)*(num-min)/(max-min));
		String temp = Integer.toBinaryString(rank);
		if(temp.length()>0){
			for(int i = 0;i<=temp.length()-1;i++){
				result.add(Integer.parseInt(temp.substring(i, i+1)));
			}
			while(result.size()<p){
				result.add(0,0);
			}
		}else{
			return null;
		}
		return result;
	}
    
    private double[][]  pop2matrix(Swarm runningobj){
    	Particle[] particles = runningobj.getParticles();
  	  if(particles==null){
  		  return null;
  	  }
  	  int m = runningobj.getNumberOfParticles();
  	  int n = particles[0].getDimention();
  	  double[][]  result = new double[m][n];
  	  for(int i = 0; i<=m-1;i++){
  		  for(int j = 0; j<=n-1;j++){
  			result[i][j] = particles[i].getPosition()[j];
  		  }
  	  }
  	  return result;
    }
}
