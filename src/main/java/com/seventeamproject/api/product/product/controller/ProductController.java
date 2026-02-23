package com.seventeamproject.api.product.product.controller;

import com.seventeamproject.api.product.product.dto.ProductRequest;
import com.seventeamproject.api.product.product.enums.ProductStatusEnum;
import com.seventeamproject.api.product.product.service.ProductService;
import com.seventeamproject.common.dto.ApiResponse;
import com.seventeamproject.common.security.principal.PrincipalUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;
    @PostMapping("/v1/products")
    public ResponseEntity<ApiResponse> save(Authentication authentication,
                                            @RequestBody ProductRequest request) {
        PrincipalUser user = (PrincipalUser) authentication.getPrincipal();
        return ResponseEntity.status(HttpStatus.CREATED).
                body(ApiResponse.success(productService.save(request, user.getId())));
    }

    @GetMapping("/v1/products")
    public ResponseEntity<ApiResponse> getAll(Authentication authentication,
                                              @RequestParam(required = false) String name,
                                              @RequestParam(required = false) Long categoryId,
                                              @RequestParam(required = false) ProductStatusEnum status,
                                              @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC)
                                              Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).
                body(ApiResponse.success(productService.getAll(pageable, name, categoryId, status)));
    }
}
