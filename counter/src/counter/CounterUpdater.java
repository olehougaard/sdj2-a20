package counter;

public class CounterUpdater implements Runnable {
    private final Counter counter;
    private final int updates;

    public CounterUpdater(String name, Counter counter, int updates) {
        this.counter = counter;
        this.updates = updates;
    }

    @Override
    public void run() {
        for(int i = 0; i < updates; i++) {
        	synchronized(counter) {
        		counter.inc();
        	}
        }
    }
}
