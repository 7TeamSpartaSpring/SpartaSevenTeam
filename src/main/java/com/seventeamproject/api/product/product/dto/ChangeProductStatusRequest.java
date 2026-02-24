package com.seventeamproject.api.product.product.dto;

import com.seventeamproject.api.product.product.enums.ProductStatus;
import jakarta.validation.constraints.NotNull;

public record ChangeProductStatusRequest (
        @NotNull(message = "상태는 필수입니다")
        ProductStatus status
) {}

