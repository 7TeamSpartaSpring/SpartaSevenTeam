package com.seventeamproject.api.order.dto;

import com.seventeamproject.api.order.entity.Order;
import com.seventeamproject.api.order.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public record OrderNotListResponse(
        Long orderId,
        String orderNumber,
        String customerName,
        String productName,
        Long totalQuantity,
        Long totalAmount,
        LocalDateTime orderedAt,
        OrderStatus status,
        String managerName
) {
    public static OrderNotListResponse from(Order order, String managerName) {
        String productName = order.getItems().isEmpty()
                ? null
                : order.getItems().get(0).getProduct().getName();

        return new OrderNotListResponse(
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


