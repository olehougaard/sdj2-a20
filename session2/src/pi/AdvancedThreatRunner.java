package pi;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Only for the curious
public class AdvancedThreatRunner {
    private static final ExecutorService executor = Executors.newFixedThreadPool(4);

    public static void start(Runnable runnable) {
        executor.execute(runnable);
    }
}
