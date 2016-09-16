package net.faustinelli.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SOExample {

    private volatile boolean usedData = true; // mutex for data
    private final Lock lock = new ReentrantLock();
    private final Condition isEmpty = lock.newCondition();
    private final Condition isFull = lock.newCondition();
    private int data;

    public void setData(int data) throws InterruptedException {
        lock.lock();
        try {
            while (!usedData) { // wait for data to be used
                isEmpty.await();
            }
            this.data = data;
            isFull.signal(); // broadcast that the data is now full.
            usedData = false; // tell others I created new data.
        } finally {
            lock.unlock(); // interrupt or not, release lock
        }
    }

    public void getData() throws InterruptedException {
        lock.lock();
        try {
            while (usedData) { // usedData is lingo for empty
                isFull.await();
            }
            isEmpty.signal(); // tell the producers to produce some more.
            usedData = true; // tell others I have used the data.
        } finally { // interrupted or not, always release lock
            lock.unlock();
        }
    }
}
