package threads;

public class SimpleThread {
    private static class Counter implements Runnable {
        private int i;

        public Counter() {
            i = 0;
        }

        public void run() {
            for(; i < 1000; i++) {
                System.out.println(i);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();
        Thread thread1 = new Thread(counter);
        Thread thread2 = new Thread(counter);
        thread1.start();
        thread2.start();
        System.out.println("Hello, World!");
        thread1.join();
        thread2.join();
        System.out.println("Program end");
    }
}
