package ltd.inmind.delivery.route;

import lombok.AllArgsConstructor;
import ltd.inmind.delivery.records.Package;
import ltd.inmind.delivery.service.DeliveryService;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/delivery")
public class DeliveryRoute {

    private DeliveryService deliveryService;

    @PostMapping
    public Package create(String senderName, String receiverName) {

        return deliveryService.create(senderName, receiverName);
    }

    @PutMapping("/sign/{id}")
    public void sign(@PathVariable("id") String id) {
        deliveryService.sign(id);
    }

}
