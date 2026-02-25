package com.seventeamproject.api.dashboard.dto;

public record CategoryProductCount(
        String categoryName,
        Long productCount
) {
}