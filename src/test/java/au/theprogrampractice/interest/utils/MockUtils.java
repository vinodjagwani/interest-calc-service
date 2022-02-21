package au.theprogrampractice.interest.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@UtilityClass
public class MockUtils {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @SuppressWarnings("unchecked")
    public static <T> T getResource(final String resource, final Class<T> clazz) {
        final InputStream inStream = MockUtils.class.getClassLoader().getResourceAsStream(resource);
        if (Objects.nonNull(inStream)) {
            try {
                if (String.class.equals(clazz)) {
                    return (T) new BufferedReader(new InputStreamReader(inStream)).lines().collect(Collectors.joining("\n"));
                }
                MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                MAPPER.registerModule(new JavaTimeModule());
                MAPPER.registerModule(new Jdk8Module());
                return MAPPER.readValue(inStream, clazz);

            } catch (IOException ex) {
                // log.error("Can't serialize: ", ex);
            }
        }
        return null;
    }
}
