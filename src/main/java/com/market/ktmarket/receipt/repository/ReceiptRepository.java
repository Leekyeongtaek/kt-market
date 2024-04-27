package com.market.ktmarket.receipt.repository;

import com.market.ktmarket.receipt.domain.Receipt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReceiptRepository extends JpaRepository<Receipt, Long> {
    Page<Receipt> findAllByMemberId(Long memberId, Pageable pageable);

    Optional<Receipt> findByIdAndMemberId(Long id, Long memberId);
}
