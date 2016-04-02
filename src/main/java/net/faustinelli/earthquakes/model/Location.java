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
