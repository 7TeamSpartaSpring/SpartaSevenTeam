package com.seventeamproject.api.product.category.dto;

import com.seventeamproject.api.product.category.entity.Category;
import com.seventeamproject.api.product.category.enums.CategoryStatusEnum;

import java.time.LocalDateTime;

public record CategoryResponse(Long id,
                               String code,
                               String name,
                               CategoryStatusEnum status,
                               Long createdBy,
                               LocalDateTime createdAt,
                               Long modifiedBy,
                               LocalDateTime modifiedAt
) {
    public CategoryResponse(Category category) {
        this(category.getId(),
                category.getCode(),
                category.getName(),
                category.getStatus(),
                category.getCreatedBy(),
                category.getCreatedAt(),
                category.getModifiedBy(),
                category.getModifiedAt());
    }
}
