package com.market.ktmarket.product.repository.query;

import com.market.ktmarket.product.domain.Product;
import com.market.ktmarket.product.dto.ProductSearchCondition;
import com.market.ktmarket.product.repository.query.dto.ProductQuery;
import com.market.ktmarket.product.repository.support.Querydsl4RepositorySupport;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.market.ktmarket.product.domain.QProduct.*;

@Repository
public class ProductQueryRepository extends Querydsl4RepositorySupport {

    private JPAQueryFactory queryFactory;

    public ProductQueryRepository(JPAQueryFactory queryFactory) {
        super(Product.class);
        this.queryFactory = queryFactory;
    }

    public Page<ProductQuery> searchProduct(Pageable pageable, ProductSearchCondition searchCondition) {
        Page<Product> result = applyPagination(pageable, contentQuery -> contentQuery
                        .selectFrom(product)
                        .where(productTypeEq(searchCondition.getType()),
                                productNameEq(searchCondition.getName()),
                                productPriceGoe(searchCondition.getMinPrice()),
                                productPriceLoe(searchCondition.getMaxPrice())),
                countQuery -> countQuery
                        .select(product.count()).from(product)
                        .where(productTypeEq(searchCondition.getType()),
                                productNameEq(searchCondition.getName()),
                                productPriceGoe(searchCondition.getMinPrice()),
                                productPriceLoe(searchCondition.getMaxPrice()))
        );

        List<ProductQuery> productQueries = result.getContent().stream().map(ProductQuery::new).toList();

        return new PageImpl<>(productQueries, pageable, result.getTotalElements());
    }

    private BooleanExpression productTypeEq(Product.Type type) {
        return type != null ? product.type.eq(type) : null;
    }

    private BooleanExpression productNameEq(String name) {
        return StringUtils.hasText(name) ? product.name.contains(name) : null;
    }

    private BooleanExpression productPriceGoe(Integer minPrice) {
        return minPrice != null ? product.price.goe(minPrice) : null;
    }

    private BooleanExpression productPriceLoe(Integer maxPrice) {
        return maxPrice != null ? product.price.loe(maxPrice) : null;
    }

}
