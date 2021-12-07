package ltd.inmind.stock.configuration;

import com.moandjiezana.toml.Toml;
import io.etcd.jetcd.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ltd.inmind.stock.model.Stock;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ltd.inmind.stock.client.EtcdClient.toByteSequence;

@Component
@Slf4j
@AllArgsConstructor
public class StockLoader {

    public static final List<Stock> stockList = new ArrayList<>();

    private final Client etcd;

    private static final String PREFIX = "stock_";


    @PostConstruct
    public void load() {
        final ClassPathResource resource = new ClassPathResource("stocks.toml");

        try {
            Toml toml = new Toml()
                    .read(resource.getInputStream());

            final List<Stock> list = toml.getList("stocks").stream()
                    .map(this::toStock)
                    .toList();

            stockList.addAll(list);

        } catch (IOException e) {
            log.error("read user failed, switch to default data.");
        }

        loadToEtcd();
    }

    void loadToEtcd() {
        final KV kvClient = etcd.getKVClient();

        stockList.forEach(stock -> {
            kvClient.put(toByteSequence(PREFIX.concat(String.valueOf(stock.productId()))), toByteSequence(stock.total()));
        });
    }

    Stock toStock(Object o) {

        if (o instanceof HashMap) {
            Map ele = (Map) o;
            final Long productId = (Long) ele.get("productId");
            final Long total = (Long) ele.get("total");
            return new Stock(productId, total);
        }

        return null;
    }





}
