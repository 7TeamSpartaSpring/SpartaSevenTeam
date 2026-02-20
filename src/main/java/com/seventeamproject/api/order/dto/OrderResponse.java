package com.seventeamproject.api.order.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.seventeamproject.api.order.entity.Order;
import com.seventeamproject.api.order.entity.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
        Long orderId,
        String orderNumber,
        OrderStatus orderStatus,
        LocalDateTime orderedAt,
        Long totalAmount,
        List<OrderItemResponse> items,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {

    public OrderResponse(Order order) {
        this(order.getId(),
                order.getOrderNumber(),
                order.getStatus(),
                order.getOrderedAt(),
                order.getTotalAmount(),
                order.getItems().stream().map(OrderItemResponse::from).toList(),
                order.getCreatedAt(),
                order.getModifiedAt());

    }

    @QueryProjection
    public OrderResponse {
    }
}
