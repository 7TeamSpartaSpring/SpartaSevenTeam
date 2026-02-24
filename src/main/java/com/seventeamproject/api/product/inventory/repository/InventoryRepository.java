package com.seventeamproject.api.product.inventory.repository;

import com.seventeamproject.api.product.inventory.entity.Inventory;
import com.seventeamproject.api.product.sku.entity.Sku;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Optional<Inventory> findBySkuId(Long skuId);
}
