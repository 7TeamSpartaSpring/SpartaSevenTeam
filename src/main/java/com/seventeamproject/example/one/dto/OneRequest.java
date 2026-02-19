package com.seventeamproject.example.one.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record OneRequest(
        @NotBlank(message = "제목은 필수입니다")
        @Size(max = 30, message = "제목은 최대 30자까지 가능합니다")
        String title,
        @NotBlank(message = "내용은 필수입니다")
        @Size(max = 200, message = "내용은 최대 200자까지 가능합니다")
        String content
) {
}
