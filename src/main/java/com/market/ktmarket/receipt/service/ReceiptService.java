package com.market.ktmarket.receipt.service;

import com.market.ktmarket.member.service.MemberService;
import com.market.ktmarket.receipt.domain.Receipt;
import com.market.ktmarket.receipt.dto.ProductCart;
import com.market.ktmarket.receipt.dto.ReceiptDetails;
import com.market.ktmarket.receipt.dto.ReceiptList;
import com.market.ktmarket.receipt.repository.ReceiptRepository;
import com.market.ktmarket.receipt.service.component.ProductCounter;
import com.market.ktmarket.receipt.service.component.ReceiptValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class ReceiptService {

    private final ReceiptRepository receiptRepository;
    private final ProductCounter productCounter;
    private final ReceiptValidator receiptValidator;
    private final MemberService memberService;

    public void issueReceipt(ProductCart productCart) {
        Receipt receipt = productCounter.unpackProductCart(productCart);
        receiptValidator.validate(receipt);
        receipt.payed();
        receiptRepository.save(receipt);
        memberService.parseReceipt(receipt);
    }

    public ReceiptDetails findReceipt(Long receiptId, Long memberId) {
        Receipt receipt = receiptRepository.findByIdAndMemberId(receiptId, memberId).orElseThrow(IllegalArgumentException::new);
        return new ReceiptDetails(receipt);
    }

    public Page<ReceiptList> findReceipts(Long memberId, Pageable pageable) {
        Page<Receipt> receipts = receiptRepository.findAllByMemberId(memberId, pageable);
        List<ReceiptList> receiptLists = receipts.getContent().stream().map(ReceiptList::new).toList();
        return new PageImpl<>(receiptLists, pageable, receipts.getTotalElements());
    }
}
