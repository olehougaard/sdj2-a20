package counter;

public class Counter {
    private long value = 0;

    public synchronized void inc() {
		this.value++;
    }

    public synchronized long getValue() {
        return value;
    }
}
