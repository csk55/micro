package org.csk.micro.order.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ProductClient {
    private final static String productServiceUrl="http://product-service:8082/api/products";

    private final WebClient webClient;

    public ProductClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(productServiceUrl).build();
    }

    public String getProduct(String productId, String traceId) {
        System.out.println("Calling Product Service at----------: " + productServiceUrl);

        return webClient.get()
                .uri("/{productId}", productId)
                .header("X-Trace-Id", traceId) // ✅ Pass traceId in headers
                .retrieve()
                .bodyToMono(String.class)
                .block(); // Blocking call, for simplicity
    }
}
