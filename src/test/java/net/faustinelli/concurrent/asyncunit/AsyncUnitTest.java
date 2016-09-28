package net.faustinelli.concurrent.asyncunit;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class AsyncUnitTest {

    @Test
    public void testWaitNotifyAssert() throws InterruptedException {

        Long start = System.currentTimeMillis();
        System.out.println("Test thread says start TimeMillis=" + start);

        MyCallback cb = new MyCallback();
        assertFalse(cb.executed);

        // clunky solution to move the work on another thread and introduce some delay
        CompletableFuture cf = new CompletableFuture();
        FutureTask ftask = new FutureTask(() -> {
            System.out.println("Scheduled Task says end TimeMillis=" + System.currentTimeMillis());
            // do any sort of useful job and modify cb's state accordingly
            cb.execute();
            cf.complete(null);
            return "whatever";
        });

        Executors.newScheduledThreadPool(1).schedule(ftask, 500, TimeUnit.MILLISECONDS);
        cf.thenRun(() -> {
            cb.onDone();
        });

        synchronized (cb) {
            cb.wait();
            // put here assertions about cb state
            assertTrue(cb.executed);
        }
        Long end = System.currentTimeMillis();
        System.out.println("Test thread says end TimeMillis=" + end);
        System.out.println("Elapsed time is millis=" + (end - start));
    }

    public class MyCallback {

        public Boolean executed = Boolean.FALSE;

        public void execute() {
            System.out.println("Callback says TimeMillis=" + System.currentTimeMillis());
            executed = true;
        }

        public void onDone() {
            synchronized (this) {
                notifyAll();
            }
        }
    }
}
