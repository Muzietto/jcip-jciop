package net.faustinelli.earthquakes.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

import java.util.List;

public class EarthquakeFeed {
    @Key
    private List<Feature> features;

    @Key
    private Metadata metadata;

    public List<Feature> getFeatures() {
        return features;
    }

    public static class Metadata {
        @Key
        private Long generated;

        @Key
        private String url;

        @Key
        private String title;

        @Key
        private Integer status;

        @Key
        private String api;

        @Key
        private Integer count;
    }

    public static class Feature extends GenericJson {

        @Key
        private String id;


    }
}


