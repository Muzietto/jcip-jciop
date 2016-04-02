package net.faustinelli.earthquakes;

import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.gson.GsonFactory;
import net.faustinelli.earthquakes.model.DelayedFeature;
import net.faustinelli.earthquakes.model.EarthquakeFeed;

import java.io.IOException;
import java.io.InputStream;
import java.time.Clock;
import java.util.Date;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;

public class Main {
    public static void main1(String args[]) throws IOException {

        HttpTransport transport = new NetHttpTransport();
        GenericUrl url = new GenericUrl("http://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2016-04-02&endtime=2016-04-03");

        HttpRequestFactory factory = transport.createRequestFactory();
        HttpRequest request = factory.buildGetRequest(url);
        request.getHeaders().setAccept("application/json");

        HttpResponse response = request.execute();
        InputStream stream = response.getContent();

        int data;
        while ((data = stream.read()) > 0) {
            System.out.write(data);
        }
    }

    public static void main2(String args[]) throws IOException {
        HttpTransport transport = new NetHttpTransport();
        GenericUrl url = new GenericUrl("http://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2016-04-02&endtime=2016-04-03");

        HttpRequestFactory factory = transport.createRequestFactory();
        HttpRequest request = factory.buildGetRequest(url);
        request.getHeaders().setAccept("application/json");
        request.setParser(new JsonObjectParser(GsonFactory.getDefaultInstance()));

        HttpResponse response = request.execute();
        EarthquakeFeed earthquakeFeed = response.parseAs(EarthquakeFeed.class);

        System.out.println(earthquakeFeed.getFeatures().get(0).toString());
    }

    public static void main(String[] args) throws IOException {
        DelayQueue<DelayedFeature> queue = new DelayQueue<>();

        HttpTransport transport = new NetHttpTransport();
        GenericUrl url = new GenericUrl("http://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2016-04-02&endtime=2016-04-03");

        HttpRequestFactory factory = transport.createRequestFactory();
        HttpRequest request = factory.buildGetRequest(url);
        request.getHeaders().setAccept("application/json");
        request.setParser(new JsonObjectParser(GsonFactory.getDefaultInstance()));

        HttpResponse response = request.execute();
        EarthquakeFeed earthquakeFeed = response.parseAs(EarthquakeFeed.class);

        Clock clock = Clock.systemUTC();
        // consumer

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        System.out.println(queue.take().unwrap());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        Random random = new Random();

        earthquakeFeed.getFeatures().stream().forEach(x -> {
            int size = queue.size();
            int delaySeconds = size * 5 + random.nextInt(4);
            long delay = delaySeconds * 1000L;

            queue.put(new DelayedFeature(x, clock, new Date(clock.millis() + delay)));
        });
    }
}
