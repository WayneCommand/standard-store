package ltd.inmind.order.route;

import lombok.AllArgsConstructor;
import ltd.inmind.common.CommonAuth;
import ltd.inmind.order.records.Inventory;
import ltd.inmind.order.records.Order;
import ltd.inmind.order.service.OrderService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@AllArgsConstructor
@RequestMapping("/order")
@RestController
public class OrderRoute {

    private final OrderService orderService;


    @PostMapping("/create")
    public Order create(@RequestBody List<Inventory> inventories, HttpServletRequest request) {

        String userAccount = CommonAuth.getUserAccount(CommonAuth.getAuthToken(request));

        return orderService.create(inventories, userAccount);
    }

    @DeleteMapping("/cancel")
    public void cancel(String id, HttpServletRequest request) {


    }

    @PostMapping("/callback/paid/{order_id}")
    public void paid(@PathVariable("order_id") String id, HttpServletRequest request) {
        String userAccount = CommonAuth.getUserAccount(CommonAuth.getAuthToken(request));

        orderService.paid(id, userAccount);
    }




}
