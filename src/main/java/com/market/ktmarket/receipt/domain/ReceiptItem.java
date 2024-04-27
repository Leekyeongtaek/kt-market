package com.market.ktmarket.receipt.domain;

import com.market.ktmarket.common.AuditingTime;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "receipt_item")
@Entity
public class ReceiptItem extends AuditingTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "receipt_item_id")
    private Long id;
    @Column(name = "product_id", nullable = false, updatable = false)
    private Long productId;
    @Column(name = "product_name", nullable = false, updatable = false)
    private String productName;
    @Column(name = "product_price")
    private int productPrice;
    @Column(name = "quantity")
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receipt_id", nullable = false, updatable = false)
    private Receipt receipt;

    @Builder
    public ReceiptItem(Long id, Long productId, String productName, int productPrice, int quantity) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.quantity = quantity;
    }

    void mappingReceipt(Receipt receipt) {
        this.receipt = receipt;
    }

    public int calculatePrice() {
        return productPrice * quantity;
    }
}
