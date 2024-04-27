package com.market.ktmarket.product.dto;

import com.market.ktmarket.product.domain.Product;
import lombok.*;

@Getter
public class ProductSearchCondition {
    private String name;
    private Integer minPrice;
    private Integer maxPrice;
    private Product.Type type;

    public ProductSearchCondition(String name, Integer minPrice, Integer maxPrice, Product.Type type) {
        this.name = name;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.type = type;
    }
}
