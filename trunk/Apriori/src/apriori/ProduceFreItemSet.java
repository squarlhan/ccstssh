package apriori;

/*
 *类ProduceFreItemSet封装了公有方法find_frequent_1_item
 *和fromItemSetToFreItemSet。其中，find_frequent_1_item作用是
 *在事务数据库中查找符合最小支持度的频繁1-项集。
 *fromItemSetToFreItemSet用于从候补k-项集中找出频繁k-项集。其中
 *的候补k-项集是有Apriori_gen类中的apriori_gen方法产生的。
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.LinkedHashSet;
import java.util.LinkedHashMap;

public class ProduceFreItemSet {
	/*
	 * 算法作用：方法find_frequent_1_item是从输入数据中，找出所有的
	 *          频繁1项。
	 * 算法思想：扫描存储在ArrayList中的数据库数据，计算出现的频度(support)，
	 *          如果出现频度不小于min_sup，则将该项及其support放入1-频繁项集中。
	 *          
	 * 参数：   arrayList是从数据库中转到ArrayList的输入数据；
	 *          min_sup是最小支持度。
	 * 
	 */
	public static Map find_frequent_1_item(ArrayList arrayList, int min_sup){
		Map itemSet = new LinkedHashMap();
		Set tempSet = new LinkedHashSet();
		for(int i = 0;i < arrayList.size();i++){
			ArrayList tempArrayList = (ArrayList)(arrayList.get(i));
			for(int j = 0;j < tempArrayList.size();j++){
				Object tempObj = tempArrayList.get(j);
				tempSet.add(tempObj);
				int value = 1;
				if(itemSet.containsKey(tempObj)){
					value = ((Integer)(itemSet.get(tempObj))).intValue();
					value++;
				}
				itemSet.put(tempObj, value);
			}
		}
		Iterator iterator = tempSet.iterator();
		while(iterator.hasNext()){
			Object tempObj = iterator.next();
			int num = ((Integer)(itemSet.get(tempObj))).intValue();
			if(num < min_sup)
				itemSet.remove(tempObj);

		}
		return itemSet;
	}
	
	/*
	 * 算法作用：从候选项集中找出频繁项集。
	 * 算法思想：对于候选项集中的每一项，遍历输入的事务，计算其支持度，如果其支持度不小于
	 *          min_sup，将该项及其支持度放入频繁项集中。
	 * 辅助函数：isArrayListContainString
	 * 
	 * 参数：   arrayList是从数据库中转到ArrayList的输入数据；
	 *          itemset是候选项集；
	 *          min_sup是最小支持度。
	 * 
	 */
	public static Map fromItemSetToFreItemSet(ArrayList arrayList, Map itemSet, int min_sup){
		Map freItemSet = new LinkedHashMap();
		Set tempSet = itemSet.keySet();
		Iterator iterator = tempSet.iterator();
		ArrayList al1 = new ArrayList();
		ArrayList al2 = new ArrayList();
		while(iterator.hasNext()){
			String tempStr = iterator.next().toString();
			for(int i = 0;i < arrayList.size();i++){
				ArrayList tempAL = (ArrayList)arrayList.get(i);
				if(isArrayListContainString(tempAL, tempStr)){
					if(al1.contains(tempStr)){
						int index = al1.indexOf(tempStr);
						int count = ((Integer)al2.get(index)).intValue();
						count++;
						al2.set(index, count);
					}
					else{
						int value = 1;
						al1.add(tempStr);
						al2.add(value);

					}
				}

			}
		}
		for(int i = 0;i < al2.size();i++){
			if(((Integer)al2.get(i)).intValue() >= min_sup)
				freItemSet.put(al1.get(i), ((Integer)al2.get(i)).intValue());
		}
		return freItemSet;
	}
	
	/*
	 * 算法作用：方法isArrayListContainString用于判断事务数据库中的每个事务是否包含
	 *          项str，是方法isArrayListContainString的辅助函数。
	 * 算法思想：数据库的每一个事务作为一个ArrayList存储在另一ArrayList中，利用"|"对项
	 *          str进行拆分，对于没一个拆分出的字符串遍历每一个事务，判断其是否在该事务中。
	 *          
	 * 参数：    arrayList是从数据库中转到ArrayList的输入数据；
	 *          str是待检测的项。
	 */
	public static boolean isArrayListContainString(ArrayList arrayList, String str){
		int flag = 1;
		StringTokenizer tokenizer = new StringTokenizer(str, "|");
		while(tokenizer.hasMoreTokens()){
			if(!arrayList.contains(tokenizer.nextToken())){
				flag = 0;
			}
		}
		if(flag == 1)
			return true;
		return false;
	}
}
