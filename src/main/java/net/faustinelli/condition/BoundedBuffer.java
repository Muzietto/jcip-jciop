package net.faustinelli.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class BoundedBuffer {

    final Lock lock = new ReentrantLock();
    final Condition notFull = lock.newCondition();
    final Condition notEmpty = lock.newCondition();

    final Object[] items = new Object[100];
    int putptr, takeptr, count;

    public void put(Object x) throws InterruptedException {
        lock.lock();
        try {
            if (count == 100) {
                System.out.println(Thread.currentThread().getName() + "<=====");
                count = 90;
            }
            while (count == items.length) {
                notFull.await();
            }
            items[putptr] = x;
            if (++putptr == items.length) {
                putptr = 0;
            }
            ++count;
            System.out.println(Thread.currentThread().getName() + ": " + xxx());
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public Object take() throws InterruptedException {
        lock.lock();
        try {
            if (count == 0) {
                System.out.println(Thread.currentThread().getName() + "=====>");
                count = 10;
            }
            while (count == 0) {
                notEmpty.await();
            }
            Object x = items[takeptr];
            if (++takeptr == items.length) {
                takeptr = 0;
            }
            --count;
            System.out.println(Thread.currentThread().getName() + ": " + xxx());
            notFull.signal();
            return x;
        } finally {
            lock.unlock();
        }
    }

    private String xxx() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i <= count; i++) {
            result.append("*");
        }
        return result.toString();
    }
}
