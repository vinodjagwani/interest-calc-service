package au.theprogrampractice.interest.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;

import java.util.Map;

@Configuration
public class KafkaConfig {

    @Autowired
    private KafkaConfigProp kafkaConfigProp;

    @Bean
    public KafkaSender<String, String> kafkaSender() {
        return KafkaSender.create(senderOptions());
    }

    private SenderOptions<String, String> senderOptions() {
        return SenderOptions.create(Map.of(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, String.join(",", kafkaConfigProp.getBootstrap().getServers()),
                ProducerConfig.CLIENT_ID_CONFIG, kafkaConfigProp.getClient().getId(),
                ProducerConfig.ACKS_CONFIG, kafkaConfigProp.getAcks(),
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class
        ));
    }


}
