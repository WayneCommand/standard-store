package ltd.inmind.payment.service;

import lombok.AllArgsConstructor;
import ltd.inmind.payment.api.OrderAPI;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Service
public class PaymentService {

    public static final Long PAYMENT_PROCESS = 1L;
    public static final Long PAYMENT_DONE = 2L;
    public static final Long PAYMENT_CANCEL = 3L;

    public static final Map<String, List<Payment>> memCache = new HashMap<>();

    private final OrderAPI orderAPI;

    public Payment pay(String orderId, String account) {
        final OrderAPI.Order order = orderAPI.get(orderId, account);

        final Double summary = order.summary();

        final Payment newPay = Payment.pay(orderId, account, summary);

        final List<Payment> payments = memCache.getOrDefault(account, new ArrayList<>());

        payments.add(newPay);

        memCache.put(account, payments);

        return newPay;
    }

    public void paid(String payId, String account) {
        if (memCache.containsKey(account)){
            final List<Payment> payments = memCache.get(account);

            final Payment payment = payments.stream()
                    .filter(p -> payId.equals(p.id()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("payment not found"));

            final Payment paid = payment.paid();

            orderAPI.paid(payment.orderId(), account);

            payments.remove(payment);
            payments.add(paid);
        }


    }

    public void cancel(String payId, String account) {

    }


    public record Payment(String id, String orderId, String account, Double summary, Long status) {

        public static Payment pay(String orderId, String account, Double summary) {
            return new Payment(String.valueOf(System.nanoTime()), orderId, account, summary, PAYMENT_PROCESS);
        }

        public Payment paid() {
            return new Payment(this.id, this.orderId, this.account, this.summary, PAYMENT_DONE);
        }

        public Payment cancel() {
            return new Payment(this.id, this.orderId, this.account, this.summary, PAYMENT_CANCEL);
        }

    }
}

