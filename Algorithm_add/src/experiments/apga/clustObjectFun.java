package experiments.apga;

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

import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.Chromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.Population;

import experiments.Matrix;
import experiments.apga.APGAFunction;
import experiments.ga.MaxFunction;

import affinitymain.CommandLineParser;
import affinitymain.InteractionData;
import affinitymain.RunAlgorithm;
import algorithm.abs.AffinityPropagationAlgorithm.AffinityConnectingMethod;

public class clustObjectFun {
	
	private static List<Integer> results;
	private static List<Double> objects;
	public static int mycount = 0;
	
	 /**
     * @param args the command line arguments
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

//        if (args.length < 2) {
//            showHelp();
//            return;
//        }

        Map<String, String> map = CommandLineParser.parseTokens(args);
        double[][] datamatrix = EucDistance.getData("data/b.txt");
        Collection<InteractionData> inputs = EucDistance.getunEucMatrix(datamatrix);
        Double lambda = 0.9;
        Integer iterations = 500;
      
        Integer convits = getConvits(map);
        Double preferences = -0.1;
        
        String kind = getOutputKind(map);
        AffinityConnectingMethod connMode = getConnMode(map);
        boolean takeLog = getTakeLog(map);
        boolean refine = getRefine(map);
        Integer steps = getSteps(map);

        RunAlgorithm alg = new RunAlgorithm(inputs, lambda, iterations, convits, preferences, kind);
        alg.setTakeLog(takeLog);
        alg.setConnMode(connMode);
        alg.setSteps(steps);
        alg.setRefine(refine);

        alg.setParemeters();
        results = alg.run();
        //calcObjectValue(results, datamatrix, 0.9);
    }
  

    
    /**
     * 返回一个模式 通过计算历史最优的N个染色体各位上的标准差，取标准差最小的前size*cutoff个，作为模式
     * 维护一个前40fitness的种群
     * @param chroms 新产生的染色体
     * @param obj 算法对象，里面包含最好的种群
     * @param cutoff 有多少gene匹配上，认为这个染色体符合这个模式
     */
    private static Map<Integer, Double> maintainbestchrom(List<IChromosome> chroms, APGA obj, Double cutoff){
    	
    	Map<Integer, Double> newpattern = new HashMap();
    	
    	Population bestPop = maintain_history_best(chroms, obj);
    	int maxsize = bestPop.getConfiguration().getPopulationSize();
    	
    	//根据新的最佳群体得到一个模式
    	int m = bestPop.size();
    	int n = bestPop.getConfiguration().getChromosomeSize();
    	Matrix mydata = pop2matrix(bestPop);
    	Double[] avgset = new Double[n];//平均值
    	for(int j = 0; j<=n-1; j++){
    		Double tempsum = 0.0;
    		for(int i = 0; i<=m-1;i++){
    			tempsum+=mydata.data[i][j];
    		}
    		avgset[j] = tempsum/m;
    	}
    	
    	Map<Integer, Double> stddev = new HashMap();//标准差
    	for(int j = 0; j<=n-1; j++){
    		Double tempsum = 0.0;
    		for(int i = 0; i<=m-1;i++){
    			tempsum+=((mydata.data[i][j]-avgset[j])*(mydata.data[i][j]-avgset[j]));
    		}
    		stddev.put(j, Math.sqrt(tempsum/m));
    	}

		if (m >= maxsize / 2) {
			List<Map.Entry<Integer, Double>> mappingList = null;
			// 通过ArrayList构造函数把map.entrySet()转换成list
			mappingList = new ArrayList<Map.Entry<Integer, Double>>(
					stddev.entrySet());
			// 通过比较器实现比较排序
			Collections.sort(mappingList,
					new Comparator<Map.Entry<Integer, Double>>() {
						public int compare(Map.Entry<Integer, Double> mapping1,
								Map.Entry<Integer, Double> mapping2) {
							return mapping1.getValue().compareTo(
									mapping2.getValue());
						}
					});

			for (int i = 0; i <= mappingList.size() * cutoff - 1; i++) {
				newpattern.put(mappingList.get(i).getKey(), mappingList.get(i)
						.getValue());
			}
			
			String pattern = "";
			for(int i = 0; i<=n-1; i++){
				if(newpattern.get(i)==null){
					pattern+="*\t";
				}else{
					pattern = pattern + avgset[i]+ "\t";
				}
			}
			obj.getPatterns().add(pattern);
		}
		
		String pat_str = "";
		for(int i=0; i<= n-1; n++){
			if(newpattern.get(i)==null){
				pat_str+="*\t";
			}else{
				pat_str = pat_str + (newpattern.get(i).intValue())+"\t";
			}
		}
		obj.getPatterns().add(pat_str);
    	return newpattern;
    }
    
