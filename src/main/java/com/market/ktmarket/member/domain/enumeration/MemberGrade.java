package com.market.ktmarket.member.domain.enumeration;

import lombok.Getter;

@Getter
public enum MemberGrade {
    BRONZE(0.01), SILVER(0.03), GOLD(0.05);

    private final double accumulatedPercent;

    MemberGrade(double accumulatedPercent) {
        this.accumulatedPercent = accumulatedPercent;
    }

    public int calculateMembershipPoint(int paymentAmount) {
        return (int) (accumulatedPercent * paymentAmount);
    }
}
