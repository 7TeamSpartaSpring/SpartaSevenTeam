package com.seventeamproject.api.product.sku.dto;

import com.seventeamproject.api.product.product.entity.Product;
import com.seventeamproject.api.product.sku.enums.SkuStatusEnum;

public record SkuRequest(Product product, Long price, SkuStatusEnum status) {
}
