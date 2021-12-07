package ltd.inmind.bag.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StockAPI {

    @Value("${api.stock.service}")
    private String stockService;

    public void clearance(Long productId, Long count) {
        System.out.println(stockService);
    }

    public void ret(Long productId, Long count) {

    }
}
