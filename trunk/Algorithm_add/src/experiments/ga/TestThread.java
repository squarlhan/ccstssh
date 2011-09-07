package experiments.ga;

import experiments.Matrix;
import experiments.ga.ThreadAckleyMaxFunction.AckleyFunction;

public class TestThread {
	
	public void runthread(int time_delay, int n, int[] results){
		Thread[] tts = new Thread[n];
		for(int i = 0; i<=n-1; i++){
			tts[i] = new AckleyFunction(time_delay,  i, results);
			tts[i].start();
			
			for(int j = 0; j<=n-1; j++){
				System.out.print(results[j]+"  ");
			}
			System.out.print("\n");
		}
		for(int j = 0; j<=n-1; j++){
			try {
				tts[j].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for(int j = 0; j<=n-1; j++){
			System.out.print("f"+results[j]+"  ");
		}
	}
	
	public static void main(String[] args){
		int time_delay = 10;
		int[] results = {6,6,6,6,6};
		TestThread tt = new TestThread();
		tt.runthread(time_delay, 5, results);
	}

	class AckleyFunction extends Thread {

		private int time_delay;
		private int[] results;
		private int i;

		public AckleyFunction(int time_delay, int i, int[] results) {
			super();
			this.time_delay = time_delay;
			this.i = i;
			this.results = results;
		}

		public void run() {
			try {
				sleep(time_delay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			results[i] = i;
			System.out.println("Thread" + i);

		}

	}

}
