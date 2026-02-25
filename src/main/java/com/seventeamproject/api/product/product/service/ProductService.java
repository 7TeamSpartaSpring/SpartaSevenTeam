package com.seventeamproject.api.product.product.service;

import com.seventeamproject.api.admin.entity.Admin;
import com.seventeamproject.api.product.category.entity.Category;
import com.seventeamproject.api.product.category.repository.CategoryRepository;
import com.seventeamproject.api.product.inventory.dto.InventoryRequest;
import com.seventeamproject.api.product.inventory.entity.Inventory;
import com.seventeamproject.api.product.inventory.service.InventoryService;
import com.seventeamproject.api.product.product.dto.*;
import com.seventeamproject.api.product.product.entity.Product;
import com.seventeamproject.api.product.product.enums.ProductStatus;
import com.seventeamproject.api.product.product.repository.ProductRepository;
import com.seventeamproject.api.product.sku.dto.SkuRequest;
import com.seventeamproject.api.product.sku.entity.Sku;
import com.seventeamproject.api.product.sku.enums.SkuStatusEnum;
import com.seventeamproject.api.product.sku.service.SkuService;
import com.seventeamproject.api.review.service.ReviewService;
import com.seventeamproject.common.dto.PageResponse;
import com.seventeamproject.common.exception.ErrorCode;
import com.seventeamproject.common.exception.ProductException;
import jakarta.persistence.EntityManager;;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SkuService skuService;
    private final ReviewService reviewService;
    private final InventoryService inventoryService;
    private final EntityManager entityManager;

    public boolean canOrder(Long id) {
        Sku sku = skuService.getSku(id);
        if (sku.getStatus() != SkuStatusEnum.AVAILABLE || sku.getProduct().getStatus() != ProductStatus.AVAILABLE) {
            throw new ProductException(ErrorCode.ORDER_UNAVAILABLE_STATUS);
        }
        return true;
    }

    @Transactional
    public Long adjustStock(Long id, Long qty) {
        Sku sku = skuService.getSku(id);
        return inventoryService.adjustQty(sku, qty);
    }

    @Transactional
    public ProductResponse save(ProductRequest request, Long id) {
        Product product = productRepository.save(new Product(
                request.name(),
                categoryRepository.findById(request.categoryId()).orElseThrow(() -> new ProductException(ErrorCode.INVALID_INPUT_VALUE)),
                request.price(),
                request.status(),
                entityManager.getReference(Admin.class, id)
        ));
        Sku sku = skuService.save(new SkuRequest(product, request.price(), SkuStatusEnum.valueOf(request.status().name())), id);
        Inventory inventory = inventoryService.save(new InventoryRequest(product, sku, request.qty()), id);
        return new ProductResponse(product, inventory.getQty());
    }

    public PageResponse<ProductsResponse> search(Pageable pageable, String name, Long categoryId, ProductStatus status) {
        return new PageResponse<>(productRepository.search(pageable, name, categoryId, status));
    }

    public ProductResponse pick(Long id) {
        return new ProductResponse(productRepository.findById(id).
                orElseThrow(() -> new ProductException(ErrorCode.PRODUCT_NOT_FOUND)),
                inventoryService.getQtyByProductId(id),
                reviewService.getReviewSummary(id));
    }

    @Transactional
    public ProductResponse update(Long id, UpdateProductRequest request) {
        Product product = productRepository.findById(id).
                orElseThrow(() -> new ProductException(ErrorCode.PRODUCT_NOT_FOUND));
        Category category = categoryRepository.findById(request.categoryId()).
                orElseThrow(() -> new ProductException(ErrorCode.CATEGORY_NOT_FOUND));
        return new ProductResponse(product.update(request.name(),
                category,
                request.price()),
                inventoryService.getQtyByProductId(id));
    }

    @Transactional
    public ProductResponse changeStatus(Long id, ChangeProductStatusRequest request) {
        return new ProductResponse(productRepository.findById(id).
                orElseThrow(() -> new ProductException(ErrorCode.PRODUCT_NOT_FOUND)).setStatus(request.status()),
                inventoryService.getQtyByProductId(id));
    }

    @Transactional
    public void delete(Long id, Long userId) {
        skuService.getSkusByProducrtId(id).forEach(sku -> {
            inventoryService.getInventoryBySkuId(sku.getId()).delete(userId);
            sku.delete(userId);
        });
        productRepository.findById(id).orElseThrow(() -> new ProductException(ErrorCode.PRODUCT_NOT_FOUND)).delete(userId);
    }
}