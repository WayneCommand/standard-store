package ltd.inmind.bag.service;

import lombok.AllArgsConstructor;
import ltd.inmind.bag.api.OrderAPI;
import ltd.inmind.bag.api.StockAPI;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BagService {

    Map<String, List<Item>> memStore = new HashMap<>();

    private final StockAPI stockAPI;

    private final OrderAPI orderAPI;


    public void addProduct(Long productId, Long count, String account) {
        if (memStore.containsKey(account)) {
            final List<Item> items = memStore.get(account);
            final Item product = items.stream()
                    .filter(item -> productId.equals(item.productId))
                    .findFirst()
                    .orElse(null);

            if (product == null) {
                items.add(new Item(productId, count));
            }else{
                Long currCount = product.count;
                items.remove(product);
                items.add(new Item(productId, currCount + count));
            }
        }else {
            List<Item> items = new ArrayList<>();
            items.add(new Item(productId, count));
            memStore.put(account, items);
        }

    }

    public void delProduct(Long productId, Long count, String account) {
        if (memStore.containsKey(account)) {
            final List<Item> items = memStore.get(account);
            final Item product = items.stream()
                    .filter(item -> productId.equals(item.productId))
                    .findFirst()
                    .orElse(null);

            if (product != null) {
                Long currCount = product.count;
                if (count >= currCount){
                    items.remove(product);
                }else {
                    items.remove(product);
                    items.add(new Item(productId, currCount - count));
                }
            }
        }

    }

    public void settlement(String account) {
        if (memStore.containsKey(account)) {
            final List<Item> items = memStore.get(account);
            items.forEach(item -> {
                stockAPI.clearance(item.productId, item.count);
            });

            final List<OrderAPI.Inventory> inventories = items.stream()
                    .map(this::toInventory)
                    .collect(Collectors.toList());

            orderAPI.create(inventories, account);

        }

    }

    OrderAPI.Inventory toInventory(Item item) {
        return new OrderAPI.Inventory(item.productId, item.count, 1.0);
    }

    record Item(Long productId, Long count) {
    }


}
