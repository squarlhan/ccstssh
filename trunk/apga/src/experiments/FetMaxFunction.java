/*
 * This file is part of JGAP.
 *
 * JGAP offers a dual license model containing the LGPL as well as the MPL.
 *
 * For licensing information please see the file license.txt included with JGAP
 * or have a look at the top of class org.jgap.Chromosome which representatively
 * includes the JGAP license policy applicable for any file delivered with JGAP.
 */
package experiments;

import java.util.*;
import java.util.ArrayList;
import java.util.List;

import org.jgap.*;
import org.jgap.impl.*;

import experiments.fea.Exectute;


/**
 * Fitness function for our example. See evaluate(...) method for details.
 *
 * @author Neil Rotstan
 * @author Klaus Meffert
 * @since 2.0
 */
public class FetMaxFunction 
    extends FitnessFunction{
  /** String containing the CVS revision. Read out via reflection!*/
  private final static String CVS_REVISION = "$Revision: 1.6 $";
  
  public static int counts = 0;

  /**
   * This example implementation calculates the fitness value of Chromosomes
   * using BooleanAllele implementations. It simply returns a fitness value
   * equal to the numeric binary value of the bits. In other words, it
   * optimizes the numeric value of the genes interpreted as bits. It should
   * be noted that, for clarity, this function literally returns the binary
   * value of the Chromosome's genes interpreted as bits. However, it would
   * be better to return the value raised to a fixed power to exaggerate the
   * difference between the higher values. For example, the difference
   * between 254 and 255 is only about .04%, which isn't much incentive for
   * the selector to choose 255 over 254. However, if you square the values,
   * you then get 64516 and 65025, which is a difference of 0.8% -- twice
   * as much and, therefore, twice the incentive to select the higher
   * value.
   *
   * @param a_subject the Chromosome to be evaluated
   * @return defect rate of our problem
   *
   * @author Neil Rotstan
   * @author Klaus Meffert
   * @since 2.0
   */
  public double evaluate(IChromosome a_subject) {
    double total = 0;
     final double MAXFOCRCE=2.35e8,MIN_AREA=2849*1.0,MAX_AREA=2849*2.5;
    int time_delay = 0;
    try {
		Thread.sleep(time_delay);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    int n = a_subject.size();
    double[] rs = new double[n];
    double sum = 0,Max_Force=0,Min_Force=0,Average_Force=0;
    
    
    for (int i = 0; i < n; i++) {     
    	rs[i] = (Double)a_subject.getGene(i).getAllele();
        sum += rs[i] ;
    }
    
    for(int i=0;i<n;i++){
    	int max=i;
    	for(int j=i+1;j<n;j++){
    		if(rs[max]<rs[j]){
    			max=j;
    		}
    	}
    	if(max!=i){
    	double temp=rs[max];
    	rs[max]=rs[i];
    	rs[i]=temp;
    	}
//    	System.out.print(rs[i]+"  ");
    }
//    System.out.println();
//    sum=0;
//    long starttime=System.currentTimeMillis();
    Exectute fea = new Exectute(a_subject);
    fea.FirstCallFrotran();
    fea.NewArea(rs);
    double result  = fea.CaculateOutputData();
//    long endtime=System.currentTimeMillis();
//    System.out.println("runtime"+(endtime-starttime)+"ms");
    List list=a_subject.GetFitnessList();
    Iterator  itor=list.iterator();
    boolean isArea=true;
    while(itor.hasNext()){
    	List sublist=(List)itor.next();
    	Iterator subitor=sublist.iterator();
    	if(isArea){
    	for(int i=0;i<sublist.size();i++){
    		subitor.next();
    	}
    	isArea=false;
    	}
    	else{
    		for(int i=0;i<sublist.size()-3;i++){
    			subitor.next();
    		}
    		Max_Force=(Double)subitor.next();
    	    Average_Force=(Double)subitor.next();
    		Min_Force=(Double)subitor.next();
    	}
    }
//    System.out.println(Max_Force+"  "+Average_Force+"  "+Min_Force);
//    System.out.println(1/result);
    counts ++;
    
   
//    double result = n*Math.pow(100, 2.0)-total; 
//    result = 1000*(result)/(n*Math.pow(100, 2.0));
    
//    result=(Math.exp(-(sum-MIN_AREA)/(MAX_AREA-MIN_AREA))/(1+Math.exp(400*(result/MAXFOCRCE-1))))*1000;
//    System.out.println(result);
result=(Math.exp(-0.5*(result-MIN_AREA)/(MAX_AREA-MIN_AREA)-0.5*(Average_Force/MAXFOCRCE))/(1+Math.exp(400*(Max_Force/MAXFOCRCE-1))))*1000;
    if(result<0.0){
    	result=0;
    }
    return result;
    
//    if(result!=-1){
//    result =((MAX_AREA-sum)/(MAX_AREA-MIN_AREA)+1-result/MAXFOCRCE)*500;
//    return result;
//    }
//    else{
//    	return 0;
//    }
  }
}
