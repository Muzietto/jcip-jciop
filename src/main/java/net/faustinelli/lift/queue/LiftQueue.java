package net.faustinelli.lift.queue;

import java.util.PriorityQueue;

public class LiftQueue<T extends Comparable> {

    private PriorityQueue<T> queue;

    public LiftQueue() {
        this(new PriorityQueue<T>());
    }

    public LiftQueue(PriorityQueue<T> queue) {
        this.queue = queue;
    }

    public Integer size() {
        return queue.size();
    }

    public Boolean push(T t) {
        return queue.add(t);
    }

    public T pop() {
        return queue.poll();
    }
}
