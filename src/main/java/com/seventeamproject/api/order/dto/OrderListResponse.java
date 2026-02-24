package com.seventeamproject.api.order.dto;

import com.seventeamproject.api.order.entity.Order;
import com.seventeamproject.api.order.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public record OrderListResponse(
        Long orderId,
        String orderNumber,
        String customerName,
        List<OrderItemResponse> items,
        Long totalQuantity,
        Long totalAmount,
        LocalDateTime orderedAt,
        OrderStatus status,
        String managerName
) {
    public static OrderListResponse from(Order order, String managerName) {
        return new OrderListResponse(
                order.getId(),
                order.getOrderNumber(),
                order.getCustomer().getName(),
                order.getItems().stream().map(OrderItemResponse::from).toList(),
                order.getTotalQuantity(),
                order.getTotalAmount(),
                order.getOrderedAt(),
                order.getStatus(),
                managerName
        );
    }
}


