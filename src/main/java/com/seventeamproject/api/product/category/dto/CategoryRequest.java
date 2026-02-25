package com.seventeamproject.api.product.category.dto;

import com.seventeamproject.api.product.category.enums.CategoryStatusEnum;
import jakarta.validation.constraints.NotNull;

public record CategoryRequest(
        @NotNull(message = "code(코드)는 필수 입니다")
        String code,
        @NotNull(message = "name(이름)은 필수 입니다")
        String name,
        @NotNull(message = "status(카테고리 상태)는 필수 입니다")
        CategoryStatusEnum status
) {
}
