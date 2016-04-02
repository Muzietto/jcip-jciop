package net.faustinelli.earthquakes;

import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;

import java.io.IOException;
import java.io.InputStream;

public class Main {
    public static void main(String args[]) throws IOException {

        HttpTransport transport = new NetHttpTransport();
        GenericUrl url = new GenericUrl("http://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2016-04-02&endtime=2016-04-03");

        HttpRequestFactory factory = transport.createRequestFactory();
        HttpRequest request = factory.buildGetRequest(url);
        request.getHeaders().setAccept("application/json");

        HttpResponse response = request.execute();
        InputStream stream = response.getContent();

        int data;
        while((data = stream.read()) > 0) {
            System.out.write(data);
        }
    }
}
