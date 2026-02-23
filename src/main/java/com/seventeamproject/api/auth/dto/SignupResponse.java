package com.seventeamproject.api.auth.dto;

import com.seventeamproject.api.admin.entity.Admin;
import com.seventeamproject.api.admin.enums.AdminRoleEnum;
import com.seventeamproject.api.admin.enums.AdminStatusEnum;

import java.time.LocalDateTime;

public record SignupResponse(Long id,
                             String email,
                             String name,
                             AdminRoleEnum role,
                             AdminStatusEnum status,
                             LocalDateTime createdAt,
                             LocalDateTime modifiedAt
) {
    public static SignupResponse from(Admin admin) {
        return new SignupResponse(
                admin.getId(),
                admin.getEmail(),
                admin.getName(),
                admin.getRole(),
                admin.getStatus(),
                admin.getCreatedAt(),
                admin.getModifiedAt()
        );
    }
}
