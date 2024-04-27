package com.market.ktmarket.product.domain;

import com.market.ktmarket.common.AuditingTime;
import com.market.ktmarket.product.dto.ProductUpdate;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product")
@Entity
public class Product extends AuditingTime {

    public enum Type {FOOD, SNACK, DRINK}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "stock", nullable = false)
    private int stock;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "type", nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private Type type;

    @Builder
    public Product(Long id, String name, int stock, int price, Type type) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.price = price;
        this.type = type;
    }

    public void update(ProductUpdate productUpdate) {
        name = productUpdate.getName();
        price = productUpdate.getPrice();
        stock += productUpdate.getAddStock();
    }
}
