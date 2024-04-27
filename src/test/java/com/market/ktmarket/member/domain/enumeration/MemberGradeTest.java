package com.market.ktmarket.member.domain.enumeration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MemberGradeTest {

    @ValueSource(ints = 10000)
    @ParameterizedTest(name = "paymentAmount")
    void 회원등급이_BRONZE라면_결제금액의_1퍼센트를_멤버십_포인트로_반환(int paymentAmount) {
        //given
        MemberGrade bronze = MemberGrade.BRONZE;

        //when
        int membershipPoint = bronze.calculateMembershipPoint(paymentAmount);

        //then
        Assertions.assertThat(membershipPoint).isEqualTo(100);
    }

    @ValueSource(ints = 10000)
    @ParameterizedTest(name = "paymentAmount")
    void 회원등급이_SILVER라면_결제금액의_3퍼센트를_멤버십_포인트로_반환(int paymentAmount) {
        //given
        MemberGrade silver = MemberGrade.SILVER;

        //when
        int membershipPoint = silver.calculateMembershipPoint(paymentAmount);

        //then
        Assertions.assertThat(membershipPoint).isEqualTo(300);
    }

    @ValueSource(ints = 10000)
    @ParameterizedTest(name = "paymentAmount")
    void 회원등급이_GOLD라면_결제금액의_5퍼센트를_멤버십_포인트로_반환(int paymentAmount) {
        //given
        MemberGrade gold = MemberGrade.GOLD;

        //when
        int membershipPoint = gold.calculateMembershipPoint(paymentAmount);

        //then
        Assertions.assertThat(membershipPoint).isEqualTo(500);
    }
}