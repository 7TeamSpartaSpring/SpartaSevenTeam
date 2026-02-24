package com.seventeamproject.api.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "email(이메일)은 필수입니다")
        @Email(message = "email(이메일) 형식이 올바르지 않습니다")
        String email,

        @NotBlank(message = "password(비밀번호)는 필수입니다")
        String password
) {}
