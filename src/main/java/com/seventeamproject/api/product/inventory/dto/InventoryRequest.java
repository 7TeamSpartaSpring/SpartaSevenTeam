package com.seventeamproject.api.product.inventory.dto;

import com.seventeamproject.api.product.product.entity.Product;
import com.seventeamproject.api.product.sku.entity.Sku;

public record InventoryRequest(Product product, Sku sku, Long qty) {
}
