package com.seventeamproject.api.review.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.seventeamproject.api.review.entity.Review;

import java.time.LocalDateTime;

public record ReviewResponse(
        Long id,
        Long productId,
        Long orderId,
        Long customerId,
        Long rating,
        String content,
        LocalDateTime createdAt) {
    public ReviewResponse (Review review) {
        this(
                review.getId(),
                review.getProduct(),
                review.getOrderId(),
                review.getCustomer(),
                review.getRating(),
                review.getContent(),
                review.getCreatedAt()
        );
    }

    @QueryProjection
    public ReviewResponse {
    }
}
