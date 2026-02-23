package com.seventeamproject.api.product.product.dto;

import com.seventeamproject.api.product.product.enums.ProductStatusEnum;

public record ProductRequest(String name, Long categoryId, Long price, Long totalQty, ProductStatusEnum status) {
}
