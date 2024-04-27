package com.market.ktmarket.receipt.service.component;

import com.market.ktmarket.receipt.domain.Receipt;
import com.market.ktmarket.receipt.domain.ReceiptItem;
import com.market.ktmarket.product.domain.Product;
import com.market.ktmarket.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;

@RequiredArgsConstructor
@Component
public class ReceiptValidator {

    private final ProductRepository productRepository;

    public void validate(Receipt receipt) {
        List<Long> productIds = receipt.getReceiptItems().stream().map(ReceiptItem::getProductId).toList();
        Map<Long, Product> productMap = productRepository.findAllById(productIds).stream().collect(Collectors.toMap(Product::getId, identity()));
        validateSize(receipt);
        receipt.validatePointUsePolicy();
        validate(receipt, productMap);
    }

    void validateSize(Receipt receipt) {
        if (receipt.getReceiptItems().isEmpty()) {
            throw new IllegalArgumentException("영수증 품목이 비어있습니다.");
        }
    }

    void validate(Receipt receipt, Map<Long, Product> productMap) {
        for (ReceiptItem receiptItem : receipt.getReceiptItems()) {
            Product product = productMap.get(receiptItem.getProductId());
            if (product == null) {
                throw new IllegalArgumentException("존재하지 않는 상품 번호입니다.");
            }
            if (!receiptItem.getProductName().equals(product.getName())) {
                throw new IllegalArgumentException("상품명이 변경되었습니다.");
            }
            if (receiptItem.getProductPrice() != product.getPrice()) {
                throw new IllegalArgumentException("가격이 변경되었습니다.");
            }
        }
    }
}
