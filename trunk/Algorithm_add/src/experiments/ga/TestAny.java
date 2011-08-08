package experiments.ga;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import experiments.Matrix;
import experiments.apga.APGA;
import experiments.apga.clustObjectFun;

public class TestAny {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		long startTime = System.currentTimeMillis();
		APGA a1 = new APGA();
		int m = 40;
		int n = 30;
		Matrix consValue = new Matrix(2, n);
		Matrix lastPos = new Matrix(m, n);
		Matrix pBest = new Matrix(1, m);
		for(int i = 0; i<=n-1;i++ ){
			consValue.data[0][i] = -10;
			consValue.data[1][i] = 10;
		}
		for(int i = 0; i<=m-1;i++ ){
			for(int j = 0; j<=n-1;j++ ){
				lastPos.data[i][j] = Math.random()*20-10;
//				lastPos.data[i][j] = 0.0;
			}
		}
		for(int i = 0; i<=m-1; i++){
			double total = 0;
			for(int j = 0; j<=n-1; j++){
//				total+=(lastPos.data[i][j]*lastPos.data[i][j]-10*Math.cos(2*lastPos.data[i][j]*Math.PI)+10);
				total+=Math.pow(lastPos.data[i][j], 2.0);
			}
//			pBest.data[0][i] = n*111-total;
			pBest.data[0][i] = n*Math.pow(10, 2.0)-total;
		}
		a1.Calculate(new MaxFunction(), 0.3, 0.3, 100.0, 0.8, 0.7, consValue, lastPos, pBest, 200);
		//a1.runapgaexample();

		long endTime = System.currentTimeMillis();
		System.out.println("运行时间 " + (endTime - startTime) + "ms");
		System.out.println("符合模式：" + clustObjectFun.mycount);
	}

}
