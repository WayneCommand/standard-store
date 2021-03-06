package ltd.inmind.order.service;

import lombok.AllArgsConstructor;
import ltd.inmind.order.api.DeliveryAPI;
import ltd.inmind.order.records.Inventory;
import ltd.inmind.order.records.Order;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Service
public class OrderService {

    static final Map<String, List<Order>> memCache = new HashMap<>();

    private final DeliveryAPI deliveryAPI;

    public Order get(String id, String account) {
        if (memCache.containsKey(account)) {
            final List<Order> orders = memCache.get(account);
            return orders.stream()
                    .filter(order -> id.equals(order.id()))
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

    public Order create(List<Inventory> inventories, String account) {
        Order aNew = Order.createNew(inventories, account);

        if (memCache.containsKey(account)) {
            memCache.get(account).add(aNew);
        } else {
            List<Order> orders = new ArrayList<>();
            orders.add(aNew);
            memCache.put(account, orders);
        }

        return aNew;
    }


    public void paid(String id, String account) {

        if (memCache.containsKey(account)) {
            List<Order> orders = memCache.get(account);
            Order paidOrder = orders.stream()
                    .filter(order -> id.equals(order.id()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("order not found."));

            Order paid = paidOrder.toPaid();
            orders.remove(paidOrder);
            orders.add(paid);

            deliveryAPI.create("DeliveryService", account);
        }

    }


}
