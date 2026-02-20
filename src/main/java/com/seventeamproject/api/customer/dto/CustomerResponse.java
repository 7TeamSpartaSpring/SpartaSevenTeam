package com.seventeamproject.api.customer.dto;

import com.seventeamproject.api.customer.entity.Customer;
import com.seventeamproject.api.customer.entity.CustomerStatus;

import java.time.LocalDateTime;

public record CustomerResponse(
        Long id,
        String name,
        String email,
        String phone,
        CustomerStatus status,
        LocalDateTime createdAt
//        , int totalOrderCount,
//        Long totalPayment
) {
    public CustomerResponse(Customer customer){
        this(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getStatus(),
                customer.getCreatedAt()
//                , customer.getTotalOrderCount(),
//                customer.getTotalPayment()
        );
    }

}
