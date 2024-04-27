package com.market.ktmarket.member.domain;

import com.market.ktmarket.member.domain.enumeration.MemberGrade;
import com.market.ktmarket.receipt.domain.Receipt;
import com.market.ktmarket.receipt.domain.ReceiptItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.market.ktmarket.member.domain.MemberPointItem.Type.MEMBERSHIP;
import static com.market.ktmarket.member.domain.MemberPointItem.Type.PAYMENT;
import static org.assertj.core.api.Assertions.assertThat;

class MemberPointTest {

    private MemberPoint memberPoint;

    @BeforeEach
    void setUp() {
        memberPoint = MemberPoint.builder()
                .memberPointItems(new ArrayList<>())
                .availablePoint(1000)
                .accumulatedPoint(1000)
                .build();

        Member.builder()
                .grade(MemberGrade.BRONZE)
                .memberPoint(memberPoint)
                .build();
    }

    @Test
    void 영수증_발급시_포인트를_사용하지_않고_현금으로만_결제한_경우_멤버십포인트가_적립된다() {
        //given
        ReceiptItem receiptItem = ReceiptItem.builder()
                .productPrice(1000)
                .quantity(1)
                .build();

        Receipt receipt = Receipt.builder()
                .usePoint(0)
                .receiptItems(List.of(receiptItem))
                .build();

        //when
        memberPoint.parseReceipt(receipt);

        //then
        assertThat(memberPoint.getAvailablePoint()).isEqualTo(1010);
        assertThat(memberPoint.getAccumulatedPoint()).isEqualTo(1010);
        assertThat(memberPoint.getMemberPointItems().size()).isEqualTo(1);
        assertThat(memberPoint.getMemberPointItems()).extracting("point").containsExactly(10);
        assertThat(memberPoint.getMemberPointItems()).extracting("type").containsExactly(MEMBERSHIP);
    }

    /*
    회원 등급에 따른 포인트 적립은 포인트만을 사용해서 결제 했다면 포인트 적립은 발생하지 않는다.
    실제 결제 금액이 0보다 큰 경우에만 결제 금액에 따라 포인트가 적립된다.
     */
    @Test
    void 영수증_발급시_포인트를_사용한_경우_사용한_포인트_만큼_보유_포인트에서_차감된다() {
        //given
        ReceiptItem receiptItem = ReceiptItem.builder()
                .productPrice(1000)
                .quantity(1)
                .build();

        Receipt receipt = Receipt.builder()
                .usePoint(1000)
                .receiptItems(List.of(receiptItem))
                .build();

        //when
        memberPoint.parseReceipt(receipt);

        //then
        assertThat(memberPoint.getAvailablePoint()).isEqualTo(0);
        assertThat(memberPoint.getAccumulatedPoint()).isEqualTo(1000);
        assertThat(memberPoint.getMemberPointItems().size()).isEqualTo(1);
        assertThat(memberPoint.getMemberPointItems()).extracting("point").containsExactly(-1000);
        assertThat(memberPoint.getMemberPointItems()).extracting("type").containsExactly(PAYMENT);
    }

    @Test
    void 영수증_발급시_현금과_포인트를_함께_사용하면_보유_포인트는_차감되고_실제_결제금액만큼_포인트가_적립된다() {
        //given
        ReceiptItem receiptItem = ReceiptItem.builder()
                .productPrice(1000)
                .quantity(1)
                .build();

        Receipt receipt = Receipt.builder()
                .usePoint(500)
                .receiptItems(List.of(receiptItem))
                .build();

        //when
        memberPoint.parseReceipt(receipt);

        //then
        assertThat(memberPoint.getAvailablePoint()).isEqualTo(505);
        assertThat(memberPoint.getAccumulatedPoint()).isEqualTo(1005);
        assertThat(memberPoint.getMemberPointItems().size()).isEqualTo(2);
        assertThat(memberPoint.getMemberPointItems()).extracting("point").containsExactly(-500, 5);
        assertThat(memberPoint.getMemberPointItems()).extracting("type").containsExactly(PAYMENT, MEMBERSHIP);
    }
}