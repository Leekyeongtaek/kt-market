package com.market.ktmarket.member.service;

import com.market.ktmarket.member.domain.Member;
import com.market.ktmarket.member.domain.MemberPoint;
import com.market.ktmarket.member.dto.MemberDetails;
import com.market.ktmarket.member.dto.MemberSave;
import com.market.ktmarket.member.repository.MemberRepository;
import com.market.ktmarket.receipt.domain.Receipt;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public void addMember(MemberSave memberSave) {
        if (memberRepository.findByUserId(memberSave.getUserId()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }
        memberRepository.save(memberSave.toEntity());
    }

    public void parseReceipt(Receipt receipt) {
        Member member = memberRepository.findByIdJoinFetchMemberPoint(receipt.getMemberId()).orElseThrow(IllegalArgumentException::new);
        MemberPoint memberPoint = member.getMemberPoint();
        memberPoint.parseReceipt(receipt);
    }

    public MemberDetails findMember(Long memberId) {
        Member member = memberRepository.findByIdJoinFetchMemberPoint(memberId).orElseThrow(IllegalAccessError::new);
        return new MemberDetails(member);
    }
}
