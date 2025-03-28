package org.csk.micro.order.service;

import org.csk.micro.order.client.ProductClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    private final ProductClient productClient;

    public OrderService(ProductClient productClient) {
        this.productClient = productClient;
    }

    public String processOrder(String productId, String traceId) {
        if (traceId == null || traceId.isEmpty()) {
            log.info("ORDER SERVICE new traceID");
            traceId = java.util.UUID.randomUUID().toString();
        }

        // Call Product Service with traceId
        String productResponse = productClient.getProduct(productId, traceId);


        return "Order placed for product " + productId + " | Product details: " + productResponse;

    }
}
