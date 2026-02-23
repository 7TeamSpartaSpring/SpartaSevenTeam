package com.seventeamproject.api.product.product.repository;

import com.querydsl.core.types.Expression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.seventeamproject.api.admin.entity.QAdmin;
import com.seventeamproject.api.product.category.entity.QCategory;
import com.seventeamproject.api.product.inventory.entity.QInventory;
import com.seventeamproject.api.product.product.dto.ProductsResponse;
import com.seventeamproject.api.product.product.dto.QProductsResponse;
import com.seventeamproject.api.product.product.entity.QProduct;
import com.seventeamproject.api.product.product.enums.ProductStatusEnum;
import com.seventeamproject.common.querydsl.QuerydslUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ProductsResponse> search(Pageable pageable, String name, Long categoryId, ProductStatusEnum status) {
        QProduct product = QProduct.product;
        QCategory category = QCategory.category;
        QAdmin admin = QAdmin.admin;
        QInventory inventory = QInventory.inventory;

        Map<String, Expression<?>> sortMap = Map.of(
                "price", product.price,
                "createdAt", product.createdAt,
                "status", product.status,
                "adminName", admin.name,
                "id", product.id,
                "name", product.name
        );

        List<ProductsResponse> content = queryFactory
                .select(new QProductsResponse(
                        product.id,
                        product.name,
                        category.name,
                        product.price,
                        JPAExpressions
                                .select(inventory.qty.sum())
                                .from(inventory)
                                .where(inventory.product.id.eq(product.id)),
                        product.status,
                        product.createdAt,
                        admin.name
                ))
                .from(product)
                .leftJoin(category).on(product.category.id.eq(category.id))
                .leftJoin(admin).on(product.createdBy.eq(admin.id))
                .where(QuerydslUtils.like(QProduct.product.name, name),
                        QuerydslUtils.eq(QCategory.category.id, categoryId),
                        QuerydslUtils.eq(QProduct.product.status, status))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(QuerydslUtils.getSort(pageable.getSort(), sortMap, product.id.desc()))
                .fetch();
        Long total = queryFactory
                .select(product.count())
                .from(product)
                .where(QuerydslUtils.like(QProduct.product.name, name),
                        QuerydslUtils.eq(QCategory.category.id, categoryId),
                        QuerydslUtils.eq(QProduct.product.status, status))
                .fetchOne();
        return new PageImpl<>(content, pageable, total);
    }
}
