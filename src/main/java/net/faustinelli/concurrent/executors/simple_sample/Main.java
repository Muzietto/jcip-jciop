package net.faustinelli.concurrent.executors.simple_sample;

import java.util.concurrent.*;

/**
 * Created by Marco Faustinelli (Muzietto) on 29/03/2016.
 */
public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService es1 = Executors.newFixedThreadPool(10);

        es1.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("Async task");
            }
        });

        Future<String> fut = es1.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                String result = "Async string";
                System.out.println(result);
                return result;
            }
        });

        System.out.println("gotten " + fut.get());

        es1.shutdown();

        System.out.println("there are " + Runtime.getRuntime().availableProcessors() + " cores.");
    }
}
