package com.seventeamproject.api.customer.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.seventeamproject.api.customer.entity.Customer;
import com.seventeamproject.api.customer.enums.CustomerStatus;

import java.time.LocalDateTime;

public record GetCustomerResponse(
        Long id,
        String name,
        String email,
        String phone,
        CustomerStatus status,
        Long totalOrderCount,
        Long totalOrderItemCount,
        Long totalPayment,
        LocalDateTime createdAt
) {
    @QueryProjection
    public GetCustomerResponse{}
}