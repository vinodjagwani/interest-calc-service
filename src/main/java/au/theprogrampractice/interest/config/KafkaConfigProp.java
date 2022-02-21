package au.theprogrampractice.interest.config;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Getter
@Validated
@ConstructorBinding
@AllArgsConstructor
@ConfigurationProperties(prefix = "kafka")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class KafkaConfigProp {

    String acks = "all";

    Client client = new Client();

    List<String> topics = new ArrayList<>(Collections.singletonList("demo-topic"));

    Bootstrap bootstrap = new Bootstrap();

    @Data
    static class Bootstrap {
        List<String> servers = new ArrayList<>(Collections.singletonList("localhost:9092"));
    }

    @Data
    static class Client {
        String id = "reactive-kafka-producer";
    }
}
