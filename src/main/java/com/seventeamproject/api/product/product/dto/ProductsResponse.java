package com.seventeamproject.api.product.product.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.seventeamproject.api.product.product.enums.ProductStatusEnum;

import java.time.LocalDateTime;

public record ProductsResponse(Long id,
                               String name,
                               String category,
                               Long price,
                               Long totalQty,
                               ProductStatusEnum status,
                               LocalDateTime createdAt,
                               String adminName) {
    @QueryProjection
    public ProductsResponse {
    }
}
