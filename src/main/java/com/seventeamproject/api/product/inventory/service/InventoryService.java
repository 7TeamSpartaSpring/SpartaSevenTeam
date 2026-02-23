package com.seventeamproject.api.product.inventory.service;

import com.seventeamproject.api.admin.entity.Admin;
import com.seventeamproject.api.product.inventory.dto.InventoryRequest;
import com.seventeamproject.api.product.inventory.entity.Inventory;
import com.seventeamproject.api.product.inventory.repository.InventoryRepository;
import com.seventeamproject.api.product.product.repository.ProductRepository;
import com.seventeamproject.api.product.sku.dto.SkuRequest;
import com.seventeamproject.api.product.sku.entity.Sku;
import com.seventeamproject.common.dto.PageResponse;
import com.seventeamproject.example.one.dto.OnesResponse;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    private final EntityManager entityManager;



    @Transactional
    public Inventory save(InventoryRequest request, Long id) {
        return inventoryRepository.save(new Inventory(
                request.product(),
                request.sku(),
                entityManager.getReference(Admin.class, id),
                request.qty()
        ));
    }

    public PageResponse<OnesResponse> getAll(Pageable pageable, String title) {
//        return new PageResponse<>(productRepository.search(pageable, title));
        return null;
    }



}
