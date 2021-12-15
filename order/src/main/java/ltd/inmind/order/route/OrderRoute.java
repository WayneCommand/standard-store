package ltd.inmind.order.route;

import lombok.AllArgsConstructor;
import ltd.inmind.common.CommonAuth;
import ltd.inmind.common.SerialUtil;
import ltd.inmind.order.records.Inventory;
import ltd.inmind.order.records.Order;
import ltd.inmind.order.service.OrderService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@AllArgsConstructor
@RequestMapping("/order")
@RestController
public class OrderRoute {

    private final OrderService orderService;

    @GetMapping("/{id}/bytes")
    public byte[] getToBytes(@PathVariable("id") String id, String account) {
        try {
            return SerialUtil.toBytes(orderService.get(id, account));
        } catch (IOException e) {
            return null;
        }
    }

    @GetMapping("/{id}")
    public Order get(@PathVariable("id") String id, HttpServletRequest request) {

        final String userAccount = CommonAuth.getUserAccount(CommonAuth.getAuthToken(request));

        return orderService.get(id, userAccount);
    }

    @PostMapping("/create")
    public Order create(@RequestBody List<Inventory> inventories, String account) {

        return orderService.create(inventories, account);
    }

    @DeleteMapping("/cancel")
    public void cancel(String id, HttpServletRequest request) {


    }

    @PostMapping("/callback/paid/{order_id}")
    public void paid(@PathVariable("order_id") String id, String account) {

        orderService.paid(id, account);
    }




}
