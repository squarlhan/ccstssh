package experiments;

/**
 * 随机数
 * @author GEO
 *
 */
public class Random {
	
	/**
	 * @param min 最小值
	 * @param max 最大值
	 * @return
	 */
	public static int[] randperm(int min, int max) {
		int num = max - min + 1;
		int[] permutation = new int [num];
		for (int  i = 0; i < num; i++) {
			permutation[i] = min + i;
		}
		
		for (int k = permutation.length - 1; k > 0; k--) {
		    int w = (int)Math.floor(Math.random() * (k+1));
		    int temp = permutation[w];
		    permutation[w] = permutation[k];
		    permutation[k] = temp;
		}
		
		return permutation;
	}

	public static int[] randperm(int n) {		
		return randperm(1, n);
	}
}
