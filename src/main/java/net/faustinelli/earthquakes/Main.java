/*
 * Project: jcip-jciop
 * Author: Marco Faustinelli - Muzietto (contacts@faustinelli.net), Davide Bellettini (http://about.bellettini.eu/)
 * Web: http://faustinelli.wordpress.com/, http://www.github.com/muzietto, http://faustinelli.net/
 * Version: 1.0
 * The GPL 3.0 License - Copyright (c) 2015-2016 - The jcip-jciop Project
 */

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
