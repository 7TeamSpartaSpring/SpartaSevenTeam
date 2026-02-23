package com.seventeamproject.api.order.dto;

import com.seventeamproject.api.order.entity.Order;
import com.seventeamproject.api.order.enums.OrderStatus;

import java.time.LocalDateTime;

public record OrderListResponse(
        Long orderId,
        String orderNumber,
        String customerName,
        String productName,
        Long quantity,
        Long totalAmount,
        LocalDateTime orderedAt,
        OrderStatus status,
        String managerName
) {
    public static OrderListResponse from(Order order, String managerName) {
        String productName = order.getItems().isEmpty()
                ? null
                : order.getItems().get(0).getProduct().getName();

        return new OrderListResponse(
                order.getId(),
                order.getOrderNumber(),
                order.getCustomer().getName(),
                productName,
                order.getTotalQuantity(),
                order.getTotalAmount(),
                order.getOrderedAt(),
                order.getStatus(),
                managerName
        );
    }
}


