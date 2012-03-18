package apriori;

/*
 *��ProduceFreItemSet��װ�˹��з���find_frequent_1_item
 *��fromItemSetToFreItemSet�����У�find_frequent_1_item������
 *���������ݿ��в��ҷ�����С֧�ֶȵ�Ƶ��1-���
 *fromItemSetToFreItemSet���ڴӺ�k-����ҳ�Ƶ��k-�������
 *�ĺ�k-�����Apriori_gen���е�apriori_gen���������ġ�
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
	 * �㷨���ã�����find_frequent_1_item�Ǵ����������У��ҳ����е�
	 *          Ƶ��1�
	 * �㷨˼�룺ɨ��洢��ArrayList�е����ݿ����ݣ�������ֵ�Ƶ��(support)��
	 *          �������Ƶ�Ȳ�С��min_sup���򽫸����support����1-Ƶ����С�
	 *          
	 * ������   arrayList�Ǵ����ݿ���ת��ArrayList���������ݣ�
	 *          min_sup����С֧�ֶȡ�
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
	 * �㷨���ã��Ӻ�ѡ����ҳ�Ƶ�����
	 * �㷨˼�룺���ں�ѡ��е�ÿһ�������������񣬼�����֧�ֶȣ������֧�ֶȲ�С��
	 *          min_sup���������֧�ֶȷ���Ƶ����С�
	 * ����������isArrayListContainString
	 * 
	 * ������   arrayList�Ǵ����ݿ���ת��ArrayList���������ݣ�
	 *          itemset�Ǻ�ѡ���
	 *          min_sup����С֧�ֶȡ�
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
	 * �㷨���ã�����isArrayListContainString�����ж��������ݿ��е�ÿ�������Ƿ����
	 *          ��str���Ƿ���isArrayListContainString�ĸ���������
	 * �㷨˼�룺���ݿ��ÿһ��������Ϊһ��ArrayList�洢����һArrayList�У�����"|"����
	 *          str���в�֣�����ûһ����ֳ����ַ�������ÿһ�������ж����Ƿ��ڸ������С�
	 *          
	 * ������    arrayList�Ǵ����ݿ���ת��ArrayList���������ݣ�
	 *          str�Ǵ������
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
