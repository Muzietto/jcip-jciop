package net.faustinelli.condition;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class ConditionTest {

    public static void main(String[] args) {

        BoundedBuffer bb = new BoundedBuffer();

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

        Produccer pp = new Produccer(bb, scheduler);
        Consummer cc = new Consummer(bb, scheduler);

        new Thread(pp).start();
        new Thread(cc).start();

    }
}
