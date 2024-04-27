package com.market.ktmarket.product.controller;

import com.market.ktmarket.product.dto.ProductDetails;
import com.market.ktmarket.product.dto.ProductSave;
import com.market.ktmarket.product.dto.ProductSearchCondition;
import com.market.ktmarket.product.repository.query.dto.ProductQuery;
import com.market.ktmarket.product.service.ProductService;
import com.market.ktmarket.product.dto.ProductUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/products")
@RestController
public class ProductController {

    private final ProductService productService;

    @PostMapping("/")
    public ResponseEntity<Void> productAdd(@RequestBody ProductSave productSave) {
        productService.addProduct(productSave);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Void> productModify(@PathVariable Long productId, @RequestBody ProductUpdate productUpdate) {
        productService.modifyProduct(productId, productUpdate);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDetails> productDetails(@PathVariable Long productId) {
        return new ResponseEntity<>(productService.findProduct(productId), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<Page<ProductQuery>> productList(Pageable pageable, ProductSearchCondition searchCondition) {
        return new ResponseEntity<>(productService.findProducts(pageable, searchCondition), HttpStatus.OK);
    }
}
