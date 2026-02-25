package com.seventeamproject.api.product.inventory.service;

import com.seventeamproject.api.admin.entity.Admin;
import com.seventeamproject.api.product.inventory.dto.InventoryRequest;
import com.seventeamproject.api.product.inventory.entity.Inventory;
import com.seventeamproject.api.product.inventory.repository.InventoryRepository;
import com.seventeamproject.api.product.product.enums.ProductStatus;
import com.seventeamproject.api.product.product.repository.ProductRepository;
import com.seventeamproject.api.product.sku.dto.SkuRequest;
import com.seventeamproject.api.product.sku.entity.Sku;
import com.seventeamproject.api.product.sku.enums.SkuStatusEnum;
import com.seventeamproject.common.dto.PageResponse;
import com.seventeamproject.common.exception.ErrorCode;
import com.seventeamproject.common.exception.ProductException;
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

    public Inventory getInventoryBySkuId(Long id) {
        return inventoryRepository.findBySkuId(id).orElseThrow(() -> new ProductException(ErrorCode.STOCK_NOT_FOUND));
    }

    public Long getQtyByProductId(Long id) {
        return inventoryRepository.sumQtyByProductId(id);
    }

    @Transactional
    public Long adjustQty(Sku sku, Long qty) {
        Inventory inventory = getInventoryBySkuId(sku.getId());
        Long adjustQty = inventory.getQty() + qty;
        if(adjustQty < 0){
            throw new ProductException(ErrorCode.ORDER_OUT_OF_STOCK);
        }
        return inventory.setQty(adjustQty);
    }

    @Transactional
    public Long setQty(Sku sku, Long qty) {
        Inventory inventory = getInventoryBySkuId(sku.getId());
        return inventory.setQty(qty);
    }
}
