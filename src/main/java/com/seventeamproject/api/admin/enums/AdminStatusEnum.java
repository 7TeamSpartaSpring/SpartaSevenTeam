package com.seventeamproject.api.admin.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AdminStatusEnum {

    PENDING("승인대기"),
    ACTIVE("활성"),
    INACTIVE("비활성"),
    SUSPENDED("정지"),
    REJECTED("거부");

    private final String title;
}
