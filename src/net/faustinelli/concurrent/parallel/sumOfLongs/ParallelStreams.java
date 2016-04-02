package net.faustinelli.concurrent.parallel.sumOfLongs;

import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.*;

public class ParallelStreams {

    public static void main(String[] args) {
        long goal = 10000000L;

        long start = System.nanoTime();
        System.out.println(sequentialSum(goal));
        long end = (System.nanoTime() - start) / 1_000_000;
        System.out.println("sequential completed in msec: " + end);

        start = System.nanoTime();
        System.out.println(parallelSum(goal));
        end = (System.nanoTime() - start) / 1_000_000;
        System.out.println("parallel completed in msec: " + end);

        start = System.nanoTime();
        System.out.println(iterativeSum(goal));
        end = (System.nanoTime() - start) / 1_000_000;
        System.out.println("iterativeSum completed in msec: " + end);

        start = System.nanoTime();
        System.out.println(rangedSum(goal));
        end = (System.nanoTime() - start) / 1_000_000;
        System.out.println("rangedSum completed in msec: " + end);

        start = System.nanoTime();
        System.out.println(parallelRangedSum(goal));
        end = (System.nanoTime() - start) / 1_000_000;
        System.out.println("parallelRangedSum completed in msec: " + end);

        start = System.nanoTime();
        System.out.println(sideEffectSum(goal));
        end = (System.nanoTime() - start) / 1_000_000;
        System.out.println("sideEffectSum completed in msec: " + end);

        start = System.nanoTime();
        System.out.println(sideEffectParallelSum(goal));
        end = (System.nanoTime() - start) / 1_000_000;
        System.out.println("sideEffectParallelSum completed in msec: " + end);

        start = System.nanoTime();
        System.out.println(sideEffectParallelSumAtomic(goal));
        end = (System.nanoTime() - start) / 1_000_000;
        System.out.println("sideEffectParallelSumAtomic completed in msec: " + end);
    }

    public static long iterativeSum(long n) {
        long result = 0;
        for (long i = 0; i <= n; i++) {
            result += i;
        }
        return result;
    }

    public static long sequentialSum(long n) {
        return Stream.iterate(1L, i -> i + 1)
                .limit(n)
                .reduce(Long::sum)
                .get();
    }

    public static long parallelSum(long n) {
        return Stream.iterate(1L, i -> i + 1)
                .limit(n)
                .parallel()
                .reduce(Long::sum)
                .get();
    }

    public static long rangedSum(long n) {
        return LongStream.rangeClosed(1, n)
                .reduce(Long::sum)
                .getAsLong();
    }

    public static long parallelRangedSum(long n) {
        return LongStream.rangeClosed(1, n)
                .parallel()
                .reduce(Long::sum)
                .getAsLong();
    }

    public static long sideEffectSum(long n) {
        Accumulator accumulator = new Accumulator();
        LongStream.rangeClosed(1, n)
                .forEach(accumulator::add);
        return accumulator.total;
    }

    public static long sideEffectParallelSum(long n) {
        Accumulator accumulator = new Accumulator();
        LongStream.rangeClosed(1, n)
                .parallel()
                .forEach(accumulator::add);
        return accumulator.total;
    }

    public static long sideEffectParallelSumAtomic(long n) {
        AtomicLong accumulator = new AtomicLong();
        LongStream.rangeClosed(1, n)
                .parallel()
                .forEach(accumulator::addAndGet);
        return accumulator.get();
    }

    public static class Accumulator {
        private long total = 0;
        public synchronized void add(long value) {
            total += value;
        }
    }
}
