package ltd.inmind.payment.route;

import lombok.AllArgsConstructor;
import ltd.inmind.common.CommonAuth;
import ltd.inmind.payment.service.PaymentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@AllArgsConstructor
@RestController
@RequestMapping("/payment")
public class PaymentRoute {

    private final PaymentService paymentService;

    @PostMapping("/pay")
    public PaymentService.Payment pay(String orderId, HttpServletRequest request) {

        final String userAccount = CommonAuth.getUserAccount(CommonAuth.getAuthToken(request));

        return paymentService.pay(orderId, userAccount);
    }


    @PostMapping("/paid")
    public void paid(String payId, String account) {
        paymentService.paid(payId, account);
    }
}
