package com.seventeamproject.api.order.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.seventeamproject.api.customer.entity.QCustomer;
import com.seventeamproject.api.order.entity.Order;
import com.seventeamproject.api.order.entity.OrderStatus;
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

        List<OrderSpecifier<?>> specifiers = new ArrayList<>();
        PathBuilder<Order> entityPath = new PathBuilder<>(Order.class, "order");

        for (Sort.Order s : sort) {
            String property = s.getProperty();
            boolean asc = s.getDirection().isAscending();

            if ("quantity".equalsIgnoreCase(property)) {
                specifiers.add(asc ? order.totalQuantity.asc() : order.totalQuantity.desc());
                continue;
            }
            if ("amount".equalsIgnoreCase(property) || "totalAmount".equalsIgnoreCase(property)) {
                specifiers.add(asc ? order.totalAmount.asc() : order.totalAmount.desc());
                continue;
            }
            if ("orderedAt".equalsIgnoreCase(property)) {
                specifiers.add(asc ? order.orderedAt.asc() : order.orderedAt.desc());
                continue;
            }

            specifiers.add(new OrderSpecifier(
                    asc ? com.querydsl.core.types.Order.ASC : com.querydsl.core.types.Order.DESC,
                    entityPath.getComparable(property, Comparable.class)
            ));
        }

        return specifiers.toArray(new OrderSpecifier[0]);
    }
}
