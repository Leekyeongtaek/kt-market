package com.market.ktmarket.receipt.service.component;

import com.market.ktmarket.receipt.domain.Receipt;
import com.market.ktmarket.receipt.dto.ProductCart;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ProductCounterTest {

    @Test
    void ProductCart_객체로_Receipt_객체를_생성할_수_있다() {
        //given
        ProductCart.ProductCartItem productCartItem1 = new ProductCart.ProductCartItem(1L, "매일우유", 1000, 5);
        ProductCart.ProductCartItem productCartItem2 = new ProductCart.ProductCartItem(2L, "서울우유", 1200, 1);
        ProductCart productCart = new ProductCart(1L, List.of(productCartItem1, productCartItem2), 100);

        //when
        Receipt receipt = new ProductCounter().unpackProductCart(productCart);

        //then
        assertThat(receipt.getMemberId()).isEqualTo(1L);
        assertThat(receipt.getUsePoint()).isEqualTo(100);
        assertThat(receipt.getReceiptItems().size()).isEqualTo(2);
        assertThat(receipt.getReceiptItems()).extracting("productId").containsExactly(1L, 2L);
        assertThat(receipt.getReceiptItems()).extracting("productName").containsExactly("매일우유", "서울우유");
        assertThat(receipt.getReceiptItems()).extracting("productPrice").containsExactly(1000, 1200);
        assertThat(receipt.getReceiptItems()).extracting("quantity").containsExactly(5, 1);
    }
}