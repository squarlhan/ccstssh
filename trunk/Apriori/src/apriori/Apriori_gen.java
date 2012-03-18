package apriori;


/*��Apriori_gen�ǰ���˽�еģ�Ŀ����ֻ��Apriori�������
 *�������з���apriori_gen��
 * 
 */
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.StringTokenizer;
class Apriori_gen {
	/* �㷨���ã�����apriori_gen����Apriori_genΨһ�Ĺ����࣬
	 *          �������Ǵ�k-1��Ƶ���������k������
	 *          ���һ����������k������LinkedHashMap��
	 *          
	 * �㷨˼�룺����preFreItemSet�е�Ƶ��k-1��е�l1��l2,
	 *          ��������һ�ͬ��l1��l2�����������ͬ��
	 *          ��ô�ϲ�l1,l2���ϲ������ǽ��ϴ�����һ��
	 *          �ӵ���һ��Ľ�β���γ��µĺ�ѡk-�
	 * 
	 * ����������keyFromMapToArrayList
	 *          isEqualExceptLastIndex
	 *          has_infrequent_subset
	 *          
	 * ������   Ƶ��k-1�preFreItemSet,��洢��LinkedHashMap��
	 *         keyΪ� ��Ӧ��valueΪ�����support
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
						if(num1 < num2){//�˴��Ƚϴ�С��Ŀ����Ϊ�˽���С�����ŵ����ǰ��
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
	/* �㷨���ã�����keyFromMapToArrayList�ǽ�map�е�keyװ����һ��
	 *          ArrayList��
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

	
	/* �㷨���ã�����isEqualExceptLastIndex���ж��ַ���str1��str2�Ƿ����
	 *          ���±�׼�������һ��Ԫ�ز�ͬ�⣬����Ԫ�ؾ���ͬ��
	 * �㷨˼�룺���ַ����ֽ�������֣�һ���ְ��������һ���ַ��������ַ�����
	 *          ��һ���ְ���"|"�Լ����һ���ַ�����
	 *          
	 * ������    str1��str2����������"1|10|24"���
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
	
	
	/* �㷨���ã�����has_infrequent_subset�����ж���str�Ƿ��з�Ƶ��ģʽ��
	 *          ��Ϊ����apriori���ʣ���Ƶ��ģʽ�ĳ���һ������Ƶ��ģʽ��
	 *          ��ˣ��ڲ�����ѡƵ��ģʽ�Ĺ�����ɾ����Щ������Ƶ��ģʽ����
	 *          ���ٺ������ArrayList�Ĵ�����
	 * 
	 * �㷨˼�룺����k���ѡ����������е�k-1���Ӽ����ٱ���k-1��
	 *          Ƶ�����������������е�һ���Ӽ����򷵻�false.
	 * 
	 * 
	 * ����������substringOfDelOneChar���ڲ���k-1���Ӽ���
	 * 
	 * ������    str�ǲ�������е�һ���
	 *           preFreItemSet��k-1��Ƶ�����
	 */
	private static boolean has_infrequent_subset(String str, Map preFreItemSet){
		ArrayList tempAL = substringOfDelOneChar(str);
		for(int i = 0;i < tempAL.size();i++){
			if(!preFreItemSet.containsKey(tempAL.get(i).toString()))
				return true;
		}
		return false;
	}
	

	/* �㷨���ã�����substringOfDelOneChar�Ǻ���has_infrequent_subset�ĸ���������
	 *          �������Ǵ�k��в�������k-1���Ӽ���
	 * 
	 * �㷨˼�룺���ģʽ����"1|10|24",�������ַ���ֱ��ȥ����ͺ����"|"
	 *          ���������ַ�����һ�����ַ��滻�����֮ǰ��"|"��
	 * 
	 * ������    str������"1|10|24"���
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
