package com.market.ktmarket.member.dto;

import com.market.ktmarket.member.domain.Member;
import com.market.ktmarket.member.domain.enumeration.MemberGrade;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemberDetails {
    private String userId;
    private MemberGrade grade;
    private LocalDateTime joinDate;
    private int availablePoint;
    private int accumulatedPoint;

    public MemberDetails(Member member) {
        this.userId = member.getUserId();
        this.grade = member.getGrade();
        this.joinDate = member.getCreatedDate();
        this.availablePoint = member.getMemberPoint().getAvailablePoint();
        this.accumulatedPoint = member.getMemberPoint().getAccumulatedPoint();
    }
}
