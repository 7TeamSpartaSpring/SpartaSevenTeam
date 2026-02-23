package com.seventeamproject.api.admin.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.seventeamproject.api.admin.entity.Admin;
import com.seventeamproject.api.admin.enums.AdminRoleEnum;
import com.seventeamproject.api.admin.enums.AdminStatusEnum;

import java.time.LocalDateTime;

public record AdminResponse(
        Long id,
        String email,
        String name,
        String phone,
        AdminRoleEnum role,
        AdminStatusEnum status,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt,
        LocalDateTime approvedAt,
        LocalDateTime rejectedAt,
        String rejectedReason
) {
    public AdminResponse(Admin admin) {
        this(
                admin.getId(),
                admin.getEmail(),
                admin.getName(),
                admin.getPhone(),
                admin.getRole(),
                admin.getStatus(),
                admin.getCreatedAt(),
                admin.getModifiedAt(),
                admin.getApprovedAt(),
                admin.getRejectedAt(),
                admin.getRejectedReason()
        );
    }

    @QueryProjection
    public AdminResponse { }
}
