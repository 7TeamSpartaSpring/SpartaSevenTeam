package com.seventeamproject.api.customer.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.seventeamproject.api.customer.dto.CustomersResponse;
import com.seventeamproject.api.customer.dto.QCustomersResponse;
import com.seventeamproject.api.customer.entity.CustomerStatus;
import com.seventeamproject.api.customer.entity.QCustomer;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

import static com.seventeamproject.api.customer.entity.QCustomer.customer;


@RequiredArgsConstructor
public class CustomerRepositoryImpl implements CustomerRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<CustomersResponse> search(
            Pageable pageable,
            String keyword,
            CustomerStatus stat
    ) {
        QCustomer customer = QCustomer.customer;
//        QOrder orderList = QOrder.order;

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
//                        JPAExpressions
//                                .select(orderList.count())
//                                .from(orderList)
//                                .where(orderList.customer.id.eq(customer.id)),
//                        JPAExpressions
//                                .select(orderList.value.sum())
//                                .from(orderList)
//                                .where(orderList.customer.id.eq(customer.id)),
                        customer.createdAt
                ))
                .from(customer)
                .where(builder
//                        keywordContains(keyword),statusEqual(status)
                );

        for (Sort.Order order : pageable.getSort()) {
            query.orderBy(getOrderSpecifier(order));
        }

        List<CustomersResponse> content = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        Long total = queryFactory
                .select(customer.count())
                .from(customer)
                .where(builder
//                        keywordContains(keyword),statusEqual(status)
                )
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }
//
//    private BooleanExpression keywordContains(String keyword) {
//        return keyword != null ?
//                customer.name.contains(keyword)
//                        .or(customer.email.contains(keyword)) : null;
//    }
//
//    private BooleanExpression statusEqual(CustomerStatus status){
//        return status != null ? customer.status.eq(status) : null;
//    }

    private OrderSpecifier<?> getOrderSpecifier(Sort.Order order) {

        QCustomer customer = QCustomer.customer;

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
}
