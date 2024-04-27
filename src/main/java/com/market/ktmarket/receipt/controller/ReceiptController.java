package com.market.ktmarket.receipt.controller;

import com.market.ktmarket.receipt.dto.ProductCart;
import com.market.ktmarket.receipt.dto.ReceiptDetails;
import com.market.ktmarket.receipt.dto.ReceiptList;
import com.market.ktmarket.receipt.service.ReceiptService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/receipts")
public class ReceiptController {

    private final ReceiptService receiptService;

    @PostMapping("/")
    public ResponseEntity<Void> issueReceipt(@RequestBody ProductCart productCart) {
        receiptService.issueReceipt(productCart);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/details/{receiptId}/{memberId}")
    public ResponseEntity<ReceiptDetails> receiptDetails(@PathVariable Long receiptId, @PathVariable Long memberId) {
        return new ResponseEntity<>(receiptService.findReceipt(receiptId, memberId), HttpStatus.OK);
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<Page<ReceiptList>> receiptList(@PathVariable Long memberId, Pageable pageable) {
        return new ResponseEntity<>(receiptService.findReceipts(memberId, pageable), HttpStatus.OK);
    }
}
