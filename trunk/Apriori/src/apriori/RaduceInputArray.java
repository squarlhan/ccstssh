package apriori;

/*
 * 类RaduceInputArray作用是负责对输入事务数据库进行裁减
 * 其裁减原则是：如果事务数据库中的一条事务，不包含任何
 * 频繁项，则将该事务从数据库中删除
 */
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
class RaduceInputArray {
	
	/*
	 * 算法作用：根据频繁项集对输入数据库进行裁减，提高算法的总体效率
	 * 算法思想：对于输入的每一个事务数据，如果其不包含频繁项，则从事务
	 *          数据库inputDB中删除该项。
	 * 辅助函数：isArrayListContainFrequentItem
	 * 
	 * 参数：    inputDB是从数据库中转到ArrayList的输入数据；
	 *           preFreItemSet是频繁项集。
	 */
	public static void reduceInputDB(ArrayList inputDB, Map preFreItemSet){
		for(int i = 0;i < inputDB.size();i++){
			ArrayList temp = (ArrayList)(inputDB.get(i));
			if(!isArrayListContainFrequentItem(temp, preFreItemSet))
				inputDB.remove(i);
		}
	}
	
	/*
	 * 算法作用：判断一个事务数据是否包含有频繁项。
	 * 算法原理：利用类ProduceFreItemSet中的isArrayListContainString函数，对频繁项集中的每一个
	 *          频繁项进行判断，频繁项均是"1|10|11"这种形式的字符串。
	 * 辅助函数：ProduceFreItemSet.isArrayListContainString
	 * 
	 * 参数：    arrayList是inputDB中的每一个事务数据；
	 *           preFreItemSet是频繁项集。
	 */

	 private static boolean isArrayListContainFrequentItem(ArrayList arrayList, Map preFreItemSet){
			Set tempSet = preFreItemSet.keySet();
			Iterator iterator = tempSet.iterator();
			while(iterator.hasNext()){
			    if(ProduceFreItemSet.isArrayListContainString(arrayList, iterator.next().toString()))
			    	return true;
			}
			return false;
		}
}
