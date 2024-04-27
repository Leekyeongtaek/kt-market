package com.market.ktmarket.product.service;

import com.market.ktmarket.product.dto.ProductDetails;
import com.market.ktmarket.product.dto.ProductSave;
import com.market.ktmarket.product.domain.Product;
import com.market.ktmarket.product.dto.ProductSearchCondition;
import com.market.ktmarket.product.repository.ProductRepository;
import com.market.ktmarket.product.dto.ProductUpdate;
import com.market.ktmarket.product.repository.query.ProductQueryRepository;
import com.market.ktmarket.product.repository.query.dto.ProductQuery;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Transactional
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductQueryRepository productQueryRepository;

    public void addProduct(ProductSave productSave) {
        Product product = productSave.toEntity();
        if (productRepository.findByName(product.getName()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 상품명입니다.");
        }
        productRepository.save(product);
    }

    public void modifyProduct(Long productId, ProductUpdate productUpdate) {
        Product product = productRepository.findById(productId).orElseThrow(IllegalArgumentException::new);
        product.update(productUpdate);
    }

    public ProductDetails findProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(IllegalArgumentException::new);
        return new ProductDetails(product);
    }

    public Page<ProductQuery> findProducts(Pageable pageable, ProductSearchCondition searchCondition) {
        return productQueryRepository.searchProduct(pageable, searchCondition);
    }
}
