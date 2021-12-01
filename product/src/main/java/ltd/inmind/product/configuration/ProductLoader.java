package ltd.inmind.product.configuration;

import com.moandjiezana.toml.Toml;
import lombok.extern.slf4j.Slf4j;
import ltd.inmind.product.model.Product;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class ProductLoader {

    public static final List<Product> productList = new ArrayList<>();

    @PostConstruct
    public void load() {
        final ClassPathResource resource = new ClassPathResource("products.toml");

        try {
            Toml toml = new Toml()
                    .read(resource.getInputStream());

            final List<Product> list = toml.getList("products", new ArrayList<>());

            productList.addAll(list);
        } catch (IOException e) {
            log.error("read user failed, switch to default data.");
        }

        System.out.println(productList);
    }


}
