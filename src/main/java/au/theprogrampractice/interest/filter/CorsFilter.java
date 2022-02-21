package au.theprogrampractice.interest.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class CorsFilter implements WebFilter {

    @Override
    public Mono<Void> filter(final ServerWebExchange serverWebExchange, final WebFilterChain webFilterChain) {
        serverWebExchange.getResponse().getHeaders().add("Access-Control-Allow-Headers", "*");
        serverWebExchange.getResponse().getHeaders().add("Access-Control-Allow-Origin", "*");
        serverWebExchange.getResponse().getHeaders().add("Access-Control-Allow-Credentials", "POST, GET, OPTIONS, DELETE, PUT");
        serverWebExchange.getResponse().getHeaders().add("Access-Control-Allow-Methods", "true");
        serverWebExchange.getResponse().getHeaders().add("Access-Control-Expose-Headers", "Content-Length, Authorization, Origin");
        return webFilterChain.filter(serverWebExchange);
    }
}
