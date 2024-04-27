package com.market.ktmarket.member.domain;

import com.market.ktmarket.common.AuditingTime;
import com.market.ktmarket.member.domain.enumeration.MemberGrade;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.market.ktmarket.member.domain.enumeration.MemberGrade.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
@Entity
public class Member extends AuditingTime {

    public enum Role {ROLE_USER, ROLE_ADMIN}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "user_id", unique = true, nullable = false)
    private String userId;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "grade", nullable = false)
    private MemberGrade grade;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "member_point_id", nullable = false)
    private MemberPoint memberPoint;

    public Member(String userId, String password, MemberPoint memberPoint) {
        this(null, userId, password, Role.ROLE_USER, BRONZE, memberPoint);
    }

    @Builder
    public Member(Long id, String userId, String password, Role role, MemberGrade grade, MemberPoint memberPoint) {
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.role = role;
        this.grade = grade;
        mappingMemberPoint(memberPoint);
    }

    private void mappingMemberPoint(MemberPoint memberPoint) {
        this.memberPoint = memberPoint;
        memberPoint.mappingMember(this);
    }

    void upgradeMemberGrade() {
        if (grade == GOLD) {
            return;
        }
        if (memberPoint.getAccumulatedPoint() >= 1000 && memberPoint.getAccumulatedPoint() < 2000) {
            this.grade = SILVER;
        } else if (memberPoint.getAccumulatedPoint() >= 2000) {
            this.grade = GOLD;
        }
    }

}
