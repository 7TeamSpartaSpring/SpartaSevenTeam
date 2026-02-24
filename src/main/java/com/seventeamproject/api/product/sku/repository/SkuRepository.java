package com.seventeamproject.api.product.sku.repository;

import com.seventeamproject.api.product.sku.entity.Sku;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SkuRepository extends JpaRepository<Sku, Long> {
    List<Sku> findByProductId(Long productId);
}
