package com.seventeamproject.api.product.sku.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SkuStatusEnum {
    AVAILABLE("판매중"),
    SOLD_OUT("품절"),
    DISCONTINUED("단종");
    private final String title;
}
