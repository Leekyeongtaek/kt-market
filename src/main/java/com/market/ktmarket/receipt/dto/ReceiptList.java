package com.market.ktmarket.receipt.dto;

import com.market.ktmarket.receipt.domain.Receipt;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReceiptList {
    private Long receiptNo;
    private int totalAmount;
    private int realPaymentAmount;
    private int usePoint;
    private LocalDateTime issuedDataTime;

    public ReceiptList(Receipt receipt) {
        this.receiptNo = receipt.getId();
        this.totalAmount = receipt.calculateTotalPrice();
        this.realPaymentAmount = receipt.calculatePaymentAmount();
        this.usePoint = receipt.getUsePoint();
        this.issuedDataTime = receipt.getCreatedDate();
    }
}
