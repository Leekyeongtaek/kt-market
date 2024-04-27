package com.market.ktmarket.member.dto;

import com.market.ktmarket.member.domain.Member;
import com.market.ktmarket.member.domain.MemberPoint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberSave {

    private String userId;
    private String password;

    MemberSave(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public Member toEntity() {
        return new Member(userId, password, MemberPoint.builder().build());
    }
}
