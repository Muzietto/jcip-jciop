package net.faustinelli.earthquakes;

import com.google.api.client.http.javanet.NetHttpTransport;
import net.faustinelli.earthquakes.http.EarthquakeNetWatcher;
import net.faustinelli.earthquakes.model.DelayedFeature;
import net.faustinelli.earthquakes.model.EarthquakeFeed;
import net.faustinelli.earthquakes.queue.FeatureConsumer;
import net.faustinelli.earthquakes.queue.FeatureProducer;

import java.io.IOException;
import java.time.Clock;
import java.util.concurrent.DelayQueue;

public class Main {
    public static void main(String[] args) throws IOException {
        DelayQueue<DelayedFeature> queue = new DelayQueue<>();

        EarthquakeFeed earthquakeFeed = new EarthquakeNetWatcher(new NetHttpTransport()).fetchFeed();

        new Thread(new FeatureConsumer(queue)).start();
        new Thread(new FeatureProducer(queue, Clock.systemUTC(), earthquakeFeed)).start();
    }
}
