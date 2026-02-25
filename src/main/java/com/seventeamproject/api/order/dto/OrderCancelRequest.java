package com.seventeamproject.api.order.dto;

import jakarta.validation.constraints.NotBlank;

public record OrderCancelRequest(
        @NotBlank(message = "취소 사유(cancellationReason)는 필수입니다.")
        String cancellationReason
) {
}
