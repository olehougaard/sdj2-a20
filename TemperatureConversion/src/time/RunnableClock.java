package time;

import view.TemperatureViewController;

import java.time.LocalTime;

public class RunnableClock implements Runnable {
    private final TemperatureViewController controller;

    public RunnableClock(TemperatureViewController controller) {
        this.controller = controller;
    }

    @Override
    public void run() {
        try {
            while (true) {
                LocalTime time = LocalTime.now();
                controller.showTime(time);
                Thread.sleep(1);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
