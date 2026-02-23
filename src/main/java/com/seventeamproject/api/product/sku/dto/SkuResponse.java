package com.seventeamproject.api.product.sku.dto;

import com.seventeamproject.api.admin.entity.Admin;
import com.seventeamproject.api.product.sku.entity.Sku;
import com.seventeamproject.api.product.sku.enums.SkuStatusEnum;

import java.time.LocalDateTime;

public record SkuResponse(Long id,
                          String productName,
                          String adminName,
                          Long price,
                          SkuStatusEnum status,
                          Long createdBy,
                          LocalDateTime createdAt,
                          Long modifiedBy,
                          LocalDateTime modifiedAt) {
    public SkuResponse(Sku sku) {
        this(
                sku.getId(),
                sku.getProduct().getName(),
                sku.getAdmin().getName(),
                sku.getPrice(),
                sku.getStatus(),
                sku.getCreatedBy(),
                sku.getCreatedAt(),
                sku.getModifiedBy(),
                sku.getModifiedAt());
    }
}
