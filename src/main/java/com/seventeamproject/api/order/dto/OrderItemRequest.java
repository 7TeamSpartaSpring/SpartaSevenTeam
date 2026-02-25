package com.seventeamproject.api.order.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderItemRequest(
        @NotNull(message = "sku ID(skuId)는 필수입니다.")
        @Positive(message = "sku ID(skuId)는 양수여야 합니다.")
        Long skuId,
        @NotNull(message = "수량(quantity)은 필수입니다.")
        @Positive(message = "수량(quantity)은 1 이상이어야 합니다.")
        Long quantity) {
}
