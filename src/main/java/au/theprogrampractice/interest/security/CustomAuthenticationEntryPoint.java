package au.theprogrampractice.interest.security;

import au.theprogrampractice.interest.exception.dto.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static java.util.Collections.emptyList;

@Slf4j
public final class CustomAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {

    final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    @SneakyThrows
    public Mono<Void> commence(final ServerWebExchange serverWebExchange, final AuthenticationException ex) {
        log.error("Unauthorized error: ", ex);
        final ErrorResponse errorResponse = ErrorResponse.builder()
                .code(1001)
                .message("Full authentication is required to access this resource")
                .errors(emptyList()).build();
        serverWebExchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        serverWebExchange.getResponse().getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        final byte[] data = objectMapper.writeValueAsBytes(errorResponse);
        return serverWebExchange.getResponse().writeWith(Flux.just(serverWebExchange.getResponse().bufferFactory().wrap(data)));
    }
}
