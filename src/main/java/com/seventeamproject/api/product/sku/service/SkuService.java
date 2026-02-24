package com.seventeamproject.api.product.sku.service;

import com.seventeamproject.api.admin.entity.Admin;
import com.seventeamproject.api.product.inventory.entity.Inventory;
import com.seventeamproject.api.product.inventory.repository.InventoryRepository;
import com.seventeamproject.api.product.inventory.service.InventoryService;
import com.seventeamproject.api.product.product.dto.ChangeProductStatusRequest;
import com.seventeamproject.api.product.product.dto.ProductResponse;
import com.seventeamproject.api.product.product.entity.Product;
import com.seventeamproject.api.product.sku.dto.ChangeQtyRequest;
import com.seventeamproject.api.product.sku.dto.SkuRequest;
import com.seventeamproject.api.product.sku.dto.SkuResponse;
import com.seventeamproject.api.product.sku.entity.Sku;
import com.seventeamproject.api.product.sku.repository.SkuRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SkuService {
    private final SkuRepository skuRepository;
    private final InventoryService inventoryService;
    private final EntityManager entityManager;

    @Transactional
    public Sku save(SkuRequest request, Long id) {
        return skuRepository.save(new Sku(
                request.product(),
                entityManager.getReference(Admin.class, id),
                request.price(),
                request.status()
        ));
    }

    public Sku getSku(Long id) {
        return skuRepository.findById(id).orElseThrow(() -> new IllegalStateException());
    }

    public List<Sku> getSkusByProducrtId(Long id) {
        return skuRepository.findByProductId(id);
    }

    @Transactional
    public SkuResponse setQty(Long id, ChangeQtyRequest request) {
        Sku sku = getSku(id);
        return new SkuResponse(sku, inventoryService.setQty(sku, request.qty()));
    }

}
