package ltd.inmind.bag.api;

import com.google.gson.Gson;
import ltd.inmind.common.HTTP;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;

@Component
public class OrderAPI {

    @Value("${api.order.service}")
    private String orderService;

    static final String create = "/order/create";

    static final Gson gson = new Gson();

    public void create(List<Inventory> inventories, String account) {
        try {

            String url = create.concat(String.format("?account=%s", account));

            final HttpResponse<String> resp = HTTP.POST(orderService, url, gson.toJson(inventories));

            if (resp.statusCode() != 200)
                throw new RuntimeException(resp.body());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public record Inventory(Long productId, Long count, Double price) {

    }
}
