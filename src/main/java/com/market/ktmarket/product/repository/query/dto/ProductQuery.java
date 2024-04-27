package com.market.ktmarket.product.repository.query.dto;

import com.market.ktmarket.product.domain.Product;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ProductQuery {
    private Long id;
    private String name;
    private int price;
    private int stock;
    private Product.Type type;
    private LocalDateTime createdDate;

    public ProductQuery(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.stock = product.getStock();
        this.type = product.getType();
        this.createdDate = product.getCreatedDate();
    }
}
