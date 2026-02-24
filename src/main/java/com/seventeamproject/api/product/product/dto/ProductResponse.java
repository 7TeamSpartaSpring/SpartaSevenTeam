package com.seventeamproject.api.product.product.dto;

import com.seventeamproject.api.product.product.entity.Product;
import com.seventeamproject.api.product.product.enums.ProductStatus;
import com.seventeamproject.api.review.dto.ProductReviewSummaryResponse;

import java.time.LocalDateTime;

public record ProductResponse(Long id,
                              String name,
                              String categoryName,
                              Long price,
                              ProductStatus status,
                              String adminEmail,
                              ProductReviewSummaryResponse reviews,
                              LocalDateTime createdAt
) {
    public ProductResponse(Product product) {
        this(product.getId(),
                product.getName(),
                product.getCategory().getName(),
                product.getPrice(),
                product.getStatus(),
                product.getAdmin().getEmail(),
                null,
                product.getCreatedAt());
    }
    public ProductResponse(Product product, ProductReviewSummaryResponse reviews) {
        this(product.getId(),
                product.getName(),
                product.getCategory().getName(),
                product.getPrice(),
                product.getStatus(),
                product.getAdmin().getEmail(),
                reviews,
                product.getCreatedAt());
    }
}
