package com.seventeamproject.api.product.sku.service;

import com.seventeamproject.api.admin.entity.Admin;
import com.seventeamproject.api.product.sku.dto.SkuRequest;
import com.seventeamproject.api.product.sku.entity.Sku;
import com.seventeamproject.api.product.sku.repository.SkuRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SkuService {
    private final SkuRepository skuRepository;
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
}
