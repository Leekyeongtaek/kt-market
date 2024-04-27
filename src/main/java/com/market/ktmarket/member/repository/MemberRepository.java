package com.market.ktmarket.member.repository;

import com.market.ktmarket.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUserId(String userId);

    @Query(value = "SELECT m FROM Member m JOIN FETCH m.memberPoint WHERE m.id = :memberId")
    Optional<Member> findByIdJoinFetchMemberPoint(@Param("memberId") Long memberId);
}