    /**
     * 维护一个历史最优的个体集合
     * @param chroms 新产生的个体
     * @param obj 算法对象
     * @return
     */
    private static Population maintain_history_best(List<IChromosome> chroms, APGA obj){
    		
    	Population bestPop = obj.getBestPop();
    	int maxsize = bestPop.getConfiguration().getPopulationSize();
    	Population all = (Population) bestPop.clone();
    	if(chroms!=null&&chroms.size()>0){
    		for(IChromosome chrom:chroms){
    			all.addChromosome(chrom);
    		}
    	}
    	
    	
    	
    	int nowsize = all.size();
    	if(nowsize<=maxsize){
    		bestPop.clear();
    		bestPop = (Population) all.clone();
    	}else{
    		all.sortByFitness();
    		bestPop.clear();
    		for(int i=0;i<=maxsize-1;i++){
    			bestPop.addChromosome((IChromosome) all.getChromosome(i).clone());
    		}
    	}
    	obj.setBestPop(bestPop);
    	return bestPop;
    }
    
    /**
     * 维护一个局部最优的个体集合
     * @param chroms 新产生的个体
     * @param obj 算法对象
     * @return
     */
    private static Population maintain_local_best(List<IChromosome> chroms, APGA obj){
    		
    	//维护一个局部最优群体
       	Population localPop = obj.getLocalPop();
       	int maxsize = localPop.getConfiguration().getPopulationSize();
       	Population all = (Population) localPop.clone();
       	//新的染色体排序
		if (chroms != null && chroms.size() > 0) {

			Collections.sort(chroms, new Comparator<IChromosome>() {
				public int compare(IChromosome chrom1, IChromosome chrom2) {
					return ((Double) chrom2.getFitnessValueDirectly())
							.compareTo(chrom1.getFitnessValueDirectly());
				}
			});

		}
       	int newsize = chroms.size();
       	int nowsize = all.size();
       	if(nowsize+10<=maxsize){
       		for(int i=0;i<=9;i++){
       			all.addChromosome(chroms.get(i));
       		}
       		localPop.clear();
       		localPop = (Population) all.clone();
       	}else{
       		localPop.clear();
       	    if(newsize<=10){
       	    	for(IChromosome chrom:chroms){
           			all.addChromosome(chrom);
           		}
       	    	for(int i=nowsize+newsize-maxsize;i<=nowsize+newsize-1;i++){
       	    		localPop.addChromosome((IChromosome) all.getChromosome(i).clone());
           		}
       	    }else{
       	    	for(int i=0;i<=9;i++){
           			all.addChromosome(chroms.get(i));
           		}
       	    	for(int i=nowsize+10-maxsize;i<=nowsize+9;i++){
       	    		localPop.addChromosome((IChromosome) all.getChromosome(i).clone());
           		}
       	    }
       	}
       	obj.setLocalPop(localPop);
    	return localPop;
    }
    
    /**
     * 维护一个局部最差的个体集合
     * @param chroms 新产生的个体
     * @param obj 算法对象
     * @return
     */
    private static Population maintain_local_worst(List<IChromosome> chroms, APGA obj){
    		
    	//维护一个局部最优群体
       	Population localWorst = obj.getLocalWorst();
       	int maxsize = localWorst.getConfiguration().getPopulationSize();
       	Population all = (Population) localWorst.clone();
       	//新的染色体排序
		if (chroms != null && chroms.size() > 0) {

			Collections.sort(chroms, new Comparator<IChromosome>() {
				public int compare(IChromosome chrom1, IChromosome chrom2) {
					return ((Double) chrom1.getFitnessValueDirectly())
							.compareTo(chrom2.getFitnessValueDirectly());
				}
			});

		}
       	int newsize = chroms.size();
       	int nowsize = all.size();
       	if(nowsize+10<=maxsize){
       		for(int i=0;i<=9;i++){
       			all.addChromosome(chroms.get(i));
       		}
       		localWorst.clear();
       		localWorst = (Population) all.clone();
       	}else{
       		localWorst.clear();
       	    if(newsize<=10){
       	    	for(IChromosome chrom:chroms){
           			all.addChromosome(chrom);
           		}
       	    	for(int i=nowsize+newsize-maxsize;i<=nowsize+newsize-1;i++){
       	    		localWorst.addChromosome((IChromosome) all.getChromosome(i).clone());
           		}
       	    }else{
       	    	for(int i=0;i<=9;i++){
           			all.addChromosome(chroms.get(i));
           		}
       	    	for(int i=nowsize+10-maxsize;i<=nowsize+9;i++){
       	    		localWorst.addChromosome((IChromosome) all.getChromosome(i).clone());
           		}
       	    }
       	}
       	obj.setLocalWorst(localWorst);
    	return localWorst;
    }
    
