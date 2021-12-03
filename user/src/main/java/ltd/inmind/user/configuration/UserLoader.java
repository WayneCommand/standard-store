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
import java.util.Map;
import java.util.stream.Collectors;

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

            final List<User> list = toml.getList("users")
                    .stream().map(ele -> toUser((Map) ele))
                    .collect(Collectors.toList());

            userList.addAll(list);
        } catch (IOException | RuntimeException e) {
            log.error("read user failed, switch to default data.");
            userList.clear();
            userList.add(new User("default", "default@inmind.ltd"));
        }
    }


    User toUser(Map ele) {

        return new User((String) ele.get("name"), (String) ele.get("account"));
    }

}
