package com.seventeamproject.api.admin.dto;

public record AdminMeResponse(
        Long id,
        String email,
        String name,
        String phone,
        com.seventeamproject.api.admin.enums.AdminRoleEnum role,
        com.seventeamproject.api.admin.enums.AdminStatusEnum status,
        java.time.LocalDateTime createdAt,
        java.time.LocalDateTime modifiedAt
) {
    public static AdminMeResponse from(com.seventeamproject.api.admin.entity.Admin admin) {
        return new AdminMeResponse(
                admin.getId(),
                admin.getEmail(),
                admin.getName(),
                admin.getPhone(),
                admin.getRole(),
                admin.getStatus(),
                admin.getCreatedAt(),
                admin.getModifiedAt()
        );
    }
}