    private static Map<Integer, Double> getPatternlesscutoff(Population pop, APGA obj, Double cutoff){
    	
        Map<Integer, Double> newpattern = new HashMap();
    	int maxsize = pop.getConfiguration().getPopulationSize();
    	//根据新的最佳群体得到一个模式
    	
    	
    	int m = pop.size();
    	int n = pop.getConfiguration().getChromosomeSize();
    	
    	if(m>=maxsize){
			Matrix mydata = pop2matrix(pop);
			Double[] avgset = new Double[n];// 平均值
			for (int j = 0; j <= n - 1; j++) {
				Double tempsum = 0.0;
				for (int i = 0; i <= m/2 - 1; i++) {
					tempsum += mydata.data[i][j];
				}
				avgset[j] = 2*tempsum / m;
			}

			// 标准差
			for (int j = 0; j <= n - 1; j++) {
				Double tempsum = 0.0;
				for (int i = 0; i <= m / 2 - 1; i++) {
					tempsum += ((mydata.data[i][j] - avgset[j]) * (mydata.data[i][j] - avgset[j]));
				}
				double stddev = Math.sqrt(2*tempsum / m);
				if (stddev <= cutoff) {
					newpattern.put(j, stddev);
				}
			}

			String pattern = "";
			for (int i = 0; i <= n - 1; i++) {
				if (newpattern.get(i) == null) {
					pattern += "*\t";
				} else {
					pattern = pattern + avgset[i] + "\t";
				}
			}
			obj.getPatterns().add(pattern);
		}
    	return newpattern;
    }
    
