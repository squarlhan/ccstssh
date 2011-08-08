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

    public static  List<Double> calcObjectValue(Population pop, List<Integer> results, double[][] datamatrix, double lamda){
    	MaxFunction mf = new MaxFunction();
		List<IChromosome> chrs = pop.getChromosomes();
		List<Double> objects = new ArrayList();
		Set<Integer> centers = new HashSet();
		centers.addAll(results);
		Map<Integer, Double> centerObjects = new HashMap();
		Iterator iter = centers.iterator();
		while(iter.hasNext()){
			int a = (Integer)iter.next();
			//do something to get the objective value
			Double dis = mf.evaluate(chrs.get(a));
			centerObjects.put(a, dis);
		}
		for(int i=0; i <= datamatrix.length-1; i++){
			double object;
			if(i==results.get(i)){
				object = centerObjects.get(i);
			}else{
				double s = 1/(1-datamatrix[i][results.get(i)]);
				object = ((1-lamda)*s+lamda)*centerObjects.get(results.get(i));
			}
			objects.add(object);
			pop.getChromosome(i).setFitnessValue(object);
		}
		/*for(double a : objects){
        	System.out.println(a);
        }*/

		return objects;
	}
    /**
     * 返回一个模式
     * 维护一个前40fitness的种群
     * @param chroms 新产生的染色体
     * @param obj 算法对象，里面包含最好的种群
     * @param cutoff 有多少gene匹配上，认为这个染色体符合这个模式
     */
    private static Map<Integer, Double> maintainbestchrom(List<IChromosome> chroms, APGA obj, Double cutoff){
    	
    	Map<Integer, Double> newpattern = new HashMap();
    	
    	Population bestPop = obj.getBestPop();
    	int maxsize = bestPop.getConfiguration().getPopulationSize();
    	Population all = (Population) bestPop.clone();
//    	all.clear();
//    	if(bestPop!=null&&bestPop.size()>0){
//    		for(int i = 0; i<= bestPop.size()-1; i++){
//    			if(bestPop.getChromosome(i).getFitnessValueDirectly()>0){
//    				all.addChromosome(bestPop.getChromosome(i));
//    			}else{
//    				;
//    			}
//    		}
//    }
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
		}
    	return newpattern;
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
    		if(Math.abs(g_value-avgset[index])>p_value)return false;
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
		Map<Integer, Double> newpattern = maintainbestchrom(null_chroms, obj, cutoff);
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
		
		Map<Integer, Double> newpattern = maintainbestchrom(center_chroms, obj, cutoff);

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
		for(int i=0; i <= datamatrix.length-1; i++){
			double object;
			if(i==results.get(i)){
				object = centerObjects.get(i);
			}else{
				double s = 1/(1+datamatrix[i][results.get(i)]);
				object = ((1-lamda)*s+lamda)*centerObjects.get(results.get(i));
				Population bestPop = obj.getBestPop();
				if(bestPop.size()>=bestPop.getConfiguration().getPopulationSize()/2&&EstimateFitness(chrs.get(i), bestPop, newpattern)){
					object *= (1+extra);
				}
			}
			objects.add(object);
			if(pop.getChromosome(i).getFitnessValueDirectly()<0||!((Chromosome)pop.getChromosome(i)).isIscenter())
			pop.getChromosome(i).setFitnessValue(object);
			((Chromosome)pop.getChromosome(i)).setIscenter(false);
		}
		/*for(double a : objects){
        	System.out.println(a);
        }*/

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
