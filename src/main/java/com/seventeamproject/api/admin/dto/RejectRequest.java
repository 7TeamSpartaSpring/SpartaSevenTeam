package com.seventeamproject.api.admin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RejectRequest (
    @NotBlank(message = "거부 사유는 필수입니다")
    @Size(max = 500, message = "거부 사유는 500자 이내로 입력해 주세요")
    String reason
) {}
