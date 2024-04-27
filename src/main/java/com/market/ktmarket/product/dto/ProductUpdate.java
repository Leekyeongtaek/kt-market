package com.market.ktmarket.product.dto;

import lombok.Getter;

@Getter
public class ProductUpdate {

    private String name;
    private int price;
    private int addStock;

    public ProductUpdate(String name, int price, int addStock) {
        this.name = name;
        this.price = price;
        this.addStock = addStock;
    }
}
