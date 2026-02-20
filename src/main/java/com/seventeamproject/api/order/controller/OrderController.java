package com.seventeamproject.api.order.controller;

import com.seventeamproject.api.order.dto.OrderRequest;
import com.seventeamproject.api.order.service.OrderService;
import com.seventeamproject.common.dto.ApiResponse;
import com.seventeamproject.common.security.principal.PrincipalUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/v1/admin/orders")
    public ResponseEntity<ApiResponse> save(
            Authentication authentication,
            @PathVariable Long id,
            @RequestBody OrderRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(orderService.save(id, request)));
    }

    @GetMapping("/v1/admin/orders")
    public ResponseEntity<ApiResponse> getAll(
            Authentication authentication,
            Pageable pageable,
            @PathVariable Long id,
            @RequestParam(required = false) String content
    ) {
        PrincipalUser user = (PrincipalUser) authentication.getPrincipal();
        log.info(user.getId().toString());
        log.info(user.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(orderService.getAll(pageable, id, content)));
    }

    @GetMapping("/v1/admin/orders/{orderId}")
    public ResponseEntity<ApiResponse> get(
            Authentication authentication,
            @PathVariable Long orderId) {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(orderService.getOne(orderId)));
    }

    @PatchMapping("/v1/admin/orders/{orderId}/status")
    public ResponseEntity<ApiResponse> update(
            Authentication authentication,
            @PathVariable Long orderId,
            @RequestBody OrderRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(orderService.update(orderId, request)));
    }

    @DeleteMapping("/v1/admin/orders/{orderId}/cancel")
    public ResponseEntity<Void> delete(
            Authentication authentication,
            @PathVariable Long orderId) {
        orderService.delete(orderId, ((PrincipalUser) authentication.getPrincipal()).getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}