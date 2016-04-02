package net.faustinelli.earthquakes.http;

import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.gson.GsonFactory;
import net.faustinelli.earthquakes.model.EarthquakeFeed;

import java.io.IOException;

public class EarthquakeNetWatcher {

    private final HttpTransport transport;

    public EarthquakeNetWatcher(HttpTransport transport) {
        this.transport = transport;
    }

    public EarthquakeFeed fetchFeed() throws IOException {
        GenericUrl url = new GenericUrl("http://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2016-04-02&endtime=2016-04-03");

        HttpRequestFactory factory = transport.createRequestFactory();
        HttpRequest request = factory.buildGetRequest(url);
        request.getHeaders().setAccept("application/json");
        request.setParser(new JsonObjectParser(GsonFactory.getDefaultInstance()));

        HttpResponse response = request.execute();
        return response.parseAs(EarthquakeFeed.class);
    }
}
