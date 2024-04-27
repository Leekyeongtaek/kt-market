package com.market.ktmarket.member.domain;

import com.market.ktmarket.common.AuditingTime;
import com.market.ktmarket.receipt.domain.Receipt;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member_point")
@Entity
public class MemberPoint extends AuditingTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_point_id")
    private Long id;
    @Column(name = "available_point")
    private int availablePoint;
    @Column(name = "accumulated_point")
    private int accumulatedPoint;

    @OneToMany(mappedBy = "memberPoint", cascade = CascadeType.PERSIST)
    private List<MemberPointItem> memberPointItems = new ArrayList<>();

    @OneToOne(mappedBy = "memberPoint")
    private Member member;

    @Builder
    public MemberPoint(Long id, int availablePoint, int accumulatedPoint, List<MemberPointItem> memberPointItems) {
        this.id = id;
        this.availablePoint = availablePoint;
        this.accumulatedPoint = accumulatedPoint;
        this.memberPointItems = memberPointItems;
    }

    void mappingMember(Member member) {
        this.member = member;
    }

    private void addPoint(int point) {
        this.accumulatedPoint += point;
        this.availablePoint += point;
    }

    private void deductPoint(int point) {
        if (availablePoint < point) {
            throw new IllegalArgumentException("포인트가 부족합니다.");
        }
        this.availablePoint -= point;
    }

    private MemberPointItem createMemberPointItemByMembership(int point) {
        addPoint(point);
        return MemberPointItem.builder()
                .type(MemberPointItem.Type.MEMBERSHIP)
                .point(point)
                .memberPoint(this)
                .build();
    }

    private MemberPointItem createMemberPointItemByPayment(int point) {
        deductPoint(point);
        return MemberPointItem.builder()
                .type(MemberPointItem.Type.PAYMENT)
                .point(-point)
                .memberPoint(this)
                .build();
    }

    public void parseReceipt(Receipt receipt) {
        if (receipt.isUsePoint()) {
            memberPointItems.add(createMemberPointItemByPayment(receipt.getUsePoint()));
        }

        if (receipt.isCashPayment()) {
            int membershipPoint = member.getGrade().calculateMembershipPoint(receipt.calculatePaymentAmount());
            memberPointItems.add(createMemberPointItemByMembership(membershipPoint));
            member.upgradeMemberGrade();
        }
    }
}
