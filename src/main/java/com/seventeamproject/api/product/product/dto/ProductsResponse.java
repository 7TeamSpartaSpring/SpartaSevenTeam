package com.seventeamproject.api.product.product.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.seventeamproject.api.product.product.enums.ProductStatus;

import java.time.LocalDateTime;

public record ProductsResponse(Long id,
                               String name,
                               String category,
                               Long price,
                               Long totalQty,
                               ProductStatus status,
                               LocalDateTime createdAt,
                               String adminName) {
    @QueryProjection
    public ProductsResponse {
    }
}
