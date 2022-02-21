package au.theprogrampractice.interest.security;

import au.theprogrampractice.interest.exception.BusinessServiceException;
import au.theprogrampractice.interest.exception.dto.ErrorCodeEnum;
import au.theprogrampractice.interest.security.util.JwtUtils;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import io.vavr.control.Either;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.DefaultOAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.ReactiveOpaqueTokenIntrospector;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Base64;
import java.util.Map;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static org.apache.commons.lang3.BooleanUtils.isFalse;
import static org.apache.commons.lang3.StringUtils.EMPTY;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class TokenIntrospect implements ReactiveOpaqueTokenIntrospector {

    JwtUtils jwtUtils;

    @Override
    public Mono<OAuth2AuthenticatedPrincipal> introspect(final String token) {
        final Either<BusinessServiceException, OAuth2AuthenticatedPrincipal> oAuth2AuthenticatedPrincipals = io.vavr.API.Match(token)
                .of(Case($(v -> isFalse(jwtUtils.validateJwtToken(token))), Either.left(new BusinessServiceException(ErrorCodeEnum.UNAUTHORIZED, "authentication.error.invalid_token"))),
                        Case($(), Either.right(authenticate(token, decode(token)))));
        return Mono.just(oAuth2AuthenticatedPrincipals.getOrElseThrow(ex -> ex));
    }

    @SneakyThrows
    private Map<String, Object> decode(final String token) {
        final DecodedJWT jwt = JWT.decode(token);
        final String base64EncodedBody = jwt.getPayload();
        final String body = new String(Base64.getUrlDecoder().decode(base64EncodedBody));
        final Map<String, Object> map = new ObjectMapper().readValue(body, new TypeReference<>() {
        });
        map.put("token", token);
        return map;
    }

    private OAuth2AuthenticatedPrincipal authenticate(final String token, final Map<String, Object> map) {
        final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken("test", EMPTY, Lists.newArrayList());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        return new DefaultOAuth2AuthenticatedPrincipal("test", Map.of("exp", Instant.now(), "token", token), Lists.newArrayList());
    }

}
