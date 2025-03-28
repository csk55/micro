package org.csk.micro.api_gateway.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class RequestLoggingFilter implements GlobalFilter, Ordered {

    private static final Logger logger = LoggerFactory.getLogger(RequestLoggingFilter.class);
    private static final String TRACE_ID_HEADER = "X-Trace-Id";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // Extract traceId from incoming request headers
        String traceId = exchange.getRequest().getHeaders().getFirst(TRACE_ID_HEADER);

        // If not present, generate a new one
        if (traceId == null || traceId.isEmpty()) {
            traceId = UUID.randomUUID().toString();
        }

        // Set traceId in MDC for logging
        MDC.put("traceId", traceId);

        // Log incoming request
        logger.info("Request AG: {} {}", exchange.getRequest().getMethod(), exchange.getRequest().getURI());

        // Mutate request to pass traceId to downstream services
        String finalTraceId = traceId;
        ServerWebExchange mutatedExchange = exchange.mutate()
                .request(r -> r.headers(headers -> headers.set(TRACE_ID_HEADER, finalTraceId)))
                .build();

        return chain.filter(mutatedExchange)
                .doFinally(signalType -> MDC.clear()) // Ensure MDC is cleared after request
                .then(Mono.fromRunnable(() ->
                        logger.info("Response AG: {} - {}", mutatedExchange.getRequest().getMethod(), mutatedExchange.getResponse().getStatusCode())
                ));
    }

    @Override
    public int getOrder() {
        return -1; // Ensure it runs first
    }
}
