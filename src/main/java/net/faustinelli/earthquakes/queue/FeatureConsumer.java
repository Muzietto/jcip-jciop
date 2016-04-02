package net.faustinelli.earthquakes.queue;

import net.faustinelli.earthquakes.model.DelayedFeature;

import java.util.concurrent.DelayQueue;
import java.util.stream.Stream;

public class FeatureConsumer implements Runnable {
    private final DelayQueue<DelayedFeature> queue;

    public FeatureConsumer(DelayQueue<DelayedFeature> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        Stream
                .generate(() -> {
                    try {
                        return queue.take();
                    } catch (InterruptedException e) {
                        return null;
                    }
                })
                .filter(x -> x != null)
                .map(x -> x.unwrap())
                .forEach(x -> System.out.println(x));
    }
}
