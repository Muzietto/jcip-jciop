package net.faustinelli.earthquakes.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

import java.util.List;
import java.util.Properties;

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

        public Long getGenerated() {
            return generated;
        }

        public String getUrl() {
            return url;
        }

        public String getTitle() {
            return title;
        }

        public Integer getStatus() {
            return status;
        }

        public String getApi() {
            return api;
        }

        public Integer getCount() {
            return count;
        }
    }

    public static class Feature extends GenericJson {

        @Key
        private String id;

        @Key
        private Properties properties;

        public static class Properties {
            @Key
            private String url;

            @Key
            private String place;

            public String getUrl() {
                return url;
            }

            public String getPlace() {
                return place;
            }

            @Override
            public String toString() {
                return getUrl() + ", " + getPlace();
            }
        }

        public String getId() {
            return id;
        }

        public Properties getProperties() {
            return properties;
        }
    }
}


