package ltd.inmind.stock.client;

import com.google.protobuf.ByteString;
import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Client;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;

@Configuration
public class EtcdClient {

    @Bean
    public Client etcd() {
        // create client using endpoints

        return Client.builder()
                .endpoints("http://localhost:2379")
                .build();
    }

    public static ByteSequence toByteSequence(Long value) {
        return ByteSequence.from(ByteString.copyFromUtf8(String.valueOf(value)));
    }

    public static String toStr(ByteSequence value) {
        return value.toString(StandardCharsets.UTF_8);
    }
}
