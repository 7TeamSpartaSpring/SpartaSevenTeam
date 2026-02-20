package com.seventeamproject.api.review.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.seventeamproject.api.review.entity.Review;

import java.time.LocalDateTime;

public record ReviewResponse(
        Long id,
        Long productId,
        Long orderId,
        Long customerId,
        Long reviewQty,
        Long rating,
        String content,
        LocalDateTime createdAt) {

    // Entity -> DTO 변환 생성자
    public ReviewResponse (Review review) {
        this(
                review.getId(),
                review.getProduct(),   // productId 위치 (Long)
                review.getOrder(),     // orderId 위치 (Long)
                review.getCustomer(),  // customerId 위치 (Long)
                review.getReviewQty(),
                review.getRating(),
                review.getContent(),
                review.getCreatedAt()
        );
    }

    @QueryProjection
    public ReviewResponse {
    }
}

