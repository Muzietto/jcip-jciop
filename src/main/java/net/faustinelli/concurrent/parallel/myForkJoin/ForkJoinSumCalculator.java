/*
 * Project: jcip-jciop
 * Author: Marco Faustinelli - Muzietto (contacts@faustinelli.net)
 * Web: http://faustinelli.wordpress.com/, http://www.github.com/muzietto, http://faustinelli.net/
 * Version: 1.0
 * The GPL 3.0 License - Copyright (c) 2015-2016 - The jcip-jciop Project
 */

package net.faustinelli.concurrent.parallel.myForkJoin;

import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Created by Marco Faustinelli (Muzietto) on 04/04/2016.
 */
public class ForkJoinSumCalculator {
    private final int[] numbers;
    private final int start;
    private final int end;
    private final long THRESHOLD = 10000;

    public ForkJoinSumCalculator(int[] numbers) {
        this(numbers, 0, numbers.length);
    }

    public ForkJoinSumCalculator(int[] numbers, int i, int length) {
        this.numbers = numbers;
        this.start = i;
        this.end = length;
    }

    public static void main(String[] args) {
        Random rnd = new Random();
        long goal = 100000000L;
        int[] ints = new int[(int) goal];
        for (long counter = 0L; counter < goal; counter++) {
            ints[((int) counter)] = rnd.nextInt();
        }

        long start = System.nanoTime();

        System.out.println(new ForkJoinSumCalculator(ints).sequentialSum(goal));

        long end = (System.nanoTime() - start) / 1_000_000;
        System.out.println("sequential completed in msec: " + end);

        start = System.nanoTime();

        System.out.println(new ForkJoinSumCalculator(ints).parallelSum(goal));

        end = (System.nanoTime() - start) / 1_000_000;
        System.out.println("parallel completed in msec: " + end);

    }

    private long parallelSum(long goal) {
        return 0;
    }

    private long sequentialSum(long goal) {
        return 0;
    }

}
