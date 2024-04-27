package com.market.ktmarket.product.dto;

import com.market.ktmarket.product.domain.Product;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
public class ProductDetails {

    private long productId;
    private String name;
    private int price;
    private int stock;
    private LocalDateTime createdDate;

    public ProductDetails(Product product) {
        productId = product.getId();
        name = product.getName();
        price = product.getPrice();
        stock = product.getStock();
        createdDate = product.getCreatedDate();
    }
}
