package com.market.ktmarket.receipt.dto;

import com.market.ktmarket.receipt.domain.Receipt;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ReceiptDetails {
    private Long receiptNo;
    private int totalAmount;
    private int realPaymentAmount;
    private int usePoint;
    private LocalDateTime issuedDataTime;
    private List<ReceiptItemDetails> receiptItemDetails;

    public ReceiptDetails(Receipt receipt) {
        this.receiptNo = receipt.getId();
        this.totalAmount = receipt.calculateTotalPrice();
        this.realPaymentAmount = receipt.calculatePaymentAmount();
        this.usePoint = receipt.getUsePoint();
        this.issuedDataTime = receipt.getCreatedDate();
        this.receiptItemDetails = receipt.getReceiptItems().stream().map(ReceiptItemDetails::new).toList();
    }

}
