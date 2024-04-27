package com.market.ktmarket.receipt.dto;

import com.market.ktmarket.receipt.domain.ReceiptItem;
import lombok.Getter;

@Getter
public class ReceiptItemDetails {

    private String productName;
    private int productPrice;
    private int quantity;
    private int amount;

    public ReceiptItemDetails(ReceiptItem receiptItem) {
        this.productName = receiptItem.getProductName();
        this.productPrice = receiptItem.getProductPrice();
        this.quantity = receiptItem.getQuantity();
        this.amount = receiptItem.calculatePrice();
    }
}
