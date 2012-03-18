package apriori;

import java.util.ArrayList;
import java.util.StringTokenizer;

/*��SubSet��װ�˺���subSet(char[])
 *�ú�������������ַ�������Ӽ��������˼���ܶ�������������Ϊn�ļ���
 *����2��n���ݸ��Ӽ���ˣ����һ���������ַ������С��ͬ��int�ͱ����
 *�顣����ÿλֻȡ0��1����ֵ��0ʱ����ʾ��λ��1ʱ��ʾ��λ��
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
		result.remove(0);//��Ҫ�ռ�
		result.remove(result.size() - 1);//��Ҫȫ��
		return result;
	}
	
	/*����pow(int, int)ֻ������������n���ݣ���һ������Ϊ������
	 *�ڶ�������Ϊ������
	 * */
	private static int pow(int num1, int num2){
		int result = 1;
		for(int i = 0;i < num2;i++)
			result *= num1;
		return result;
	}
	
	/*����changeBoolNumber(int[])���ڸı�����boolNumber��λ��ֵ��
	 *ÿ�������һ���Ӽ�������øú���һ�������趨�������趨ʱ����
	 *��1�ĳ�0������һ��������0�ĳ�1��
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
