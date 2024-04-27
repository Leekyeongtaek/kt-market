package com.market.ktmarket.receipt.domain;

import com.market.ktmarket.common.AuditingTime;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "receipt")
@Entity
public class Receipt extends AuditingTime {

    public enum Status {PAYED, CANCEL}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "receipt_id")
    private Long id;
    @Column(name = "member_id", nullable = false, updatable = false)
    private Long memberId;
    @Column(name = "use_point")
    private int usePoint;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "receipt", cascade = CascadeType.PERSIST)
    private List<ReceiptItem> receiptItems = new ArrayList<>();

    @Builder
    public Receipt(Long id, Long memberId, int usePoint, Status status, List<ReceiptItem> receiptItems) {
        this.id = id;
        this.memberId = memberId;
        this.usePoint = usePoint;
        this.status = status;
        receiptItems.forEach(this::mappingReceiptItem);
    }

    private void mappingReceiptItem(ReceiptItem receiptItem) {
        receiptItems.add(receiptItem);
        receiptItem.mappingReceipt(this);
    }

    public int calculateTotalPrice() {
        return receiptItems.stream().mapToInt(ReceiptItem::calculatePrice).sum();
    }

    public int calculatePaymentAmount() {
        return calculateTotalPrice() - usePoint;
    }

    public boolean isCashPayment() {
        return calculatePaymentAmount() > 0;
    }

    public boolean isUsePoint() {
        return usePoint > 0;
    }

    public void payed() {
        this.status = Status.PAYED;
    }

    public void cancel() {
        this.status = Status.CANCEL;
    }

    public void validatePointUsePolicy() {
        if (usePoint > calculateTotalPrice()) {
            throw new IllegalArgumentException("사용 포인트는 예상 결제 금액보다 클 수 없습니다.");
        }
    }
}
