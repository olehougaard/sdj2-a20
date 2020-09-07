package pi;

import java.math.BigDecimal;
import java.util.Random;

public class PiServiceTest {
    private static class PiServiceTester implements Runnable {
        private final PiService service;
        private final int decimals;

        public PiServiceTester(PiService service, int decimals) {
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
        for(int i = 0; i < 100; i ++) {
            PiServiceTester tester = new PiServiceTester(service, random.nextInt(50));
            Thread thread = new Thread(tester);
            thread.start();
        }
    }
}
