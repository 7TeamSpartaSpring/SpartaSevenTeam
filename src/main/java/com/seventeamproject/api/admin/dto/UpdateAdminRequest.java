package com.seventeamproject.api.admin.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

// SUPER_ADMIN이 타 관리자 프로필을 수정할 때 사용하는 요청 dto
public record UpdateAdminRequest(
        @NotBlank(message = "이름은 필수입니다")
        String name,

        @Email(message = "이메일 형식이 올바르지 않습니다")
        @NotBlank(message = "이메일은 필수입니다")
        String email,

        @Pattern(regexp = "^010-\\d{4}-\\d{4}$", message = "전화번호 형식은 010-xxxx-xxxx입니다")
        @NotBlank(message = "전화번호는 필수입니다")
        String phone
) {}
