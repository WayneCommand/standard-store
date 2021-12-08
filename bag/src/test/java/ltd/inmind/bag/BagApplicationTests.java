package ltd.inmind.bag;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariables;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@EnabledIfEnvironmentVariables({
        @EnabledIfEnvironmentVariable(named = "api.stock.service", matches = "api.stock.service"),
        @EnabledIfEnvironmentVariable(named = "api.order.service", matches = "api.order.service")
})
class BagApplicationTests {

    @Test
    void contextLoads() {
    }

}
