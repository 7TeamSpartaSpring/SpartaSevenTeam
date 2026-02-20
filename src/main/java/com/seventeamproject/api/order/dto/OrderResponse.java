package com.seventeamproject.api.order.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.seventeamproject.api.order.entity.Order;

import java.time.LocalDateTime;

public record OrderResponse(
        Long id,
        Long oneId,
        String content,
        Long value,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt) {

    public OrderResponse(Order order) {
        this(order.getId(),
                order.getOne().getId(),
                order.getContent(),
                order.getValue(),
                order.getCreatedAt(),
                order.getModifiedAt());
    }

    @QueryProjection
    public OrderResponse {
    }
}
