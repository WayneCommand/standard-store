package ltd.inmind.payment;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariables;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@EnabledIfEnvironmentVariables({
        @EnabledIfEnvironmentVariable(named = "api.order.service", matches = "api.order.service")
})
class PaymentApplicationTests {

    @Test
    void contextLoads() {
    }

}
