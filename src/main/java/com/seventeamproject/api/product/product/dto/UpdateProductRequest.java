package com.seventeamproject.api.product.product.dto;

import com.seventeamproject.api.product.product.enums.ProductStatus;

public record UpdateProductRequest(String name, Long categoryId, Long price) {
}
