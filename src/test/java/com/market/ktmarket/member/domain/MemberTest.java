package com.market.ktmarket.member.domain;

import com.market.ktmarket.member.domain.enumeration.MemberGrade;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.market.ktmarket.member.domain.enumeration.MemberGrade.*;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class MemberTest {

    @Test
    void 회원등급이_BRONZE이고_누적포인트가_1000이상이_되면_멤버십_업그레이드시_SILVER_등급이_된다() {
        //given
        MemberPoint memberPoint = MemberPoint.builder()
                .memberPointItems(List.of(MemberPointItem.builder().build()))
                .accumulatedPoint(1000)
                .build();

        Member member = Member.builder()
                .memberPoint(memberPoint)
                .grade(BRONZE)
                .build();

        //when
        member.upgradeMemberGrade();

        //then
        assertThat(member.getGrade()).isEqualTo(SILVER);
    }

    @Test
    void 회원등급이_SILVER이고_누적포인트가_2000이상이_되면_멤버십_업그레이드시_GOLD_등급이_된다() {
        //given
        MemberPoint memberPoint = MemberPoint.builder()
                .memberPointItems(List.of(MemberPointItem.builder().build()))
                .accumulatedPoint(2000)
                .build();

        Member member = Member.builder()
                .memberPoint(memberPoint)
                .grade(SILVER)
                .build();

        //when
        member.upgradeMemberGrade();

        //then
        assertThat(member.getGrade()).isEqualTo(GOLD);
    }

}