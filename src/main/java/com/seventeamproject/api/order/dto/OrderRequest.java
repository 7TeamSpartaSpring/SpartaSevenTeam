package com.seventeamproject.api.order.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record OrderRequest(
        @NotBlank(message = "내용(content)은 필수 입니다")
        @Size(max = 200, message = "(content)최대 200자까지 입력 가능 합니다")
        String content,
        Long value
        ) {
}
