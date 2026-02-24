package com.seventeamproject.api.product.product.repository;

import com.seventeamproject.api.product.product.dto.ProductsResponse;
import com.seventeamproject.api.product.product.enums.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepositoryCustom {
    Page<ProductsResponse> search(Pageable pageable, String name, Long categoryId, ProductStatus status);
}
