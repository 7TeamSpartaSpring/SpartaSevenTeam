package com.seventeamproject.api.order.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateSingleOrderRequest
        (
                @NotNull (message = "고객ID는 필수입니다.")
                @Positive (message = "고객ID는 양수여야합니다.")
                Long customerId,
                @NotNull (message = "skuId는 필수입니다.")
                @Positive (message = "skuID는 양수여야합니다.")
                Long skuId,
                @NotNull (message = "주문 수량은 필수입니다.")
                @Positive (message = "주문수량은 양수여야합니다.")
                Long quantity

        ){
}
