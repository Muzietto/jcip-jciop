package net.faustinelli.concurrent.parallel.sumOfLongs;

import java.util.stream.Stream;

/**
 * Created by Marco Faustinelli (Muzietto) on 01/04/2016.
 */
public class Main {

    public static void main(String[] args) {

        long goal = 100000000L;

        long start = System.nanoTime();

        System.out.println(sequentialSum(goal));

        long end = (System.nanoTime() - start) / 1_000_000;
        System.out.println("sequential completed in msec: " + end);

        start = System.nanoTime();

        System.out.println(parallelSum(goal));

        end = (System.nanoTime() - start) / 1_000_000;
        System.out.println("parallel completed in msec: " + end);
    }

    private static long parallelSum(long l) {
        return Stream.iterate(1L, i -> i + 1)
                .limit(l)
                .parallel()
                .reduce(0L, Long::sum);
    }

    private static long sequentialSum(long l) {
        return Stream.iterate(1L, i -> i + 1)
                .limit(l)
                .reduce(0L, Long::sum);
    }

}
