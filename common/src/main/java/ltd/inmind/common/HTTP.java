package ltd.inmind.common;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HTTP {

    static final String protocol = "http://";

    public static HttpResponse<String> GET(String service, String url) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder(URI.create(protocol.concat(service).concat(url)))
                .GET()
                .build();

        return HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());
    }

    public static HttpResponse<byte[]> GETBytes(String service, String url) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder(URI.create(protocol.concat(service).concat(url)))
                .GET()
                .build();

        return HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofByteArray());
    }

    public static HttpResponse<String> POST(String service,String url) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder(URI.create(protocol.concat(service).concat(url)))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        return HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());
    }

    public static HttpResponse<String> POST(String service, String url, String body) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder(URI.create(protocol.concat(service).concat(url)))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        return HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());
    }
}
