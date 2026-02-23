package com.seventeamproject.api.admin.dto;

import com.seventeamproject.api.admin.enums.AdminStatusEnum;
import jakarta.validation.constraints.NotNull;

public record ChangeStatusRequest(
        @NotNull(message = "상태는 필수입니다")
        AdminStatusEnum status
) {}
