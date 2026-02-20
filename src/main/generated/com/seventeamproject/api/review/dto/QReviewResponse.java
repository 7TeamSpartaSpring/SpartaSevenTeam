package com.seventeamproject.api.review.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.seventeamproject.api.review.dto.QReviewResponse is a Querydsl Projection type for ReviewResponse
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QReviewResponse extends ConstructorExpression<ReviewResponse> {

    private static final long serialVersionUID = 1447410072L;

    public QReviewResponse(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<Long> productId, com.querydsl.core.types.Expression<Long> orderId, com.querydsl.core.types.Expression<Long> customerId, com.querydsl.core.types.Expression<Long> rating, com.querydsl.core.types.Expression<String> content, com.querydsl.core.types.Expression<java.time.LocalDateTime> createdAt) {
        super(ReviewResponse.class, new Class<?>[]{long.class, long.class, long.class, long.class, long.class, String.class, java.time.LocalDateTime.class}, id, productId, orderId, customerId, rating, content, createdAt);
    }

}

