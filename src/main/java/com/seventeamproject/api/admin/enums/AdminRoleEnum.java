package com.seventeamproject.api.admin.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AdminRoleEnum {

    SUPER_ADMIN("ROLE_SUPER_ADMIN", "슈퍼 관리자"),
    ADMIN("ROLE_ADMIN", "관리자");

    private final String authority;
    private final String title;
}