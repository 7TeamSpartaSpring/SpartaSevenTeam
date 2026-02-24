package com.seventeamproject.api.admin.dto;

import com.seventeamproject.api.admin.enums.AdminRoleEnum;
import com.seventeamproject.api.admin.enums.AdminStatusEnum;

public record AdminSearchRequest(
        String keyword,
        AdminRoleEnum role,
        AdminStatusEnum status
) {}
