package apriori;


/*类Apriori_gen是包级私有的，目的是只让Apriori类访问它
 *包含公有方法apriori_gen。
 * 
 */
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.StringTokenizer;
class Apriori_gen {
	/* 算法作用：方法apriori_gen是类Apriori_gen唯一的共有类，
	 *          其作用是从k-1项频繁项集产生出k项候补项集。
	 *          输出一个包含所有k项候补项集的LinkedHashMap。
	 *          
	 * 算法思想：对于preFreItemSet中的频繁k-1项集中的l1和l2,
	 *          如果除最后一项不同外l1和l2的所有项均相同，
	 *          那么合并l1,l2，合并方法是将较大的最后一项
	 *          加到令一项的结尾，形成新的候选k-项。
	 * 
	 * 辅助函数：keyFromMapToArrayList
	 *          isEqualExceptLastIndex
	 *          has_infrequent_subset
	 *          
	 * 参数：   频繁k-1项集preFreItemSet,其存储于LinkedHashMap中
	 *         key为项， 对应的value为该项的support
	 *         
	 */
	public static Map apriori_gen(Map preFreItemSet){
		Map result = new LinkedHashMap();
		ArrayList tempAL = keyFromMapToArrayList(preFreItemSet);
		for(int i = 0;i < tempAL.size();i++){
			for(int j = i + 1;j < tempAL.size();j++){
				String str1 = tempAL.get(i).toString();
				String str2 = tempAL.get(j).toString();
				if(str1.contains("|")){
					if(isEqualExceptLastIndex(str1, str2)){
						String temp1 = str1.substring(str1.lastIndexOf("|") + 1);
						String temp2 = str2.substring(str2.lastIndexOf("|") + 1);
						String temp3;
						int num1 = (new Integer(temp1)).intValue();
						int num2 = (new Integer(temp2)).intValue();
						if(num1 < num2){//此处比较大小的目的是为了将较小的数放到项的前面
							temp3 = str1 + "|" + str2.substring(str2.lastIndexOf("|") + 1);
						}
						else
							temp3 = str2 + "|" + str1.substring(str1.lastIndexOf("|") + 1); 
						if(!has_infrequent_subset(temp3, preFreItemSet))
							result.put(temp3, 0);
					}
				}
				else{
					String temp;
					int num1 = (new Integer(str1)).intValue();
					int num2 = (new Integer(str2)).intValue();
					if(num1 < num2)
						temp = str1 + "|" + str2;
					else
						temp = str2 + "|" + str1;
					result.put(temp, 0);
				}
			}
		}
		return result;
	}
	/* 算法作用：方法keyFromMapToArrayList是将map中的key装化成一个
	 *          ArrayList。
	 * 
	 */
	private static ArrayList keyFromMapToArrayList(Map itemSet){
		Set tempSet = itemSet.keySet();
		ArrayList arrayList = new ArrayList();
		Iterator iterator = tempSet.iterator();
		while(iterator.hasNext()){
			String tempString = iterator.next().toString();
			arrayList.add(tempString);
		}
		return arrayList;
	}

	
	/* 算法作用：方法isEqualExceptLastIndex是判断字符串str1和str2是否符合
	 *          以下标准：除最后一个元素不同外，其余元素均相同。
	 * 算法思想：将字符串分解成两部分，一部分包含出最后一个字符串外字字符串，
	 *          另一部分包含"|"以及最后一个字符串。
	 *          
	 * 参数：    str1和str2是两个形如"1|10|24"的项。
	 */
	private static boolean isEqualExceptLastIndex(String str1, String str2){
		String temp1 = str1.substring(0, str1.lastIndexOf("|"));
		String temp2 = str2.substring(0, str2.lastIndexOf("|"));
		String temp3 = str1.substring(str1.lastIndexOf("|"));
		String temp4 = str2.substring(str2.lastIndexOf("|"));
		if(temp1.equals(temp2) && !temp3.equals(temp4))
			return true;
		return false;
	}
	
	
	/* 算法作用：函数has_infrequent_subset用于判断项str是否含有非频繁模式，
	 *          因为根据apriori性质，非频繁模式的超集一定不是频繁模式。
	 *          因此，在产生候选频繁模式的过程中删除那些包含非频繁模式的项
	 *          减少后面遍历ArrayList的次数。
	 * 
	 * 算法思想：对于k项候选项集，产生所有的k-1项子集，再遍历k-1项
	 *          频繁项集。如果不包含其中的一个子集，则返回false.
	 * 
	 * 
	 * 辅助函数：substringOfDelOneChar用于产生k-1项子集。
	 * 
	 * 参数：    str是产生候补项集中的一个项；
	 *           preFreItemSet是k-1项频繁项集。
	 */
	private static boolean has_infrequent_subset(String str, Map preFreItemSet){
		ArrayList tempAL = substringOfDelOneChar(str);
		for(int i = 0;i < tempAL.size();i++){
			if(!preFreItemSet.containsKey(tempAL.get(i).toString()))
				return true;
		}
		return false;
	}
	

	/* 算法作用：函数substringOfDelOneChar是函数has_infrequent_subset的辅助函数，
	 *          其作用是从k项集中产生所有k-1项子集。
	 * 
	 * 算法思想：项的模式形如"1|10|24",对于首字符，直接去掉其和后面的"|"
	 *          对于其他字符，用一个空字符替换其和其之前的"|"。
	 * 
	 * 参数：    str是形如"1|10|24"的项。
	 * 
	 * 
	 */
	private static ArrayList substringOfDelOneChar(String str){
		ArrayList result = new ArrayList();
		StringTokenizer tokenizer = new StringTokenizer(str, "|");
		while(tokenizer.hasMoreTokens()){
			String temp1 = tokenizer.nextToken().toString();
			if(str.indexOf(temp1) == 0){
				result.add(str.substring(temp1.length() + 1));
			}
			else{
				String temp2;
				temp2 = str.replace(("|" + temp1), "");
				result.add(temp2);
			}
		}
		return result;
	}
}
