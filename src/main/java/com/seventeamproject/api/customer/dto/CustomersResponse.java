package com.seventeamproject.api.customer.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.seventeamproject.api.customer.entity.CustomerStatus;

import java.time.LocalDateTime;

public record CustomersResponse(
        Long id,
        String name,
        String email,
        String phone,
        CustomerStatus status,
        LocalDateTime createdAt
//        , int totalOrderCount,
//        Long totalPayment
) {
    @QueryProjection
    public CustomersResponse{

    }
}
