package counter;

public class TestCounter {
    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();
        CounterUpdater updater1 =
                new CounterUpdater("Updater1", counter, 20000);
        CounterUpdater updater2 =
                new CounterUpdater("Updater2", counter, 20000);
        Thread thread1 = new Thread(updater1);
        Thread thread2 = new Thread(updater2);

        thread1.start();
        thread2.start();
        
        thread1.join();
        thread2.join();

        System.out.println("Value = " + counter.getValue());
        System.out.println("Main Thread Ended");
    }
}
