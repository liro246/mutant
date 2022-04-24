package co.com.meli.api.filter;

import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Log
@Component
public class ApiKeyRequestFilter implements WebFilter {

    @Value("${security-api-key}")
    private String apiGeeKey;

    @SneakyThrows
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String headerKey = exchange.getRequest().getHeaders().getFirst("x-api-key");
        if (!apiGeeKey.equals(headerKey) && !exchange.getRequest().getPath().value().contains("/health")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Api key not found");
        }
        return chain.filter(exchange);
    }
}
