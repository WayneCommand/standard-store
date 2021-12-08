package ltd.inmind.bag.api;

import ltd.inmind.common.HTTP;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.http.HttpResponse;

@Component
public class StockAPI {

    @Value("${api.stock.service}")
    private String stockService;

    static final String clearance = "/stock/clearance";
    static final String ret = "/stock/return";


    public void clearance(Long productId, Long count) {
        try {

            String params = String.format("?productId=%s&count=%s", productId, count);

            HttpResponse<String> resp = HTTP.POST(stockService, clearance.concat(params));

            if (resp.statusCode() != 200)
                throw new RuntimeException(resp.body());


        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void ret(Long productId, Long count) {
        try {

            String params = String.format("?productId=%s&count=%s", productId, count);

            HttpResponse<String> resp = HTTP.POST(stockService, ret.concat(params));

            if (resp.statusCode() != 200)
                throw new RuntimeException("service call failed.");

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
