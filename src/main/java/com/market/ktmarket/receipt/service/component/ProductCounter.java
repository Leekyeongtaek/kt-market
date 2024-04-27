package com.market.ktmarket.receipt.service.component;

import com.market.ktmarket.receipt.domain.Receipt;
import com.market.ktmarket.receipt.domain.ReceiptItem;
import com.market.ktmarket.receipt.dto.ProductCart;
import com.market.ktmarket.receipt.dto.ProductCart.ProductCartItem;
import org.springframework.stereotype.Component;

@Component
public class ProductCounter {

    public Receipt unpackProductCart(ProductCart productCart) {
        return Receipt.builder()
                .memberId(productCart.getMemberId())
                .usePoint(productCart.getUsedPoint())
                .receiptItems(productCart.getProductCartItemList().stream().map(this::toReceiptItem).toList())
                .build();
    }

    private ReceiptItem toReceiptItem(ProductCartItem cartItem) {
        return ReceiptItem.builder()
                .productId(cartItem.getProductId())
                .productName(cartItem.getProductName())
                .productPrice(cartItem.getProductPrice())
                .quantity(cartItem.getQuantity())
                .build();
    }

}
