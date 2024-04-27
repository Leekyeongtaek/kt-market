package com.market.ktmarket.product.repository.query;

import com.market.ktmarket.config.TestConfig;
import com.market.ktmarket.product.domain.Product;
import com.market.ktmarket.product.dto.ProductSearchCondition;
import com.market.ktmarket.product.repository.ProductRepository;
import com.market.ktmarket.product.repository.query.dto.ProductQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static com.market.ktmarket.product.domain.Product.Type.DRINK;
import static com.market.ktmarket.product.domain.Product.Type.SNACK;
import static org.assertj.core.api.Assertions.assertThat;

@Import(TestConfig.class)
@DataJpaTest
class ProductQueryRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductQueryRepository productQueryRepository;

    @BeforeEach
    void setUp() {
        Product product1 = Product.builder()
                .name("삼립)오븐에구운도넛")
                .type(Product.Type.FOOD)
                .price(1200)
                .build();

        Product product2 = Product.builder()
                .name("롯데)도리토스나쵸치즈")
                .type(SNACK)
                .price(1700)
                .build();

        Product product3 = Product.builder()
                .name("농심)양파링")
                .type(SNACK)
                .price(1500)
                .build();

        Product product4 = Product.builder()
                .name("매일)우유속에딸기")
                .type(DRINK)
                .price(1900)
                .build();

        Product product5 = Product.builder()
                .name("매일)우유속에코코아")
                .type(DRINK)
                .price(1900)
                .build();

        productRepository.saveAll(List.of(product1, product2, product3, product4, product5));
    }

    @Test
    void 상품_검색시_상품명으로_상품을_검색할_수_있다() {
        //given
        PageRequest pageRequest = PageRequest.of(0, 10);
        ProductSearchCondition searchCondition = new ProductSearchCondition("매일", null, null, null);

        //when
        List<ProductQuery> content = productQueryRepository.searchProduct(pageRequest, searchCondition).getContent();

        //then
        assertThat(content.stream().allMatch(p -> p.getName().contains("우유"))).isTrue();
    }

    @Test
    void 상품_검색시_상품_가격으로_상품을_검색할_수_있다() {
        //given
        PageRequest pageRequest = PageRequest.of(0, 10);
        ProductSearchCondition searchCondition = new ProductSearchCondition(null, 1200, 1700, null);

        //when
        List<ProductQuery> content = productQueryRepository.searchProduct(pageRequest, searchCondition).getContent();

        //then
        assertThat(content.stream().allMatch(p -> p.getPrice() >= 1200 && p.getPrice() <= 1700)).isTrue();
    }

    @Test
    void 상품_검색시_상품_타입으로_상품을_검색할_수_있다() {
        //given
        PageRequest pageRequest = PageRequest.of(0, 10);
        ProductSearchCondition searchCondition = new ProductSearchCondition(null, null, null, SNACK);

        //when
        List<ProductQuery> content = productQueryRepository.searchProduct(pageRequest, searchCondition).getContent();

        //then
        assertThat(content.stream().allMatch(p -> p.getType().equals(SNACK))).isTrue();
    }
}