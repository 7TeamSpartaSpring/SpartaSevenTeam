package com.seventeamproject.api.product.category.dto;

import com.seventeamproject.api.product.category.enums.CategoryStatusEnum;

public record CategoryRequest(String code, String name, CategoryStatusEnum status) {
}
