package com.seventeamproject.api.product.product.dto;

import com.seventeamproject.api.product.product.entity.Product;
import com.seventeamproject.api.product.product.enums.ProductStatus;
import com.seventeamproject.api.review.dto.ProductReviewSummaryResponse;

import java.time.LocalDateTime;

public record ProductResponse(Long id,
                              String name,
                              String categoryName,
                              Long price,
                              Long qty,
                              ProductStatus status,
                              String adminEmail,
                              ProductReviewSummaryResponse reviews,
                              LocalDateTime createdAt
) {
    public ProductResponse(Product product, Long qty) {
        this(product.getId(),
                product.getName(),
                product.getCategory().getName(),
                product.getPrice(),
                qty,
                product.getStatus(),
                product.getAdmin().getEmail(),
                null,
                product.getCreatedAt());
    }
    public ProductResponse(Product product, Long qty, ProductReviewSummaryResponse reviews) {
        this(product.getId(),
                product.getName(),
                product.getCategory().getName(),
                product.getPrice(),
                qty,
                product.getStatus(),
                product.getAdmin().getEmail(),
                reviews,
                product.getCreatedAt());
    }
}
