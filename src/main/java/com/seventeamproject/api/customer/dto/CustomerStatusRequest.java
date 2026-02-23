package com.seventeamproject.api.customer.dto;

import jakarta.validation.constraints.NotBlank;

public record CustomerStatusRequest(
        @NotBlank(message = "변경할 상태값은 필수입니다.")
        String status
) {
}
