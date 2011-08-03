package experiments.ga;

import experiments.Matrix;

public class TestAny {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		long startTime = System.currentTimeMillis();
		APGATest a1 = new APGATest();
		int m = 40;
		int n = 2;
		Matrix consValue = new Matrix(2, n);
		Matrix lastPos = new Matrix(m, n);
		Matrix pBest = new Matrix(1, m);
		for(int i = 0; i<=n-1;i++ ){
			consValue.data[0][i] = -5.12;
			consValue.data[1][i] = 5.12;
		}
		for(int i = 0; i<=m-1;i++ ){
			for(int j = 0; j<=n-1;j++ ){
				lastPos.data[i][j] = Math.random()*10.24-5.12;
//				lastPos.data[i][j] = 0.0;
			}
		}
		for(int i = 0; i<=m-1; i++){
			double total = 0;
			for(int j = 0; j<=n-1; j++){
				total+=Math.pow(lastPos.data[i][j], 2.0);
			}
			pBest.data[0][i] = 2*Math.pow(5.12, 2.0)-total;
		}
		a1.Calculate(new MaxFunction(), 0.0, 0.0, 100.0, 0.7, 0.7, consValue, lastPos, pBest, 200);
		//a1.runapgaexample();

		long endTime = System.currentTimeMillis();
		System.out.println("运行时间 " + (endTime - startTime) + "ms");
	}

}
