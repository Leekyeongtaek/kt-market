package com.market.ktmarket.product.domain;

import com.market.ktmarket.product.dto.ProductSave;
import com.market.ktmarket.product.dto.ProductUpdate;
import org.junit.jupiter.api.Test;

import static com.market.ktmarket.product.domain.Product.Type.FOOD;
import static org.assertj.core.api.Assertions.assertThat;

class ProductTest {

    @Test
    void ProductSave_객체로_Product_객체를_생성할_수_있다() {
        //given
        ProductSave productSave = new ProductSave("동원)양반죽", 2000, 2, FOOD);

        //when
        Product product = productSave.toEntity();

        //then
        assertThat(product.getName()).isEqualTo("동원)양반죽");
        assertThat(product.getPrice()).isEqualTo(2000);
        assertThat(product.getStock()).isEqualTo(2);
        assertThat(product.getType()).isEqualTo(FOOD);
    }

    @Test
    void ProductUpdate_객체로_Product_객체를_수정할_수_있다() {
        //given
        ProductUpdate productUpdate = new ProductUpdate("동원)양반죽 수정", 2000, 10);

        Product product = Product.builder()
                .name("동원)양반죽")
                .price(1000)
                .stock(5)
                .build();

        //when
        product.update(productUpdate);

        //then
        assertThat(product.getName()).isEqualTo("동원)양반죽 수정");
        assertThat(product.getPrice()).isEqualTo(2000);
        assertThat(product.getStock()).isEqualTo(15);
    }

}