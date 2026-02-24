package com.seventeamproject.api.admin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ChangePasswordRequest(
        @NotBlank(message = "currentPassword(현재 비밀번호)는 필수입니다")
        String currentPassword,

        @NotBlank(message = "newPassword(새 비밀번호)는 필수입니다")
        @Size(min = 8, message = "비밀번호는 최소 8자 이상입니다")
        String newPassword
) {}
