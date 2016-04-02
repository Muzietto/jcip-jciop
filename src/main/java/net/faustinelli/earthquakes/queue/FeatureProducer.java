package net.faustinelli.earthquakes.queue;

import net.faustinelli.earthquakes.model.DelayedFeature;
import net.faustinelli.earthquakes.model.EarthquakeFeed;

import java.time.Clock;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.DelayQueue;

/**
 * Created by davide on 02/04/16.
 */
public class FeatureProducer implements Runnable {
    private final DelayQueue<DelayedFeature> queue;
    private final Clock clock;
    private final EarthquakeFeed earthquakeFeed;

    public FeatureProducer(DelayQueue<DelayedFeature> queue, Clock clock, EarthquakeFeed earthquakeFeed) {
        this.queue = queue;
        this.clock = clock;
        this.earthquakeFeed = earthquakeFeed;
    }

    @Override
    public void run() {
        Random random = new Random();
        earthquakeFeed.getFeatures().stream().forEach(x -> {
            int size = queue.size();
            int delaySeconds = size * 5 + random.nextInt(4);
            long delay = delaySeconds * 1000L;

            queue.put(new DelayedFeature(x, clock, new Date(clock.millis() + delay)));
        });
    }
}
