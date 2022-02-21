package au.theprogrampractice.interest.security;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReactiveWebSecurityConfig {

    @Autowired
    TokenIntrospect tokenIntrospect;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(final ServerHttpSecurity http) {
        return http.exceptionHandling()
                .accessDeniedHandler((swe, e) -> Mono.fromRunnable(() -> swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN)))
                .and()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .authorizeExchange()
                .pathMatchers(HttpMethod.OPTIONS).permitAll()
                .pathMatchers("/actuator/**", "/swagger**/**", "/v2/api-docs/**",
                        "/webjars/**", "/static/**", "/error", "/v1/documents").permitAll()
                .anyExchange().permitAll()
                .and()
                .oauth2ResourceServer().opaqueToken(tokenSpec -> tokenSpec.introspector(tokenIntrospect))
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint()).and().build();
    }

}
