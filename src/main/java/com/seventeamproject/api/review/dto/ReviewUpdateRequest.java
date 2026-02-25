package com.seventeamproject.api.review.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ReviewUpdateRequest(
        @NotBlank(message = "내용은 필수입니다.")
        @Size(max = 100, message = "최대 100자입니다.")
        String content
) {
}
