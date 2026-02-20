package com.seventeamproject.api.auth.dto;

import com.seventeamproject.api.admin.entity.Admin;
import com.seventeamproject.api.admin.enums.AdminRoleEnum;

import java.time.LocalDateTime;

public record SignupResponse(Long id,
                             String email,
                             String name,
                             AdminRoleEnum role,
                             LocalDateTime createdAt,
                             LocalDateTime modifiedA
) {
    public static SignupResponse from(Admin admin) {
        return new SignupResponse(admin.getId(), admin.getEmail(), admin.getName(), admin.getRole(), admin.getCreatedAt(), admin.getModifiedAt());
    }
}
