package com.seventeamproject.api.product.category.controller;

import com.seventeamproject.api.product.category.dto.CategoryRequest;
import com.seventeamproject.api.product.category.service.CategoryService;
import com.seventeamproject.common.dto.ApiResponse;
import com.seventeamproject.common.dto.PageResponse;
import com.seventeamproject.common.security.principal.PrincipalUser;
import com.seventeamproject.example.one.dto.OnesResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/v1/categorys")
    public ResponseEntity<ApiResponse> save(Authentication authentication,
                                            @Valid @RequestBody CategoryRequest request) {
        PrincipalUser user = (PrincipalUser) authentication.getPrincipal();
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(categoryService.save(request, user.getId())));
    }
    @GetMapping("/v1/categorys")
    public ResponseEntity<ApiResponse> getAll(Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(categoryService.getAll()));
    }
    @DeleteMapping("/v1/categorys/{id}")
    public ResponseEntity<Void> delete(Authentication authentication,
                                       @PathVariable Long id) {
        categoryService.delete(id, ((PrincipalUser) authentication.getPrincipal()).getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
