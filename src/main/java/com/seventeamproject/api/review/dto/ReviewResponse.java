package com.seventeamproject.api.review.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.seventeamproject.api.review.entity.Review;

import java.time.LocalDateTime;

public record ReviewResponse(
        Long id,
        String product,
        String order,
        String customer,
        Long rating,
        String content,
        LocalDateTime createdAt) {

    public ReviewResponse (Review review) {
        this(
                review.getId(),
                review.getProduct().getName(),
                review.getOrder().getOrderNumber(),
                review.getCustomer().getName(),
                review.getRating(),
                review.getContent(),
                review.getCreatedAt()
        );
    }

    @QueryProjection
    public ReviewResponse {
    }
}

