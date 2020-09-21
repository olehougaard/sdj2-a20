package pi;

import java.math.BigDecimal;

public class PiService {
    private final PiCache cache;

    public PiService() {
        this.cache = new PiCache();
    }

    public BigDecimal computePi(int decimals) {
        if (!cache.contains(decimals)) {
            ComputePi computer = new ComputePi(decimals);
            ThreadRunner.start(computer);
            // Wait how long?
            cache.put(decimals, computer.getPiValue());
        }
        return cache.getPiValue(decimals);
    }
}
