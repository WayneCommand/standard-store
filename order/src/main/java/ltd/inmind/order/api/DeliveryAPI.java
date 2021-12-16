package ltd.inmind.order.api;

import ltd.inmind.common.HTTP;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.http.HttpResponse;

@Component
public class DeliveryAPI {

    @Value("${api.delivery.service}")
    private String deliveryService;

    private static final String CREATE = "/delivery?senderName=%s&receiverName%s";

    public void create(String senderName, String receiverName) {
        try {
            final HttpResponse<String> resp = HTTP.POST(deliveryService, CREATE);

            if (resp.statusCode() != 200)
                throw new RuntimeException("service call failed.");

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
