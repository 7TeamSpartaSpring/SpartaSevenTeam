package com.seventeamproject.api.auth.dto;

import com.seventeamproject.api.admin.enums.AdminRoleEnum;
import jakarta.validation.constraints.*;

// 관리자 회원가입 신청
public record SignupRequest(

        @NotBlank(message = "email(이메일)은 필수입니다")
        @Email(message = "email(이메일)이 올바르지 않습니다")
        String email,

        @NotBlank(message = "password(비밀번호)는 필수입니다")
        @Size(min = 8, message = "password(비밀번호)는 최소 8자 이상이어야 합니다")
        String password,

        @NotBlank(message = "name(이름)은 필수입니다")
        String name,

        @NotBlank(message = "phone(전화번호)는 필수입니다")
        @Pattern(regexp = "^010-\\d{4}-\\d{4}$", message = "phone(전화번호)는 010-xxxx-xxxx 형식이어야 합니다")
        String phone,

        @NotNull(message = "role(역할)은 필수입니다.")
        AdminRoleEnum role
        ) {}
