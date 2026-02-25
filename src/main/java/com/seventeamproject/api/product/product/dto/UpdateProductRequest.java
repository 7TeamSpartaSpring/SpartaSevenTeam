package com.seventeamproject.api.product.product.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record UpdateProductRequest(
        @NotNull(message = "name(이름)은 필수 입니다")
        @Size(max = 20, message = "name(이름)을 20자 이내로 입력해 주세요")
        String name,
        @NotNull(message = "categoryId(카테고리ID)는 필수 입니다")
        Long categoryId,
        @NotNull(message = "price(가격)은 필수 입니다")
        @PositiveOrZero(message = "0이상의 price(가격)을 입력해 주세요")
        Long price
) {
}
