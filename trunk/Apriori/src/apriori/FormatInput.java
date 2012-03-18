package apriori;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/*
 * 类FormatInput负责格式化输入，使输入与数据库具体格式无关
 * 增强算法的通用性。
 * 
 * 参数fromNumToStrMap是一个Map型数据结构，用于保存数字字符串与商品ID字符串的映射。
 * 其key是数字字符串，object是商品ID字符串
 */
public class FormatInput {
	private static Map fromNumToStrMap = new LinkedHashMap();
	
	/*
	 * 算法作用：方法getFormatedInput是将商品ID映射成从1－n的数字
	 * 算法思想：调用方法DBPool.connectDB连接数据库；
	 *          调用方法mapFromArrayList和formatInput来对输入数据库进行转换。
	 */
	public static ArrayList getFormatedInput(String ip, String db, String username, String password, String car){
		ArrayList result = DBPool.connectDB(ip, db, username, password, car);
		Map map = mapFromArrayList(result, fromNumToStrMap);
		formatInput(result, map);
		System.out.println("转换后是：");
		System.out.println(result);
		return result;
	}
	/*
	 * 算法作用：方法mapFromArrayList作用是遍历商品ID数据库（arrayList），返回对应的映射（map）
	 * 
	 * 算法思想：定义保存映射的数据结构result，遍历输入数据（arrayList）的每一个元素，如果result中不
	 *          包含该元素，将该元素与对应的数字（num＋＋）添加到result中去。
	 *          为方便将数字映射回商品ID，在上述过程的同时，将对应的数字与对应元素添加到map中。
	 * 参数：   arrayList存储商品ID，一个单元对应一个ArrayList，此ArrayList存储与arrayList1中
	 *                   相对应的用户所购买的商品。
	 *          map 是一个类FormatInput的私有属性fromNumToStrMap。
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
	 * 算法作用：方法formatInput是根据商品ID以及其与具体数字的映射表（map），将商品
	 *          ID修改成相应的数字。
	 *          
	 * 算法思想：对于商品ID数据库（arrayList）的每一个元素，在映射表（map）中查找其对应
	 *          的数字，然后将其改为该数字。
	 * 参数：    arrayList存储商品ID，一个单元对应一个ArrayList，此ArrayList存储与arrayList1中
	 *                    相对应的用户所购买的商品。
	 *           map是商品ID与数字的映射表，其key是商品ID，object是对应数字。
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
