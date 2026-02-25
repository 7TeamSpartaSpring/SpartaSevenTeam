package com.seventeamproject.api.product.sku.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record ChangeQtyRequest(
        @NotNull(message = "qty(수량)은 필수 입니다")
        @PositiveOrZero(message = "0이상의 qty(수량)을 입력해 주세요")
        Long qty
) {
}
