package com.seventeamproject.api.admin.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

// 로그인한 관리자(본인)가 내 프로필을 수정할 때 사용하는 요청 dto
public record UpdateMeRequest (
        @NotBlank(message = "name(이름)은 필수입니다")
        String name,

        @Email(message = "email(이메일)이 올바르지 않습니다")
        @NotBlank(message = "email(이메일)은 필수입니다")
        String email,

        @Pattern(regexp = "^010-\\d{4}-\\d{4}$", message = "phone(전화번호) 형식은 010-xxxx-xxxx입니다")
        @NotBlank(message = "phone(전화번호)는 필수입니다")
        String phone
) {}
