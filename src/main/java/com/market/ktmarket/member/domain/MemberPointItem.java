package com.market.ktmarket.member.domain;

import com.market.ktmarket.common.AuditingTime;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member_point_item")
@Entity
public class MemberPointItem extends AuditingTime {

    public enum Type {MEMBERSHIP, PAYMENT}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_point_item_id")
    private Long id;

    @Column(name = "type", nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(name = "point")
    private int point;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_point_id", nullable = false, updatable = false)
    private MemberPoint memberPoint;

    @Builder
    public MemberPointItem(Long id, Type type, int point, MemberPoint memberPoint) {
        this.id = id;
        this.type = type;
        this.point = point;
        this.memberPoint = memberPoint;
    }
}