    private static List<Integer> getPatternfromPop(Population pop, double cutoff, APGA obj){
    	List<Integer> result = new ArrayList();
    	//首先把pop转成二进制
    	List<List<Integer>> popbin = new ArrayList();
    	int n = 0;
    	for(int i = 0; i<=pop.size()-1; i++){
    		List<Integer> chrombin = new ArrayList();
    		for(int j = 0; j<=pop.getConfiguration().getChromosomeSize()-1;j++){
    			chrombin.addAll(doube2binary2(obj.getConsValue().getData()[0][j],
    					obj.getConsValue().getData()[1][j],
    					10,
    					(Double) pop.getChromosome(i).getGene(j).getAllele()));
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
    
    /**
     * 判断一个新的染色体能匹配上模式多少
     * @param chrom 新的染色体
     * @param bestPop 好的染色体集合
     * @param newpattern 模式
     * @return
     */
    private static int EstimateFitnessHowmuch(IChromosome chrom, Population bestPop, Map<Integer, Double> newpattern){
    	int result = 0;
    	
    	int m = bestPop.size();
    	int n = chrom.size();
    	Matrix mydata = pop2matrix(bestPop);
    	Double[] avgset = new Double[n];//平均值
    	for(int j = 0; j<=n-1; j++){
    		Double tempsum = 0.0;
    		for(int i = 0; i<=m-1;i++){
    			tempsum+=mydata.data[i][j];
    		}
    		avgset[j] = tempsum/m;
    	}
    	List<Map.Entry<Integer, Double>> mappingList =  new ArrayList<Map.Entry<Integer, Double>>(newpattern.entrySet());
    	for(Map.Entry<Integer, Double> item:mappingList){
    		int index = item.getKey();
    		double p_value = item.getValue();
    		double g_value = (Double) chrom.getGene(index).getAllele();
    		if(Math.abs(g_value-avgset[index])<p_value){
    			result++;
    		}
    	}
    	
    	return result;
    }
    
    /**
     * 判断一个新的染色体是否接近好的染色体
     * @param chrom 新的染色体
     * @param bestPop 好的染色体集合
     * @param cutoff 有多少gene匹配上，认为这个染色体符合这个模式
     * @return
     */
    private static boolean EstimateFitness(IChromosome chrom, Population bestPop, Map<Integer, Double> newpattern){
    	boolean result = true;
    	
    	int m = bestPop.size();
    	int n = chrom.size();
    	Matrix mydata = pop2matrix(bestPop);
    	Double[] avgset = new Double[n];//平均值
    	for(int j = 0; j<=n-1; j++){
    		Double tempsum = 0.0;
    		for(int i = 0; i<=m-1;i++){
    			tempsum+=mydata.data[i][j];
    		}
    		avgset[j] = tempsum/m;
    	}
    	List<Map.Entry<Integer, Double>> mappingList =  new ArrayList<Map.Entry<Integer, Double>>(newpattern.entrySet());
    	for(Map.Entry<Integer, Double> item:mappingList){
    		int index = item.getKey();
    		double p_value = item.getValue();
    		double g_value = (Double) chrom.getGene(index).getAllele();
    		if(Math.abs(g_value-avgset[index])>p_value){
    			return false;
    		}
    	}
    	
    	return result;
    }
    
    /**
     * 判断一个新的染色体是否接近好的染色体利用二进制
     * @param chrom 新的染色体
     * @param bestPop 好的染色体集合
     * @param cutoff 有多少gene匹配上，认为这个染色体符合这个模式
     * @return
     */
    private static boolean EstimateFitnessBybin(IChromosome chrom, APGA obj, List<Integer> newpattern){
    	boolean result = true;
    	
    	int n = chrom.size();
    	List<Integer> chrombin = new ArrayList();
		for(int j = 0; j<=n-1;j++){
			chrombin.addAll(doube2binary2(obj.getConsValue().getData()[0][j],
					obj.getConsValue().getData()[1][j],
					10,
					(Double)chrom.getGene(j).getAllele()));
		}
		for(int i = 0; i<=newpattern.size()-1;i++){
			if(newpattern.get(i)!=2){
				if(newpattern.get(i)!=chrombin.get(i)){
					return false;
				}
			}
		}
		
    	
    	return result;
    }
    
    public static  Double calconeFittnessValue(IChromosome chrom, APGA obj,  APGAFunction fitness){
    	List<IChromosome> chroms =new ArrayList();
    	chroms.add(chrom);
    	Double[] results = fitness.excute(chromlst2matrix(chroms), obj.getnIterateCount());
    	obj.setnIterateCount(obj.getnIterateCount()+1);
    	return results[0];
    }
    
    public static  List<Double> calcFittnessValueDrictely(Population pop, APGA obj,  APGAFunction fitness, double cutoff){
   
    	List<Double> objects = new ArrayList();
    	List<Integer> nullfitset = new  ArrayList();
    	List<IChromosome> null_chroms = new ArrayList();
    	Double[] fits;
    	for(int i = 0; i<=pop.size()-1; i++){
    		if(pop.getChromosome(i).getFitnessValueDirectly()<0){
    			nullfitset.add(i);
    			null_chroms.add(pop.getChromosome(i));
    		}
		}
		if (null_chroms.size() < 1) {
			return null;
		} else {
			fits = fitness.excute(chromlst2matrix(null_chroms), obj.getnIterateCount());
		}
		for(int i = 0; i<=null_chroms.size()-1; i++){
			int j = nullfitset.get(i);
			pop.getChromosome(j).setFitnessValue(fits[i]);
			((Chromosome)pop.getChromosome(j)).setIscenter(true);
			null_chroms.get(i).setFitnessValue(fits[i]);
			((Chromosome)null_chroms.get(i)).setIscenter(true);
		}
		Population bestPop = maintain_history_best(null_chroms, obj);
		Population localPop = maintain_local_best(null_chroms, obj);
		Population localWorst = maintain_local_worst(null_chroms, obj);
        obj.setnIterateCount(obj.getnIterateCount()+null_chroms.size());
    	return objects;
    }
    
    public static  List<Double> calcFittnessValue(Population pop, APGA obj,  APGAFunction fitness, List<Integer> results, double[][] datamatrix, double lamda, double cutoff, double extra){

		List<IChromosome> chrs = pop.getChromosomes();
		List<Double> objects = new ArrayList();
		List<Integer> clac_centers = new ArrayList();
		Set<Integer> centers = new HashSet();
		centers.addAll(results);
		Map<Integer, Double> centerObjects = new HashMap();
		Iterator iter = centers.iterator();
		List<IChromosome> center_chroms = new ArrayList();
		while (iter.hasNext()) {
			int a = (Integer) iter.next();
			if(((Chromosome)pop.getChromosome(a)).isIscenter()&&pop.getChromosome(a).getFitnessValueDirectly()>0){
				centerObjects.put(a, pop.getChromosome(a).getFitnessValueDirectly());
			}else{
			    center_chroms.add(pop.getChromosome(a));
			    clac_centers.add(a);
			}
		}
		Double[] fits;
		if(center_chroms.size()>0){
			fits = fitness.excute(chromlst2matrix(center_chroms),obj.getnIterateCount());
			obj.setnIterateCount(obj.getnIterateCount() + center_chroms.size());
			for (int i = 0; i <= clac_centers.size() - 1; i++) {
				centerObjects.put(clac_centers.get(i), fits[i]);
				((Chromosome) pop.getChromosome(clac_centers.get(i))).setIscenter(true);
				center_chroms.get(i).setFitnessValue(fits[i]);
				((Chromosome)center_chroms.get(i)).setIscenter(true);
			}
		}
		
		Population bestPop = maintain_history_best(center_chroms, obj);
		Population localPop = maintain_local_best(center_chroms, obj);
		Population localWorst = maintain_local_worst(center_chroms, obj);
		
		Map<Integer, Double> bestpattern = getPatternlesscutoff(localPop, obj, cutoff);
		Map<Integer, Double> worstpattern = getPatternlesscutoff(localWorst, obj, cutoff);

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
		double diffmin = 6000;
		double diffmax = 0;
		double diffsum = 0;
		for(int i=0; i <= datamatrix.length-1; i++){
			double tempfit  = 0.0;
			if(i==results.get(i)){
				tempfit = centerObjects.get(i).doubleValue();
			}else{
				
				double s = 1/(1+datamatrix[i][results.get(i)]);
				tempfit = ((1-lamda)*s+lamda)*centerObjects.get(results.get(i));
//				tempfit = (Math.random()+0.5)*centerObjects.get(results.get(i));
//				tempfit = centerObjects.get(results.get(i));
				// 对于局部模式估计 这里用localPop
//				boolean flag = EstimateFitnessBybin(chrs.get(i), obj, newpattern);
				int e_much = EstimateFitnessHowmuch(chrs.get(i), localPop, bestpattern);
				int w_much = EstimateFitnessHowmuch(chrs.get(i), localWorst, worstpattern);
//				boolean flag = EstimateFitness(chrs.get(i), bestPop, newpattern);
//				boolean flag = EstimateFitness(chrs.get(i), localPop, newpattern);
//				if(localPop.size()>=bestPop.getConfiguration().getPopulationSize()&&flag){
				if(localPop.size()>=bestPop.getConfiguration().getPopulationSize()){
					if(e_much>0){
//					    tempfit = tempfit+tempfit*extra;
					    tempfit = tempfit+tempfit*extra*e_much/bestpattern.size();
					}
					if(w_much>0){
//					    tempfit = tempfit-tempfit*extra;
					    tempfit = tempfit-tempfit*extra*w_much/worstpattern.size();
					}
					if(tempfit>bestPop.determineFittestChromosome().getFitnessValueDirectly()){
						tempfit=bestPop.determineFittestChromosome().getFitnessValueDirectly();
					}
					mycount++;
				}
			}
		
//			realone[i] = calconeFittnessValue(pop.getChromosome(i), obj,  fitness);
//			evalone[i] = tempfit;
//			diff[i] = Math.abs(realone[i]-evalone[i]);
//			if(diff[i]>diffmax)diffmax = diff[i];
//			if(diff[i]!=0&&diff[i]<diffmin)diffmin = diff[i];
//			diffsum += diff[i];
			
			objects.add(tempfit);
			if(pop.getChromosome(i).getFitnessValueDirectly()<0||!((Chromosome)pop.getChromosome(i)).isIscenter()){
			    pop.getChromosome(i).setFitnessValue(tempfit);
			    ((Chromosome)pop.getChromosome(i)).setIscenter(false);
			}
		}

//		double diffavg = diffsum/datamatrix.length;
//		double diffstdsum = 0;
//		for(int i=0; i <= datamatrix.length-1; i++){
//			diffstdsum+=((diffavg-diff[i])*(diffavg-diff[i]));
//		}
//		double diffstd = Math.sqrt(diffstdsum/datamatrix.length);
//		String fitstr = diffmin + "\t" + diffmax + "\t" + diffstd;
//		obj.getFitnessvalues().add(fitstr);

		return objects;
	}
	
    private static Matrix chromlst2matrix(List<IChromosome> pop){
  	  if(pop==null){
  		  return null;
  	  }
  	  int m = pop.size();
  	  int n = pop.get(0).size();
  	  Matrix result = new Matrix(m,n);
  	  for(int i = 0; i<=m-1;i++){
  		  for(int j = 0; j<=n-1;j++){
  			  result.data[i][j] = (Double)pop.get(i).getGene(j).getAllele();
  		  }
  	  }
  	  return result;
    }
    
    private static Matrix pop2matrix(Population pop){
    	  if(pop==null){
    		  return null;
    	  }
    	  int m = pop.size();
    	  int n = pop.getChromosome(0).size();
    	  Matrix result = new Matrix(m,n);
    	  for(int i = 0; i<=m-1;i++){
    		  for(int j = 0; j<=n-1;j++){
    			  result.data[i][j] = (Double)pop.getChromosome(i).getGene(j).getAllele();
    		  }
    	  }
    	  return result;
      }
  //二进制分三部分，第一位是符号位，接下来几位表示整数部分，最后一些是小数部分
  	private static List<Integer> doube2binary2(double min, double max, int p, double num){
  		List<Integer> result = new ArrayList();
  		//首先确定符号，1表示+，0表示-
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
  		//确定小数部分，这个和精度有关系， 比如小数点后面3位，精度为10， 4位精度为14位， 5位为17, 6位为20位
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
    
	public static AffinityConnectingMethod getConnMode(Map<String, String> map) {
        String modeStr = map.get("conn");
        if (modeStr == null) {
            return AffinityConnectingMethod.ORIGINAL;
        } else {
            if (modeStr.equals("org")) {
                return AffinityConnectingMethod.ORIGINAL;
            } else {
                return AffinityConnectingMethod.PRIME_ALG;
            }
        }
    }

	public static String getOutputKind(Map<String, String> map) {
        String kind = map.get("kind");
        if (kind == null) {
            return "clusters";
        } else {
            if (kind.equals("centers")) {
                return kind;
            } else {
                return "clusters";
            }
        }
    }

	public static Double getPreferences(Map<String, String> map) {
        String lamStr = map.get("p");
        if (lamStr == null) {
            System.out.println("You have to set preferences (p)!");
            return null;
        } else {
            try {
                System.out.println("pref: " + Double.valueOf(lamStr));
                return Double.valueOf(lamStr);
            } catch (NumberFormatException e) {
                return null;
            }
        }
    }

	public static Double getLambda(Map<String, String> map) {
        String lamStr = map.get("lam");
        if (lamStr == null) {
            System.out.println("You have to set lambda (lam)!");
            return null;
        } else {
            try {
                return Double.valueOf(lamStr);
            } catch (NumberFormatException e) {
                return null;
            }
        }
    }

	public static Integer getIterations(Map<String, String> map) {
        try {
            return Integer.valueOf(map.get("it"));
        } catch (NumberFormatException e) {
            return null;
        }
    }

	public static Integer getConvits(Map<String, String> map) {
        try {
            return Integer.valueOf(map.get("con"));
        } catch (NumberFormatException e) {
            return null;
        }
    }

	public static String getFilepath(Map<String, String> map) {
        return map.get("in");
    }

	public static String getFoutput(Map<String, String> map) {
        return map.get("out");
    }

	public static boolean getRefine(Map<String, String> map) {
        String ref = map.get("ref");
        if (ref == null) {
            return true;
        } else if (ref.equals("false")) {
            return false;
        } else {
            return true;
        }
    }

	public static Integer getSteps(Map<String, String> map) {
        String depthStr = map.get("dep");
        if (depthStr == null) {
            return null;
        } else {
            try {
                return Integer.valueOf(depthStr);
            } catch (NumberFormatException e) {
                return null;
            }
        }
    }

	public static boolean getTakeLog(Map<String, String> map) {
        String getLog = map.get("log");
        if (getLog == null) {
            return false;
        } else if (getLog.equals("true")) {
            return true;
        } else {
            return false;
        }
    }
}
