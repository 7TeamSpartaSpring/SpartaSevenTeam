package com.seventeamproject.api.auth.dto;

import com.seventeamproject.api.admin.enums.AdminRoleEnum;
import jakarta.validation.constraints.*;

// 관리자 회원가입 신청
public record SignupRequest(

        @NotBlank(message = "이메일은 필수입니다")
        @Email(message = "이메일 형식이 올바르지 않습니다")
        String email,

        @NotBlank(message = "비밀번호는 필수입니다")
        @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다")
        String password,

        @NotBlank(message = "이름은 필수입니다")
        String name,

        @NotBlank(message = "전화번호는 필수입니다")
        @Pattern(regexp = "^010-\\d{4}-\\d{4}$", message = "전화번호는 010-xxxx-xxxx 형식이어야 합니다")
        String phone,

        @NotNull(message = "역할은 필수입니다.")
        AdminRoleEnum role
        ) {
}
