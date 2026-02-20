package com.seventeamproject.api.customer.dto;

import com.querydsl.core.annotations.QueryProjection;

import java.time.LocalDateTime;

public record CustomersResponse(
        Long id,
        String name,
        String email,
        String phone,
        String status,
        LocalDateTime createdAt
//        , int totalOrderCount,
//        Long totalPayment
) {
    @QueryProjection
    public CustomersResponse{

    }
}
