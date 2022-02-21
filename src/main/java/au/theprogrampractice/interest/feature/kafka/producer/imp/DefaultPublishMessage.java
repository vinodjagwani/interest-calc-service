package au.theprogrampractice.interest.feature.kafka.producer.imp;

import au.theprogrampractice.interest.feature.kafka.producer.PublishMessage;
import au.theprogrampractice.interest.feature.kafka.producer.dto.MessagePayload;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class DefaultPublishMessage implements PublishMessage {

    KafkaSender<String, String> kafkaSender;

    @Override
    public void sendToKafka(final String topic, final Flux<MessagePayload> messages) {
        final String correlationId = UUID.randomUUID().toString();
        log.debug("Start sending message to kafka with correlationId [{}]", correlationId);
        kafkaSender.send(messages.map(m -> SenderRecord.create(new ProducerRecord<>(topic, m.getMetaData(), m.getMetaData()), correlationId)))
                .doOnError(e -> log.error("Send failed", e)).subscribe(r -> log.info("{}", r.recordMetadata()));
    }
}
