//package com.seventeamproject.api.customer.repository;
//
//import com.querydsl.core.types.dsl.BooleanExpression;
//import com.querydsl.jpa.JPAExpressions;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import com.seventeamproject.api.customer.dto.CustomerResponse;
//import com.seventeamproject.api.customer.entity.QCustomer;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.Pageable;
//
//import java.util.List;
//
//@RequiredArgsConstructor
//public class CustomRepositoryImpl implements CustomerRespositoryCustom {
//    private final JPAQueryFactory queryFactory;
//
//    @Override
//    public Page<CustomersResponse> search(Pageable pageable, String title) {
//
//        QCustomer customer = QCustomer.customer;
//        QOrder order = QOrder.order;
//
//        List<CustomersResponse> content = queryFactory
//                .select(new QCustomersResponse(
//                        customer.id,
//                        customer.title,
//                        customer.content,
//                        JPAExpressions
//                                .select(order.count())
//                                .from(order)
//                                .where(order.customer.id.eq(customer.id)),
//                        JPAExpressions
//                                .select(order.value.sum())
//                                .from(order)
//                                .where(order.customer.id.eq(customer.id)),
//                        customer.createdAt,
//                        customer.modifiedAt
//                ))
//                .from(customer)
////                .leftJoin(user).on(one.userId.eq(user.id))
//                .where(titleContains(title))
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .fetch();
//        Long total = queryFactory
//                .select(customer.count())
//                .from(customer)
//                .where(titleContains(title))
//                .fetchOne();
//
//        return new PageImpl<>(content, pageable, total);
//    }
//
//    private BooleanExpression titleContains(String title) {
//        return title != null ? QCustomer.customer.title.contains(title) : null;
//    }
//}
