package net.faustinelli.earthquakes.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import com.sun.org.apache.xerces.internal.impl.dv.xs.DateDV;

import java.util.Date;
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

        @Key
        private Geometry geometry;

        public static class Geometry {
            @Key
            private double[] coordinates;

            public Location getCoordinates() {
                return Location.fromArray(coordinates);
            }

            @Override
            public String toString() {
                return getCoordinates().toString();
            }
        }

        public static class Properties {
            @Key
            private String url;

            @Key
            private String place;

            @Key
            private Long time;

            @Key
            private Long updated;

            @Key
            private Integer tz;



            public String getUrl() {
                return url;
            }

            public String getPlace() {
                return place;
            }

            public Date getTime() {
                return new Date(time);
            }

            public Date getUpdated() {
                return new Date(updated);
            }

            public Integer getTz() {
                return tz;
            }

            @Override
            public String toString() {
                return getUrl() + ", " + getPlace() + ", " + getTime() + ", " + getUpdated() + ", " + getTz();
            }
        }

        public String getId() {
            return id;
        }

        public Geometry getGeometry() {
            return geometry;
        }

        public Properties getProperties() {
            return properties;
        }

        @Override
        public String toString() {
            return "Properties: " + getProperties() + ", geometry: " + getGeometry();
        }
    }
}


