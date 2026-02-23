package com.seventeamproject.api.order.dto;

import com.seventeamproject.api.order.entity.OrderStatus;
import jakarta.validation.constraints.NotNull;

public record StatusUpdateRequest(
        @NotNull(message = "바꿀 상태를 선택해야합니다.")
        OrderStatus orderStatus
) {
}
