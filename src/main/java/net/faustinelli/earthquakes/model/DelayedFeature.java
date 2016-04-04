/*
 * Project: jcip-jciop
 * Author: Marco Faustinelli - Muzietto (contacts@faustinelli.net), Davide Bellettini (http://about.bellettini.eu/)
 * Web: http://faustinelli.wordpress.com/, http://www.github.com/muzietto, http://faustinelli.net/
 * Version: 1.0
 * The GPL 3.0 License - Copyright (c) 2015-2016 - The jcip-jciop Project
 */

package net.faustinelli.earthquakes.model;

import java.time.Clock;
import java.util.Date;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayedFeature implements Delayed {
    private final EarthquakeFeed.Feature feature;
    private final Clock clock;
    private final Date expiration;

    public DelayedFeature(EarthquakeFeed.Feature feature, Clock clock, Date expiration) {
        this.feature = feature;
        this.clock = clock;
        this.expiration = expiration;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(expiration.getTime() - clock.millis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        return expiration.compareTo(((DelayedFeature)o).expiration);
    }

    public EarthquakeFeed.Feature unwrap() {
        return feature;
    }
}
