package com.seventeamproject.api.order.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record CreateOrderRequest(
        @NotBlank(message = "고객ID는 필수입니다.")
        Long customerId,
       @NotBlank(message = "주문은 1건이상 이어야합니다.")
       List<OrderItemRequest>items
) {
}
