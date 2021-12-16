package ltd.inmind.order;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariables;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@EnabledIfEnvironmentVariables({
        @EnabledIfEnvironmentVariable(named = "api.delivery.service", matches = "api.delivery.service")
})
class OrderApplicationTests {

    @Test
    void contextLoads() {
    }

}
