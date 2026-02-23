package com.seventeamproject.api.customer.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.seventeamproject.api.customer.dto.CustomersResponse;
import com.seventeamproject.api.customer.dto.GetCustomerResponse;
import com.seventeamproject.api.customer.dto.QCustomersResponse;
import com.seventeamproject.api.customer.dto.QGetCustomerResponse;
import com.seventeamproject.api.customer.enums.CustomerStatus;
import com.seventeamproject.api.customer.entity.QCustomer;
import com.seventeamproject.api.order.entity.QOrder;
import com.seventeamproject.api.order.entity.QOrderItem;
import com.seventeamproject.api.order.enums.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class CustomerRepositoryImpl implements CustomerRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    QCustomer customer = QCustomer.customer;
    QOrder orderList = QOrder.order;
    QOrderItem orderItem = QOrderItem.orderItem;

    @Override
    public Page<CustomersResponse> search(
            Pageable pageable,
            String keyword,
            CustomerStatus stat
    ) {
        BooleanBuilder builder = new BooleanBuilder();

        if (keyword != null && !keyword.isBlank()) {
            builder.and(
                    customer.name.containsIgnoreCase(keyword)
                            .or(customer.email.containsIgnoreCase(keyword))
            );
        }

        if (stat != null) {
            builder.and(customer.status.eq(stat));
        }

        JPAQuery<CustomersResponse> query = queryFactory
                .select(new QCustomersResponse(
                        customer.id,
                        customer.name,
                        customer.email,
                        customer.phone,
                        customer.status,
                        totalOrderCountSubQuery,
                        totalOrderItemCountSubQuery,
                        totalPaymentSubQuery,
                        customer.createdAt
                ))
                .from(customer)
                .where(builder);

        for (Sort.Order order : pageable.getSort()) {
            query.orderBy(getOrderSpecifier(order));
        }

        List<CustomersResponse> contents = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        Long total = queryFactory
                .select(customer.count())
                .from(customer)
                .where(builder)
                .fetchOne();

        return new PageImpl<>(contents, pageable, total);
    }

    @Override
    public GetCustomerResponse searchOne(Long id) {
        GetCustomerResponse result = queryFactory
                .select(new QGetCustomerResponse(
                        customer.id,
                        customer.name,
                        customer.email,
                        customer.phone,
                        customer.status,
                        totalOrderCountSubQuery,
                        totalOrderItemCountSubQuery,
                        totalPaymentSubQuery,
                        customer.createdAt
                ))
                .from(customer)
                .where(customer.id.eq(id))
                .fetchOne();
        return result;
    }

    private OrderSpecifier<?> getOrderSpecifier(Sort.Order order) {
        Order direction = order.isAscending()
                ? Order.ASC
                : Order.DESC;

        switch (order.getProperty()) {
            case "name":
                return new OrderSpecifier<>(direction, customer.name);
            case "email":
                return new OrderSpecifier<>(direction, customer.email);
            case "phone":
                return new OrderSpecifier<>(direction,customer.phone);
            case "createdAt":
                return new OrderSpecifier<>(direction, customer.createdAt);
            default:
                return new OrderSpecifier<>(Order.ASC, customer.createdAt);
        }
    }

    //총 주문 금액 계산
    Expression<Long> totalPaymentSubQuery =
            JPAExpressions
                    .select(orderItem.totalAmount.sum().coalesce(0L))
                    .from(orderItem)
                    .join(orderItem.order,orderList)
                    .where(
                            orderItem.order.customer.id.eq(customer.id)
                                    .and(orderList.status.ne(OrderStatus.CANCELED))
                    );

    // 총 주문 횟수 계산
    Expression<Long> totalOrderCountSubQuery =
            JPAExpressions
                    .select(orderList.count())
                    .from(orderList)
                    .where(
                            orderList.customer.id.eq(customer.id)
                                    .and(orderList.status.ne(OrderStatus.CANCELED))
                    );

    // 총 주문한 물품 개수 계산
    Expression<Long> totalOrderItemCountSubQuery =
            JPAExpressions
                    .select(orderItem.quantity.sum().coalesce(0L))
                    .from(orderItem)
                    .join(orderItem.order,orderList)
                    .where(
                            orderItem.order.customer.id.eq(customer.id)
                                    .and(orderList.status.ne(OrderStatus.CANCELED))
                    );

}
