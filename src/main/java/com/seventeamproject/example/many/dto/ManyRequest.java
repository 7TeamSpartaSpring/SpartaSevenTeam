package com.seventeamproject.example.many.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ManyRequest(
        @NotBlank(message = "내용은 필수 입니다")
        @Size(max = 200, message = "최대 200자까지 입력 가능 합니다")
        String content,
        Long value
        ) {
}
