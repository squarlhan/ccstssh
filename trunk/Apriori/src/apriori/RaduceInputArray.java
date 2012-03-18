package apriori;

/*
 * ��RaduceInputArray�����Ǹ���������������ݿ���вü�
 * ��ü�ԭ���ǣ�����������ݿ��е�һ�����񣬲������κ�
 * Ƶ����򽫸���������ݿ���ɾ��
 */
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
class RaduceInputArray {
	
	/*
	 * �㷨���ã�����Ƶ������������ݿ���вü�������㷨������Ч��
	 * �㷨˼�룺���������ÿһ���������ݣ�����䲻����Ƶ����������
	 *          ���ݿ�inputDB��ɾ�����
	 * ����������isArrayListContainFrequentItem
	 * 
	 * ������    inputDB�Ǵ����ݿ���ת��ArrayList���������ݣ�
	 *           preFreItemSet��Ƶ�����
	 */
	public static void reduceInputDB(ArrayList inputDB, Map preFreItemSet){
		for(int i = 0;i < inputDB.size();i++){
			ArrayList temp = (ArrayList)(inputDB.get(i));
			if(!isArrayListContainFrequentItem(temp, preFreItemSet))
				inputDB.remove(i);
		}
	}
	
	/*
	 * �㷨���ã��ж�һ�����������Ƿ������Ƶ���
	 * �㷨ԭ��������ProduceFreItemSet�е�isArrayListContainString��������Ƶ����е�ÿһ��
	 *          Ƶ��������жϣ�Ƶ�������"1|10|11"������ʽ���ַ�����
	 * ����������ProduceFreItemSet.isArrayListContainString
	 * 
	 * ������    arrayList��inputDB�е�ÿһ���������ݣ�
	 *           preFreItemSet��Ƶ�����
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
