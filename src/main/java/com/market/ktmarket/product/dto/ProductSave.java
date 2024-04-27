package com.market.ktmarket.product.dto;

import com.market.ktmarket.product.domain.Product;
import lombok.Getter;

import static com.market.ktmarket.product.domain.Product.*;

@Getter
public class ProductSave {

    private String name;
    private int price;
    private int stock;
    private final Type type;

    public ProductSave(String name, int price, int stock, Type type) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.type = type;
    }

    public Product toEntity() {
        return Product.builder()
                .name(name)
                .price(price)
                .stock(stock)
                .type(type)
                .build();
    }
}
