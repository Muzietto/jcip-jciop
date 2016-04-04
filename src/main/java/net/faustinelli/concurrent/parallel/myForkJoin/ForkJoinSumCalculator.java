/*
 * Project: jcip-jciop
 * Author: Marco Faustinelli - Muzietto (contacts@faustinelli.net)
 * Web: http://faustinelli.wordpress.com/, http://www.github.com/muzietto, http://faustinelli.net/
 * Version: 1.0
 * The GPL 3.0 License - Copyright (c) 2015-2016 - The jcip-jciop Project
 */

package net.faustinelli.concurrent.parallel.myForkJoin;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

/**
 * Created by Marco Faustinelli (Muzietto) on 04/04/2016.
 */
public class ForkJoinSumCalculator extends RecursiveTask<Long> {
    private final long[] numbers;
    private final int start;
    private final int end;
    public static final long THRESHOLD = 10_000;

    public ForkJoinSumCalculator(long[] numbers) {
        this(numbers, 0, numbers.length);
    }

    public ForkJoinSumCalculator(long[] numbers, int start, int end) {
        this.numbers = numbers;
        this.start = start;
        this.end = end;
    }

    private long sequentialSum() {
        long result = 0;
        for (int counter = 0; counter < this.end; counter++) {
            result += this.numbers[counter];
        }
        return result;
    }

    @Override
    protected Long compute() {
        int length = this.end - this.start;
        if (length <= this.THRESHOLD) {
            return sequentialSum();
        } else {
            ForkJoinSumCalculator leftTask = new ForkJoinSumCalculator(numbers, this.start, this.start + length / 2);
            ForkJoinSumCalculator rightTask = new ForkJoinSumCalculator(numbers, this.start + length / 2, this.end);

            leftTask.fork();
            Long rightResult = rightTask.compute();
            Long leftResult = leftTask.join();
            return rightResult + leftResult;
        }
    }

    public static void main(String[] args) {
        Random rnd = new Random();
        long goal = 4000000L;
        long[] longs = LongStream.rangeClosed(1, goal).toArray();

        long start = System.nanoTime();

        System.out.println(new ForkJoinSumCalculator(longs).sequentialSum());

        long end = (System.nanoTime() - start) / 1_000_000;
        System.out.println("sequential completed in msec: " + end);

        start = System.nanoTime();

        System.out.println(new ForkJoinPool().invoke(new ForkJoinSumCalculator(longs)));

        end = (System.nanoTime() - start) / 1_000_000;
        System.out.println("parallel completed in msec: " + end);
    }
}
