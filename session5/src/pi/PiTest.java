package pi;

import java.math.BigDecimal;
import java.util.Random;

public class PiTest {
	private static class PiRunner implements Runnable {
		private PiService service;
		private int decimals;

		public PiRunner(PiService service, int decimals) {
			this.service = service;
			this.decimals = decimals;
			
		}

		@Override
		public void run() {
			BigDecimal pi = service.computePi(decimals);
			System.out.println(pi);
		}
	}
	
	public static void main(String[] args) {
		Random random = new Random();
		PiService service = new PiService();
		for(int i = 0; i < 100; i++) {
			PiRunner runner = new PiRunner(service, random.nextInt(100));
			Thread thread = new Thread(runner);
			thread.start();
		}
	}
}
