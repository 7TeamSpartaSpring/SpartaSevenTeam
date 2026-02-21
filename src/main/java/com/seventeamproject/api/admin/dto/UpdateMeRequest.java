package com.seventeamproject.api.admin.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UpdateMeRequest (
        @NotBlank(message = "이름은 필수입니다")
        String name,

        @Email(message = "이메일 형식이 올바르지 않습니다")
        @NotBlank(message = "이메일은 필수입니다")
        String email,

        @Pattern(regexp = "^010-\\d{4}-\\d{4}$", message = "전화번호 형식은 010-xxxx-xxxx입니다")
        @NotBlank(message = "전화번호는 필수입니다")
        String phone
) {}
