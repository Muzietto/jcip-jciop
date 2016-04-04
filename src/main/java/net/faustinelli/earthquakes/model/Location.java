/*
 * Project: jcip-jciop
 * Author: Marco Faustinelli - Muzietto (contacts@faustinelli.net), Davide Bellettini (http://about.bellettini.eu/)
 * Web: http://faustinelli.wordpress.com/, http://www.github.com/muzietto, http://faustinelli.net/
 * Version: 1.0
 * The GPL 3.0 License - Copyright (c) 2015-2016 - The jcip-jciop Project
 */

package net.faustinelli.earthquakes.model;

public class Location {
    private final double latitude;

    private final double longitude;

    private Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static Location fromArray(double[] coordinates) {
        return new Location(coordinates[1], coordinates[0]);
    }

    @Override
    public String toString() {
        return "{" + latitude + "," + longitude + "}";
    }
}
