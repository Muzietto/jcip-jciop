package net.faustinelli.lift.movement;

import net.faustinelli.lift.queue.Floor;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Move {
    
    private Floor floor;

    private ScheduledExecutorService scheduler;

    public Move() {
        this(Executors.newScheduledThreadPool(1));
    }

    public Move(ScheduledExecutorService scheduler) {
        this.scheduler = scheduler;
    }

    public Move(Floor aFloor) {
        this();
        this.floor = aFloor;
    }

    public CompletableFuture moveTo(Floor floor) {

        CompletableFuture cf = new CompletableFuture();

        Callable fut = new Callable() {
            @Override
            public Object call() throws Exception {
                System.out.println("Scheduled Task says end TimeMillis=" + System.currentTimeMillis());
                cf.complete(null);
                return new Object();
            }
        };

        FutureTask ftask = new FutureTask(fut);

        scheduler.schedule(ftask, 2500, TimeUnit.MILLISECONDS);
        return cf;
    }
}
