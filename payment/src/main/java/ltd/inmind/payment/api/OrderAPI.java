package ltd.inmind.payment.api;

import ltd.inmind.common.HTTP;
import ltd.inmind.common.SerialUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.util.List;

@Component
public class OrderAPI {

    @Value("${api.order.service}")
    private String orderService;

    private static final String GET = "/order/%s/bytes?account=%s";
    private static final String PAID = "/callback/paid/%s?account=%s";

    public Order get(String id, String account) {

        String url = String.format(GET, id, account);

        try {
            final HttpResponse<byte[]> resp = HTTP.GETBytes(orderService, url);

            if (resp.statusCode() != 200)
                throw new RuntimeException("service call failed.");

            return SerialUtil.fromBytes(resp.body());
        } catch (IOException | InterruptedException | ClassNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    public void paid(String id, String account) {
        final String url = String.format(PAID, id, account);
        try {
            final HttpResponse<String> resp = HTTP.POST(orderService, url);

            if (resp.statusCode() != 200)
                throw new RuntimeException(resp.body());

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public record Order(
            String id,
            List<Inventory> inventories,
            String account,
            Instant createTime,
            Long status // 1 created, 2 paid, 3 transferring, 4 done, 0 canceled
    ) {

        public Double summary() {
            if (inventories == null) return 0D;
            return inventories.stream()
                    .mapToDouble(inventory -> inventory.count() * inventory.price())
                    .sum();
        }

    }

    public record Inventory(Long productId, Long count, Double price) {
    }




}
