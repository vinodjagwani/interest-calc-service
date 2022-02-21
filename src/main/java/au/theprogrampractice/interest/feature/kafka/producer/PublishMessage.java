package au.theprogrampractice.interest.feature.kafka.producer;

import au.theprogrampractice.interest.feature.kafka.producer.dto.MessagePayload;
import reactor.core.publisher.Flux;

@FunctionalInterface
public interface PublishMessage {

    void sendToKafka(final String topic, final Flux<MessagePayload> messages);
}
