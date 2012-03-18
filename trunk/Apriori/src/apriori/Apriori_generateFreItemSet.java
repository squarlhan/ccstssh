package apriori;


import java.util.Map;
import java.util.LinkedHashMap;
import java.util.ArrayList;
public class Apriori_generateFreItemSet {
	public static Map generateFreItemSet(ArrayList inputDB, int min_sup){
		Map tempMap;
		tempMap = ProduceFreItemSet.find_frequent_1_item(inputDB, min_sup);
		Map resultMap = new LinkedHashMap();
		resultMap.putAll(tempMap);
		while(tempMap.size() >= 2){//Ƶ��ģʽ���ֻʣһ�����ʱ�򣬾Ͳ����ټ�����
			Map innerMap = new LinkedHashMap();
			innerMap.putAll(tempMap);
			tempMap = Apriori_gen.apriori_gen(tempMap);
		
			if(tempMap.isEmpty()){
				resultMap.putAll(innerMap);
			}		
			else{
				tempMap = ProduceFreItemSet.fromItemSetToFreItemSet(inputDB, tempMap, min_sup);
				resultMap.putAll(tempMap);
				RaduceInputArray.reduceInputDB(inputDB, tempMap);
			}
		}
		return resultMap;
	}
}
