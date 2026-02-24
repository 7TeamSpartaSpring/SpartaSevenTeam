package com.seventeamproject.api.product.product.service;

import com.seventeamproject.api.admin.entity.Admin;
import com.seventeamproject.api.product.category.entity.Category;
import com.seventeamproject.api.product.inventory.dto.InventoryRequest;
import com.seventeamproject.api.product.inventory.service.InventoryService;
import com.seventeamproject.api.product.product.dto.ProductRequest;
import com.seventeamproject.api.product.product.dto.ProductResponse;
import com.seventeamproject.api.product.product.dto.ProductsResponse;
import com.seventeamproject.api.product.product.entity.Product;
import com.seventeamproject.api.product.product.enums.ProductStatus;
import com.seventeamproject.api.product.product.repository.ProductRepository;
import com.seventeamproject.api.product.sku.dto.SkuRequest;
import com.seventeamproject.api.product.sku.entity.Sku;
import com.seventeamproject.api.product.sku.enums.SkuStatusEnum;
import com.seventeamproject.api.product.sku.service.SkuService;
import com.seventeamproject.common.dto.PageResponse;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final SkuService skuService;
    private final InventoryService inventoryService;
    private final EntityManager entityManager;

    @Transactional
    public boolean adjustStock(Long id, Long qty) {
        return true;
    }

    @Transactional
    public ProductResponse save(ProductRequest request, Long id) {
        Product product = productRepository.save(new Product(
                request.name(),
                entityManager.getReference(Category.class, request.categoryId()),
                request.price(),
                request.totalQty(),
                request.status(),
                entityManager.getReference(Admin.class, id)
        ));
        Sku sku = skuService.save(new SkuRequest(product, request.price(), SkuStatusEnum.valueOf(request.status().name())), id);
        inventoryService.save(new InventoryRequest(product, sku, request.totalQty()), id);
        return new ProductResponse(product);
    }

    public PageResponse<ProductsResponse> getAll(Pageable pageable, String name, Long categoryId, ProductStatus status) {
        return new PageResponse<>(productRepository.search(pageable, name, categoryId, status));
    }
}
