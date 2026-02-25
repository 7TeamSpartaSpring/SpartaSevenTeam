package com.seventeamproject.api.order.dto;

import com.seventeamproject.api.admin.entity.Admin;
import com.seventeamproject.api.admin.enums.AdminRoleEnum;
import com.seventeamproject.api.order.entity.Order;
import com.seventeamproject.api.order.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public record GetOneOrderResponse (
        String orderNumber,
        String customerName,
        String customerEmail,
        List<OrderItemResponse> items,
        OrderStatus orderStatus,
        Long createdBy,
        LocalDateTime createdAt,
        Long modifiedBy,
        LocalDateTime modifiedAt,
        String managerName,
        String managerEmail,
        AdminRoleEnum managerRole
){
    public GetOneOrderResponse(Order order, Admin admin){
        this(order.getOrderNumber(),
                order.getCustomer().getName(),
                order.getCustomer().getEmail(),
                order.getItems().stream().map(OrderItemResponse::from).toList(),
                order.getStatus(),
                order.getCreatedBy(),
                order.getCreatedAt(),
                order.getModifiedBy(),
                order.getModifiedAt(),
                admin != null ? admin.getName() : null,
                admin != null ? admin.getEmail() : null,
                admin != null ? admin.getRole() : null
        );
    }
}


