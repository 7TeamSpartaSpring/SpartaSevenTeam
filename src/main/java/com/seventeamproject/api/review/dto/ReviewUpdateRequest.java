package com.seventeamproject.api.review.dto;

import jakarta.validation.constraints.NotBlank;

public record ReviewUpdateRequest(
        @NotBlank(message = "내용은 필수입니다.")
        String content
) {
}
