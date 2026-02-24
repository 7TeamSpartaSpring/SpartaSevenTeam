package com.seventeamproject.api.order.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record CreateOrderRequest(
        @NotNull(message = "고객ID(customerId)는 필수입니다.")
        @Positive(message = "고객ID(customerId)는 양수여야 합니다.")
        Long customerId,
        @NotEmpty(message = "주문(items)은 1건이상 이어야합니다.")
        List<OrderItemRequest> items
) {
}
