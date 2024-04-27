package com.market.ktmarket.receipt.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class ProductCart {

    private Long memberId;
    private int usedPoint;
    private List<ProductCartItem> productCartItemList;

    public ProductCart(Long memberId, List<ProductCartItem> productCartItemList, int usedPoint) {
        this.memberId = memberId;
        this.productCartItemList = productCartItemList;
        this.usedPoint = usedPoint;
    }

    @Getter
    public static class ProductCartItem {
        private Long productId;
        private String productName;
        private int productPrice;
        private int quantity;

        public ProductCartItem(Long productId, String productName, int productPrice, int quantity) {
            this.productId = productId;
            this.productName = productName;
            this.productPrice = productPrice;
            this.quantity = quantity;
        }
    }

}
