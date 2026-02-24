package com.seventeamproject.api.customer.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.seventeamproject.api.customer.enums.CustomerStatus;

import java.time.LocalDateTime;

public record GetCustomerResponse(
        Long id,
        String name,
        String email,
        String phone,
        CustomerStatus status,
        Long totalOrderCount, // 총 주문수
        Long totalOrderItemCount, // 총 주문 물품 수
        Long totalPayment, // 총 구매 금액
        LocalDateTime createdAt
) {
    @QueryProjection
    public GetCustomerResponse{}
}