package ltd.inmind.user.configuration;

import com.moandjiezana.toml.Toml;
import lombok.extern.slf4j.Slf4j;
import ltd.inmind.user.model.User;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class UserLoader {

    public static final List<User> userList = new ArrayList<>();

    @PostConstruct
    public void load() {
        final ClassPathResource resource = new ClassPathResource("users.toml");

        try {
            Toml toml = new Toml()
                    .read(resource.getInputStream());

            final List<User> list = toml.getTable("user")
                    .getList("list", new ArrayList<>());

            userList.addAll(list);
        } catch (IOException e) {
            log.error("read user failed, switch to default data.");
            userList.clear();
            userList.add(new User("default", "default@inmind.ltd"));
        }
    }


}
