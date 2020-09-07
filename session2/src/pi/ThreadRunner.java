package pi;

public class ThreadRunner {
    public static void start(Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.start();
    }
}
