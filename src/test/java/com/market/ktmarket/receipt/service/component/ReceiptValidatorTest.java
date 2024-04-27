package com.market.ktmarket.receipt.service.component;

import com.market.ktmarket.product.domain.Product;
import com.market.ktmarket.product.repository.ProductRepository;
import com.market.ktmarket.receipt.domain.Receipt;
import com.market.ktmarket.receipt.domain.ReceiptItem;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.List;

class ReceiptValidatorTest {

    ReceiptValidator receiptValidator;

    @BeforeEach
    void setUp() {
        receiptValidator = new ReceiptValidator(Mockito.mock(ProductRepository.class));
    }

    @Test
    void 유효성_검증_성공() {
        //given
        ReceiptItem receiptItem = ReceiptItem.builder().productId(1L).productName("매일우유").productPrice(1000).quantity(1).build();

        Receipt receipt = Receipt.builder().receiptItems(List.of(receiptItem)).build();

        Product product = Product.builder().name("매일우유").price(1000).build();

        //when + then
        Assertions.assertThatNoException().isThrownBy(() -> receiptValidator.validate(receipt, new HashMap<>() {{
            put(1L, product);
        }}));
    }

    @Test
    void 상품번호가_존재하지_않는_상품_번호인_경우_예외발생() {
        ReceiptItem receiptItem = ReceiptItem.builder().productId(2L).productName("매일우유").productPrice(1000).quantity(1).build();

        Receipt receipt = Receipt.builder().receiptItems(List.of(receiptItem)).build();

        Product product = Product.builder().name("매일우유").price(1000).build();

        Assertions.assertThatThrownBy(() -> receiptValidator.validate(receipt, new HashMap<>() {{
            put(1L, product);
        }})).isInstanceOf(IllegalArgumentException.class).hasMessage("존재하지 않는 상품 번호입니다.");
    }

    @Test
    void 상품명이_일치하지_않으면_예외발생() {
        ReceiptItem receiptItem = ReceiptItem.builder().productId(1L).productName("매일우유 변경").productPrice(1000).quantity(1).build();

        Receipt receipt = Receipt.builder().receiptItems(List.of(receiptItem)).build();

        Product product = Product.builder().name("매일우유").price(1000).build();

        Assertions.assertThatThrownBy(() -> receiptValidator.validate(receipt, new HashMap<>() {{
            put(1L, product);
        }})).isInstanceOf(IllegalArgumentException.class).hasMessage("상품명이 변경되었습니다.");
    }

    @Test
    void 상품가격이_일치하지_않으면_예외발생() {
        ReceiptItem receiptItem = ReceiptItem.builder().productId(1L).productName("매일우유").productPrice(1000).quantity(1).build();

        Receipt receipt = Receipt.builder().receiptItems(List.of(receiptItem)).build();

        Product product = Product.builder().name("매일우유").price(1200).build();

        Assertions.assertThatThrownBy(() -> receiptValidator.validate(receipt, new HashMap<>() {{
            put(1L, product);
        }})).isInstanceOf(IllegalArgumentException.class).hasMessage("가격이 변경되었습니다.");
    }

}