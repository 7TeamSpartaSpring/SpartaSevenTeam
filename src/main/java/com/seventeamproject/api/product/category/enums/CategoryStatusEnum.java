package com.seventeamproject.api.product.category.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CategoryStatusEnum {
    ACTIVE("활성"),
    INACTIVE("비활성");
    private final String title;
}
