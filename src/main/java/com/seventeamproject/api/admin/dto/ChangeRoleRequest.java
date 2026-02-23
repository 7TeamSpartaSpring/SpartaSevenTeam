package com.seventeamproject.api.admin.dto;

import com.seventeamproject.api.admin.enums.AdminRoleEnum;
import jakarta.validation.constraints.NotNull;

public record ChangeRoleRequest(
        @NotNull(message = "역할은 필수입니다")
        AdminRoleEnum role
) {}
