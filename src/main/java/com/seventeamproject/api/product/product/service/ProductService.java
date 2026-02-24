package com.seventeamproject.api.product.product.service;

import com.seventeamproject.api.admin.entity.Admin;
import com.seventeamproject.api.product.category.repository.CategoryRepository;
import com.seventeamproject.api.product.inventory.dto.InventoryRequest;
import com.seventeamproject.api.product.inventory.entity.Inventory;
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
import com.seventeamproject.api.review.service.ReviewService;
import com.seventeamproject.common.dto.PageResponse;
import com.seventeamproject.common.exception.ErrorCode;
import com.seventeamproject.common.exception.ProductException;
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
    private final CategoryRepository categoryRepository;
    private final SkuService skuService;
    private final ReviewService reviewService;
    private final InventoryService inventoryService;
    private final EntityManager entityManager;

    public boolean canOrder(Long id) {
        Sku sku = skuService.getSku(id);
        if(sku.getStatus() != SkuStatusEnum.AVAILABLE || sku.getProduct().getStatus() != ProductStatus.AVAILABLE){
            throw new ProductException(ErrorCode.ORDER_OUT_OF_STOCK);
        }
        return true;
    }

    @Transactional
    public Long adjustStock(Long id, Long qty) {
        Sku sku = skuService.getSku(id);
        Inventory inventory = inventoryService.getInventory(id);

        Long adjustQty = inventory.getQty() + qty;
        if(adjustQty < 0){
            throw new ProductException(ErrorCode.ORDER_OUT_OF_STOCK);
        }else if(adjustQty == 0 && sku.getStatus() != SkuStatusEnum.DISCONTINUED){
            if(sku.getStatus() != SkuStatusEnum.DISCONTINUED){
                sku.setStatus(SkuStatusEnum.SOLD_OUT);
            }
            if(sku.getProduct().getStatus() != ProductStatus.DISCONTINUED){
                sku.getProduct().setStatus(ProductStatus.SOLD_OUT);
            }
        }
        return inventory.setQty(adjustQty);
    }

    @Transactional
    public ProductResponse save(ProductRequest request, Long id) {
        Product product = productRepository.save(new Product(
                request.name(),
                categoryRepository.findById(request.categoryId()).orElseThrow(() -> new IllegalStateException("적절한 에러 메세지")),
                request.price(),
                request.totalQty(),
                request.status(),
                entityManager.getReference(Admin.class, id)
        ));
        Sku sku = skuService.save(new SkuRequest(product, request.price(), SkuStatusEnum.valueOf(request.status().name())), id);
        inventoryService.save(new InventoryRequest(product, sku, request.totalQty()), id);
        return new ProductResponse(product);
    }

    public PageResponse<ProductsResponse> search(Pageable pageable, String name, Long categoryId, ProductStatus status) {
        return new PageResponse<>(productRepository.search(pageable, name, categoryId, status));
    }

    public ProductResponse pick(Long id) {
        return new ProductResponse(productRepository.findById(id).
                orElseThrow(() -> new IllegalStateException("적절한 에러 메세지")),
                reviewService.getReviewSummary(id));
    }

    @Transactional
    public ProductResponse update(Long id, ProductRequest request) {
        return new ProductResponse(productRepository.findById(id).
                orElseThrow(() -> new IllegalStateException("적절한 에러 메세지")).update(request.name(),
                        categoryRepository.findById(request.categoryId()).
                                orElseThrow(() -> new IllegalStateException("적절한 에러 메세지")),
                        request.price()));
    }
}
