package net.faustinelli.condition;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Produccer implements Runnable {

    private BoundedBuffer bb;
    private ScheduledExecutorService scheduler;

    public Produccer(BoundedBuffer bb, ScheduledExecutorService scheduler) {
        this.bb = bb;
        this.scheduler = scheduler;
    }

    @Override
    public void run() {
        while (true) {

            Long rnd = new Long(ThreadLocalRandom.current().nextInt(300, 1000 + 1));

            scheduler.schedule(() -> {
                try {
                    bb.put(new Object());
                    System.out.println(Thread.currentThread().getName() + ": Putting");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, rnd, TimeUnit.MILLISECONDS);

        }
    }
}
