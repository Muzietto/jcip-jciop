/*
 * Project: jcip-jciop
 * Author: Marco Faustinelli - Muzietto (contacts@faustinelli.net)
 * Web: http://faustinelli.wordpress.com/, http://www.github.com/muzietto, http://faustinelli.net/
 * Version: 1.0
 * The GPL 3.0 License - Copyright (c) 2015-2017 - The jcip-jciop Project
 */

package net.faustinelli.concurrent.junit_future;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static junit.framework.Assert.assertTrue;

/**
 * Created by Marco Faustinelli (Muzietto) on 23/03/2017.
 */
public class TestingFutures {

    @Test
    public void testAssertionInsideContinuation() throws InterruptedException {

        Long now = System.currentTimeMillis();
        System.out.println("start TimeMillis=" + now);

        CompletableFuture fut = new CompletableFuture();

        ScheduledExecutorService executorService =
                Executors.newScheduledThreadPool(1);
        executorService
                .schedule(() -> {
                    fut.complete("whatever");
                }, 500, TimeUnit.MILLISECONDS);

        fut.thenRun(() -> {
            long then = System.currentTimeMillis();
            System.out.println("end TimeMillis=" + then);
            assertTrue(then - now >= 500);
        });

        fut.join();
        //executorService.awaitTermination(1, TimeUnit.SECONDS);
    }
}