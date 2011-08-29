package experiments.ga;

public class NoSence {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int time_delay = 1000;
		for (int i = 0; i <= 9; i++) {
			try {
				Thread.sleep(time_delay);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			System.out.println(i);
		}
//		System.out.println("Final!");

	}

}
