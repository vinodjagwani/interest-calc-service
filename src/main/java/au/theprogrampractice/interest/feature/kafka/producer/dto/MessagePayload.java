package au.theprogrampractice.interest.feature.kafka.producer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(builderClassName = "MessagePayloadBuilder", toBuilder = true)
@JsonDeserialize(builder = MessagePayload.MessagePayloadBuilder.class)
public class MessagePayload {

    @JsonProperty("metadata")
    String metaData;

    @JsonPOJOBuilder(withPrefix = "")
    public static class MessagePayloadBuilder {
    }
}
