package com.seventeamproject.api.customer.dto;

import com.seventeamproject.api.customer.entity.Customer;
import com.seventeamproject.api.customer.enums.CustomerStatus;

import java.time.LocalDateTime;

public record CustomerResponse(
        Long id,
        String name,
        String email,
        String phone,
        CustomerStatus status,
        LocalDateTime createdAt
) {
    public CustomerResponse(Customer customer){
        this(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getStatus(),
                customer.getCreatedAt()
        );
    }
}
