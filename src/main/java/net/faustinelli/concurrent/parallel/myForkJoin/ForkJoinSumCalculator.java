/*
 * Project: jcip-jciop
 * Author: Marco Faustinelli - Muzietto (contacts@faustinelli.net)
 * Web: http://faustinelli.wordpress.com/, http://www.github.com/muzietto, http://faustinelli.net/
 * Version: 1.0
 * The GPL 3.0 License - Copyright (c) 2015-2016 - The jcip-jciop Project
 */

package net.faustinelli.concurrent.parallel.myForkJoin;

import java.util.Random;
import java.util.concurrent.RecursiveTask;

/**
 * Created by Marco Faustinelli (Muzietto) on 04/04/2016.
 */
public class ForkJoinSumCalculator extends RecursiveTask<Integer> {
    private final int[] numbers;
    private final int start;
    private final int end;
    private final long THRESHOLD = 10000000L;

    public ForkJoinSumCalculator(int[] numbers) {
        this(numbers, 0, numbers.length);
    }

    public ForkJoinSumCalculator(int[] numbers, int start, int end) {
        this.numbers = numbers;
        this.start = start;
        this.end = end;
    }

    private int parallelSum() {
        if (this.end < this.THRESHOLD) {
            return sequentialSum();
        } else {
            int length = this.end - this.start;
            ForkJoinSumCalculator leftTask = new ForkJoinSumCalculator(numbers, this.start, this.start + length / 2);
            ForkJoinSumCalculator rightTask = new ForkJoinSumCalculator(numbers, (this.start + length / 2) , this.end);

            leftTask.fork();
            Integer rightResult = rightTask.compute();
            Integer leftResult = leftTask.join();
            return rightResult + leftResult;
        }
    }

    private int sequentialSum() {
        int result = 0;
        for (int counter = 0; counter < this.end; counter++) {
            result += this.numbers[counter];
        }
        return result;
    }

    @Override
    protected Integer compute() {
        return sequentialSum();
    }

    public static void main(String[] args) {
        Random rnd = new Random();
        long goal = 80000000L;
        int[] ints = new int[(int) goal];
        for (long counter = 0L; counter < goal; counter++) {
            ints[((int) counter)] = rnd.nextInt();
        }

        long start = System.nanoTime();

        System.out.println(new ForkJoinSumCalculator(ints).sequentialSum());

        long end = (System.nanoTime() - start) / 1_000_000;
        System.out.println("sequential completed in msec: " + end);

        start = System.nanoTime();

        System.out.println(new ForkJoinSumCalculator(ints).parallelSum());

        end = (System.nanoTime() - start) / 1_000_000;
        System.out.println("parallel completed in msec: " + end);

    }

}
