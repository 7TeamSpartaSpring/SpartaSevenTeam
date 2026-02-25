package com.seventeamproject.api.admin.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AdminRoleEnum {

    SUPER_ADMIN("ROLE_SUPER_ADMIN", "슈퍼 관리자"),
    OPERATION_ADMIN("ROLE_OPERATION_ADMIN","운영 관리자"),
    CS_ADMIN("ROLE_CS_ADMIN", "CS 관리자");

    private final String authority;
    private final String title;
}