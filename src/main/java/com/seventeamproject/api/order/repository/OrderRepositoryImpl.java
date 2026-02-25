package com.seventeamproject.api.order.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.seventeamproject.api.customer.entity.QCustomer;
import com.seventeamproject.api.order.entity.Order;
import com.seventeamproject.api.order.enums.OrderStatus;
import com.seventeamproject.api.order.entity.QOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Order> search(Pageable pageable, String keyword, OrderStatus status) {
        QOrder order = QOrder.order;
        QCustomer customer = QCustomer.customer;

        List<Order> content = queryFactory
                .selectFrom(order)
                .join(order.customer, customer)
                .where(
                        keywordContains(keyword, order, customer),
                        statusEq(status, order)
                )
                .orderBy(orderSpecifiers(pageable.getSort(), order))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(order.count())
                .from(order)
                .join(order.customer, customer)
                .where(
                        keywordContains(keyword, order, customer),
                        statusEq(status, order)
                )
                .fetchOne();

        return new PageImpl<>(content, pageable, total == null ? 0 : total);
    }

    private BooleanExpression keywordContains(String keyword, QOrder order, QCustomer customer) {
        if (keyword == null || keyword.isBlank()) {
            return null;
        }
        return order.orderNumber.containsIgnoreCase(keyword)
                .or(customer.name.containsIgnoreCase(keyword));
    }

    private BooleanExpression statusEq(OrderStatus status, QOrder order) {
        return status != null ? order.status.eq(status) : null;
    }

    private OrderSpecifier<?>[] orderSpecifiers(Sort sort, QOrder order) {
        if (sort == null || sort.isUnsorted()) {
            return new OrderSpecifier[]{order.orderedAt.desc()};
        }

        // 정렬 규칙들을 모아둘 리스트 (정렬이 여러 개일 수 있어서 List 사용)
        List<OrderSpecifier<?>> specifiers = new ArrayList<>();

        // 문자열 필드명("orderedAt" 같은 것)을 QueryDSL 경로로 바꿀 때 쓰는 도구
        // Order.class: 주문 엔티티 기준
        // "order": QueryDSL alias 이름
        PathBuilder<Order> entityPath = new PathBuilder<>(Order.class, "order");

        // 요청으로 들어온 정렬 조건들을 하나씩 처리
        for (Sort.Order s : sort) {
            // 예: "quantity", "amount", "orderedAt"
            String property = s.getProperty();

            // 오름차순이면 true, 내림차순이면 false
            boolean asc = s.getDirection().isAscending();

            // quantity 요청이면 DB 필드 totalQuantity로 매핑해서 정렬
            if ("quantity".equalsIgnoreCase(property)) {
                specifiers.add(asc ? order.totalQuantity.asc() : order.totalQuantity.desc());
                continue;
            }

            // amount/totalAmount 요청이면 totalAmount로 정렬
            if ("amount".equalsIgnoreCase(property) || "totalAmount".equalsIgnoreCase(property)) {
                specifiers.add(asc ? order.totalAmount.asc() : order.totalAmount.desc());
                continue;
            }

            // orderedAt 요청이면 orderedAt으로 정렬
            if ("orderedAt".equalsIgnoreCase(property)) {
                specifiers.add(asc ? order.orderedAt.asc() : order.orderedAt.desc());
                continue;
            }

            // 위 화이트리스트에 없는 필드일 때의 fallback 동적 정렬
            // 1) ASC/DESC 방향
            // 2) 문자열 필드명(property)을 정렬 가능한 경로로 변환(entityPath.getComparable)
            specifiers.add(new OrderSpecifier(
                    asc ? com.querydsl.core.types.Order.ASC : com.querydsl.core.types.Order.DESC,
                    entityPath.getComparable(property, Comparable.class)
            ));
        }

        // List -> 배열 변환해서 orderBy(...)에 넘길 수 있게 반환
        // new OrderSpecifier[0]은 "OrderSpecifier 배열 타입으로 변환"하라는 타입 힌트
        return specifiers.toArray(new OrderSpecifier[0]);
    }
}


