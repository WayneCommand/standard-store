package ltd.inmind.bag.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class StockAPI {

    @Value("${api.stock.service}")
    private String stockService;

    static final String protocol = "http://";

    static final String clearance = "/stock/clearance";
    static final String ret = "/stock/return";


    public void clearance(Long productId, Long count) {
        try {

            String params = String.format("?productId=%s&count=%s", productId, count);

            HttpResponse<String> resp = request(clearance.concat(params));

            if (resp.statusCode() != 200)
                throw new RuntimeException(resp.body());


        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void ret(Long productId, Long count) {
        try {

            String params = String.format("?productId=%s&count=%s", productId, count);

            HttpResponse<String> resp = request(ret.concat(params));

            if (resp.statusCode() != 200)
                throw new RuntimeException("service call failed.");


        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public HttpResponse<String> request(String url) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder(URI.create(protocol.concat(stockService).concat(url)))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        return HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());
    }
}
