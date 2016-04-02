package net.faustinelli.earthquakes.queue;

import net.faustinelli.earthquakes.model.DelayedFeature;

import java.util.concurrent.DelayQueue;

public class FeatureConsumer implements Runnable {
    private final DelayQueue<DelayedFeature> queue;

    public FeatureConsumer(DelayQueue<DelayedFeature> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println(queue.take().unwrap());
            } catch (InterruptedException e) {
            }
        }
    }
}
