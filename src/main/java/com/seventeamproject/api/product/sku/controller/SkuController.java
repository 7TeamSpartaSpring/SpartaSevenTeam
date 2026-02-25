package com.seventeamproject.api.product.sku.controller;

import com.seventeamproject.api.product.product.dto.ChangeProductStatusRequest;
import com.seventeamproject.api.product.product.service.ProductService;
import com.seventeamproject.api.product.sku.dto.ChangeQtyRequest;
import com.seventeamproject.api.product.sku.service.SkuService;
import com.seventeamproject.common.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class SkuController {

    private final SkuService skuService;

    @PatchMapping("/v1/skus/{skuId}/qty")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'OPERATION_ADMIN')")
    public ResponseEntity<ApiResponse> setQty(Authentication authentication,
                                                    @PathVariable Long skuId,
                                                    @Valid @RequestBody ChangeQtyRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(skuService.setQty(skuId, request)));
    }
}
