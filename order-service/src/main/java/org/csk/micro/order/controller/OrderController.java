package org.csk.micro.order.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.csk.micro.order.client.ProductClient;
import org.csk.micro.order.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{productId}")
    public String placeOrder(@PathVariable String productId, HttpServletRequest request) {

        String traceId = request.getHeader("X-Trace-Id");
        return orderService.processOrder(productId, traceId);    }
}
