package apriori;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.text.DecimalFormat;
public class Apriori_generateRule {
	public static ArrayList generatreRule(Map freItemSet, double min_confidence){
		Set tempSet = freItemSet.keySet();
		Iterator iterator = tempSet.iterator();
		ArrayList result = new ArrayList();
		while(iterator.hasNext()){
			String temp = iterator.next().toString();
			ArrayList arrayList = SubSet.subSet(temp);
			for(int i = 0;i < arrayList.size();i++){
				Rule rule = new Rule(arrayList.get(i).toString(), temp);
				countConfidence(rule, freItemSet);
				if(rule.getConfidence() >= min_confidence)
					result.add(rule);
			}
		}
		return result;
	}
	private static void countConfidence(Rule rule, Map freItemSet){
		int temp1 = ((Integer)freItemSet.get(rule.getConditionAndConclution())).intValue();
		int temp2 = ((Integer)freItemSet.get(rule.getCondition())).intValue();
		double temp = temp1 / (temp2 * 1.0);
		rule.setConfidence(temp);
	}

}
