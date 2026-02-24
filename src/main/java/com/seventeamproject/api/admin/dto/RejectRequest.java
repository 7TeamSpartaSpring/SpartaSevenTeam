package com.seventeamproject.api.admin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RejectRequest (
    @NotBlank(message = "reason(거부 사유)는 500자 이내로 입력해 주세요")
    String reason
) {}
