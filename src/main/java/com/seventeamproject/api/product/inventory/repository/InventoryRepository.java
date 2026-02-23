package com.seventeamproject.api.product.inventory.repository;

import com.seventeamproject.api.product.inventory.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
}
