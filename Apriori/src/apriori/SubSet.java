package apriori;

import java.util.ArrayList;
import java.util.StringTokenizer;

/*类SubSet封装了函数subSet(char[])
 *该函数用于求给定字符数组的子集，其基本思想受二进制启发，幂为n的集合
 *共有2的n次幂个子集因此，设计一个与输入字符数组大小相同的int型标记数
 *组。其中每位只取0，1两种值，0时不显示该位，1时显示该位。
 * */
public class SubSet {
	public static ArrayList subSet(String item){
		String[] input = stringToArray(item);
		int size = input.length;
		int[] boolNumber = new int[size];
		for(int i = 0;i < size;i++)
			boolNumber[i] = 0;
		int temp = pow(2, size);
		ArrayList result = new ArrayList();
		for(int j = 0;j < temp;j++){
			int count = 0;
			String tempStr = "";
			for(int k = 0;k < size;k++){
				if(boolNumber[k] == 1){
					if(count == 0)
						tempStr = input[k];
					else
						tempStr = tempStr + "|" + input[k];
					count++;
				}
			}
			result.add(tempStr);
			changeBoolNumber(boolNumber);
			
		}
		result.remove(0);//不要空集
		result.remove(result.size() - 1);//不要全集
		return result;
	}
	
	/*函数pow(int, int)只用于求整数的n次幂，第一个参数为底数，
	 *第二个参数为幂数。
	 * */
	private static int pow(int num1, int num2){
		int result = 1;
		for(int i = 0;i < num2;i++)
			result *= num1;
		return result;
	}
	
	/*函数changeBoolNumber(int[])用于改变数组boolNumber各位的值，
	 *每次输出完一个子集，便调用该函数一次重新设定，重新设定时，遇
	 *到1改成0，将第一次遇到的0改成1。
	 * */
	private static void changeBoolNumber(int[] boolNumber){
		for(int i = 0;i < boolNumber.length;i++){
			if(boolNumber[i] == 1)
				boolNumber[i] = 0;
			else if(boolNumber[i] == 0){
				boolNumber[i] = 1;
				break;
			}
		}
	}
	
	private static String[] stringToArray(String str){
		ArrayList tempAL = new ArrayList();
		StringTokenizer tokenizer = new StringTokenizer(str, "|");
		while(tokenizer.hasMoreTokens()){
			tempAL.add(tokenizer.nextToken());
		}
		String[] result = new String[tempAL.size()];
		for(int i = 0;i < result.length;i++){
			result[i] = tempAL.get(i).toString();
		}
		return result;
	}
	
}
