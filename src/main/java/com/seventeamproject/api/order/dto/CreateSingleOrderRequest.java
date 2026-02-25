package com.seventeamproject.api.order.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateSingleOrderRequest
        (
                @NotNull (message = "고객ID(customerId)는 필수입니다.")
                @Positive (message = "고객ID(customerId)는 양수여야합니다.")
                Long customerId,
                @NotNull (message = "skuId(skuId)는 필수입니다.")
                @Positive (message = "skuID(skuId)는 양수여야합니다.")
                Long skuId,
                @NotNull (message = "주문 수량(quantity)은 필수입니다.")
                @Positive (message = "주문수량(quantity)은 양수여야합니다.")
                Long quantity

        ){
}
