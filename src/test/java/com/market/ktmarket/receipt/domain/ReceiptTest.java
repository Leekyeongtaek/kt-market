package com.market.ktmarket.receipt.domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static com.market.ktmarket.receipt.domain.Receipt.Status.CANCEL;
import static com.market.ktmarket.receipt.domain.Receipt.Status.PAYED;
import static org.assertj.core.api.Assertions.*;

class ReceiptTest {

    @Test
    void 결제완료() {
        //given
        Receipt receipt = Receipt.builder()
                .receiptItems(List.of(ReceiptItem.builder().build()))
                .build();

        //when
        receipt.payed();

        //then
        assertThat(receipt.getStatus()).isEqualTo(PAYED);
    }

    @Test
    void 결제취소() {
        //given
        Receipt receipt = Receipt.builder()
                .receiptItems(List.of(ReceiptItem.builder().build()))
                .status(PAYED)
                .build();

        //when
        receipt.cancel();

        //then
        assertThat(receipt.getStatus()).isEqualTo(CANCEL);
    }

    @Test
    void 총결제금액은_구매상품의_상품금액_곱하기_구매수량의_합이다() {
        //given
        ReceiptItem receiptItem = ReceiptItem.builder()
                .productPrice(2000)
                .quantity(5)
                .build();

        Receipt receipt = Receipt.builder()
                .receiptItems(List.of(receiptItem))
                .build();

        //when
        int totalPrice = receipt.calculateTotalPrice();

        //then
        assertThat(totalPrice).isEqualTo(10000);
    }

    @Test
    void 실제_결제금액은_총결제금액에서_사용포인트를_차감한_금액이다() {
        //given
        ReceiptItem receiptItem = ReceiptItem.builder()
                .productPrice(2000)
                .quantity(5)
                .build();

        Receipt receipt = Receipt.builder()
                .receiptItems(List.of(receiptItem))
                .usePoint(1000)
                .build();

        //when
        int paymentAmount = receipt.calculatePaymentAmount();

        //then
        assertThat(paymentAmount).isEqualTo(9000);
    }

    @Test
    void 현금결제_여부는_예상_결제금액에서_사용포인트를_차감한_금액이_0보다_큰_경우_True_반환() {
        //given
        ReceiptItem receiptItem = ReceiptItem.builder()
                .productPrice(1000)
                .quantity(1)
                .build();

        Receipt receipt = Receipt.builder()
                .receiptItems(List.of(receiptItem))
                .usePoint(500)
                .build();

        //when
        boolean isCashPayment = receipt.isCashPayment();

        //then
        assertThat(isCashPayment).isTrue();
    }

    @Test
    void 포인트_사용여부는_사용포인트_값이_0보다_큰_경우_True_반환() {
        //given
        ReceiptItem receiptItem = ReceiptItem.builder()
                .productPrice(1000)
                .quantity(1)
                .build();

        Receipt receipt = Receipt.builder()
                .receiptItems(List.of(receiptItem))
                .usePoint(500)
                .build();

        //when
        boolean isUsePoint = receipt.isUsePoint();

        //then
        assertThat(isUsePoint).isTrue();
    }

    @Test
    void 사용하려는_포인트금액이_예상_결제금액보다_큰_경우_예외발생() {
        //given
        ReceiptItem receiptItem = ReceiptItem.builder()
                .productPrice(1000)
                .quantity(1)
                .build();


        Receipt receipt = Receipt.builder()
                .usePoint(1001)
                .receiptItems(List.of(receiptItem))
                .build();

        //when + then
        assertThatThrownBy(receipt::validatePointUsePolicy)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("사용 포인트는 예상 결제 금액보다 클 수 없습니다.");
    }
}