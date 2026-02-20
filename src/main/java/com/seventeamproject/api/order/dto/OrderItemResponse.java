package com.seventeamproject.api.order.dto;

import com.seventeamproject.api.order.entity.OrderItem;

public record OrderItemResponse(
        Long productId,
        String productName,
        Long quantity,
        Long orderPrice,
        Long totalAmount
) {
    public static OrderItemResponse from(OrderItem orderItem) {
        return new OrderItemResponse(
                orderItem.getProduct().getId(),
                orderItem.getProduct().getName(),
                orderItem.getQuantity(),
                orderItem.getOrderPrice(),
                orderItem.getTotalAmount());
    }
}
