package org.csk.micro.order.service;

import org.csk.micro.order.client.ProductClient;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class OrderService {

    private final ProductClient productClient;

    public OrderService(ProductClient productClient) {
        this.productClient = productClient;
    }

    public String processOrder(String productId) {
        String productInfo = productClient.getProductInfo(productId);
        return "Processing order for: " + productInfo;
    }
}
