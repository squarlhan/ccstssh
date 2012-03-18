package apriori;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/*
 * ��FormatInput�����ʽ�����룬ʹ���������ݿ�����ʽ�޹�
 * ��ǿ�㷨��ͨ���ԡ�
 * 
 * ����fromNumToStrMap��һ��Map�����ݽṹ�����ڱ��������ַ�������ƷID�ַ�����ӳ�䡣
 * ��key�������ַ�����object����ƷID�ַ���
 */
public class FormatInput {
	private static Map fromNumToStrMap = new LinkedHashMap();
	
	/*
	 * �㷨���ã�����getFormatedInput�ǽ���ƷIDӳ��ɴ�1��n������
	 * �㷨˼�룺���÷���DBPool.connectDB�������ݿ⣻
	 *          ���÷���mapFromArrayList��formatInput�����������ݿ����ת����
	 */
	public static ArrayList getFormatedInput(String ip, String db, String username, String password, String car){
		ArrayList result = DBPool.connectDB(ip, db, username, password, car);
		Map map = mapFromArrayList(result, fromNumToStrMap);
		formatInput(result, map);
		System.out.println("ת�����ǣ�");
		System.out.println(result);
		return result;
	}
	/*
	 * �㷨���ã�����mapFromArrayList�����Ǳ�����ƷID���ݿ⣨arrayList�������ض�Ӧ��ӳ�䣨map��
	 * 
	 * �㷨˼�룺���屣��ӳ������ݽṹresult�������������ݣ�arrayList����ÿһ��Ԫ�أ����result�в�
	 *          ������Ԫ�أ�����Ԫ�����Ӧ�����֣�num��������ӵ�result��ȥ��
	 *          Ϊ���㽫����ӳ�����ƷID�����������̵�ͬʱ������Ӧ���������ӦԪ����ӵ�map�С�
	 * ������   arrayList�洢��ƷID��һ����Ԫ��Ӧһ��ArrayList����ArrayList�洢��arrayList1��
	 *                   ���Ӧ���û����������Ʒ��
	 *          map ��һ����FormatInput��˽������fromNumToStrMap��
	 */
	private static Map mapFromArrayList(ArrayList arrayList, Map map){
		Map result = new LinkedHashMap();
		int num = 1;
		for(int i = 0;i < arrayList.size();i++){
			ArrayList tempAL = (ArrayList)arrayList.get(i);
			for(int j = 0;j < tempAL.size();j++ ){
				String tempStr = tempAL.get(j).toString();
				if(!result.containsKey(tempStr)){
					result.put(tempStr, num);
					map.put(num + "", tempStr);
					num++;
				}
			}
		}
		return result;
	}
	
	/*
	 * �㷨���ã�����formatInput�Ǹ�����ƷID�Լ�����������ֵ�ӳ���map��������Ʒ
	 *          ID�޸ĳ���Ӧ�����֡�
	 *          
	 * �㷨˼�룺������ƷID���ݿ⣨arrayList����ÿһ��Ԫ�أ���ӳ���map���в������Ӧ
	 *          �����֣�Ȼ�����Ϊ�����֡�
	 * ������    arrayList�洢��ƷID��һ����Ԫ��Ӧһ��ArrayList����ArrayList�洢��arrayList1��
	 *                    ���Ӧ���û����������Ʒ��
	 *           map����ƷID�����ֵ�ӳ�����key����ƷID��object�Ƕ�Ӧ���֡�
	 *      
	 */
	private static void formatInput(ArrayList arrayList, Map map){
		for(int i = 0;i < arrayList.size();i++){
			ArrayList tempAL = (ArrayList)arrayList.get(i);
			for(int j = 0;j < tempAL.size();j++ ){
				String tempStr = tempAL.get(j).toString();
				tempAL.set(j, map.get(tempStr).toString());
			}
		}
	}
	public static Map getFromNumToStrMap() {
		return fromNumToStrMap;
	}

}
