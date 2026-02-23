package com.seventeamproject.api.product.product.dto;

import com.seventeamproject.api.product.product.entity.Product;
import com.seventeamproject.api.product.product.enums.ProductStatusEnum;

import java.time.LocalDateTime;

public record ProductResponse(Long id,
                              String name,
                              String categoryName,
                              Long price,
                              Long totalQty,
                              ProductStatusEnum status,
                              Long createdBy,
                              LocalDateTime createdAt,
                              Long modifiedBy,
                              LocalDateTime modifiedAt
) {
    public ProductResponse(Product product) {
        this(product.getId(),
                product.getName(),
                product.getCategory().getName(),
                product.getPrice(),
                product.getTotalQty(),
                product.getStatus(),
                product.getCreatedBy(),
                product.getCreatedAt(),
                product.getModifiedBy(),
                product.getModifiedAt());
    }
}
