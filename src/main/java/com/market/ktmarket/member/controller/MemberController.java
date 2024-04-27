package com.market.ktmarket.member.controller;

import com.market.ktmarket.member.dto.MemberDetails;
import com.market.ktmarket.member.dto.MemberSave;
import com.market.ktmarket.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Iterator;

@RequiredArgsConstructor
@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/")
    public ResponseEntity<Void> memberAdd(@RequestBody MemberSave memberSave) {
        memberService.addMember(memberSave);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<MemberDetails> memberDetail(@PathVariable Long memberId) {
        return new ResponseEntity<>(memberService.findMember(memberId), HttpStatus.OK);
    }
}
