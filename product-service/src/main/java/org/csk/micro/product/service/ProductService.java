package org.csk.micro.product.service;

import org.springframework.stereotype.Service;

@Service
public class ProductService {

    public String getProductInfo(String productId) {
        return "Product Id: " + productId + ", Name: Laptop, Price: $999";
    }
}
