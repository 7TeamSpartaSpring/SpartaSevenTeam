package com.seventeamproject.api.product.product.controller;

import com.seventeamproject.api.product.product.dto.ChangeProductStatusRequest;
import com.seventeamproject.api.product.product.dto.ProductRequest;
import com.seventeamproject.api.product.product.dto.UpdateProductRequest;
import com.seventeamproject.api.product.product.enums.ProductStatus;
import com.seventeamproject.api.product.product.service.ProductService;
import com.seventeamproject.common.dto.ApiResponse;
import com.seventeamproject.common.security.principal.PrincipalUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/v1/products")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'OPERATION_ADMIN')")
    public ResponseEntity<ApiResponse> save(Authentication authentication,
                                            @Valid @RequestBody ProductRequest request) {
        PrincipalUser user = (PrincipalUser) authentication.getPrincipal();
        return ResponseEntity.status(HttpStatus.CREATED).
                body(ApiResponse.success(productService.save(request, user.getId())));
    }

    @GetMapping("/v1/products")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'OPERATION_ADMIN', 'CS_ADMIN')")
    public ResponseEntity<ApiResponse> search(Authentication authentication,
                                              @RequestParam(required = false) String name,
                                              @RequestParam(required = false) Long categoryId,
                                              @RequestParam(required = false) ProductStatus status,
                                              @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC)
                                              Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).
                body(ApiResponse.success(productService.search(pageable, name, categoryId, status)));
    }

    @GetMapping("/v1/products/{productId}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'OPERATION_ADMIN', 'CS_ADMIN')")
    public ResponseEntity<ApiResponse> pick(Authentication authentication,
                                            @PathVariable Long productId) {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(productService.pick(productId)));
    }

    @PutMapping("/v1/products/{productId}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'OPERATION_ADMIN')")
    public ResponseEntity<ApiResponse> update(Authentication authentication,
                                              @PathVariable Long productId,
                                              @Valid @RequestBody UpdateProductRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(productService.update(productId, request)));
    }

    @PatchMapping("/v1/products/{productId}/status")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'OPERATION_ADMIN')")
    public ResponseEntity<ApiResponse> changeStatus(Authentication authentication,
                                                    @PathVariable Long productId,
                                                    @Valid @RequestBody ChangeProductStatusRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(productService.changeStatus(productId, request)));
    }

    @DeleteMapping("/v1/products/{productId}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','OPERATION_ADMIN', 'CS_ADMIN')")
    public ResponseEntity<Void> delete(Authentication authentication,
                                       @PathVariable Long productId) {
        productService.delete(productId, ((PrincipalUser) authentication.getPrincipal()).getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
