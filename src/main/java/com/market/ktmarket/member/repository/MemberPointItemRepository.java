package com.market.ktmarket.member.repository;

import com.market.ktmarket.member.domain.MemberPointItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberPointItemRepository extends JpaRepository<MemberPointItem, Long> {
}
