package com.seventeamproject.api.order.controller;

import com.seventeamproject.api.order.dto.*;
import com.seventeamproject.api.order.enums.OrderStatus;
import com.seventeamproject.api.order.service.OrderService;
import com.seventeamproject.common.dto.ApiResponse;
import com.seventeamproject.common.dto.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor

public class OrderController {
    private final OrderService orderService;

    //생성
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','OPERATION', 'CS_ADMIN')")
    @PostMapping("/v1/orders")
    public ResponseEntity<ApiResponse<OrderResponse>> save(
            Authentication authentication,
            @Valid @RequestBody CreateOrderRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(orderService.save(authentication, request)));
    }

    //전체조회
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','OPERATION', 'CS_ADMIN')")
    @GetMapping("/v1/orders")
    public ResponseEntity<ApiResponse<PageResponse<OrderListResponse>>> search(
            Pageable pageable,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) OrderStatus status
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(orderService.getAll(pageable, keyword, status)));
    }

    // 단건조회
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','OPERATION', 'CS_ADMIN')")
    @GetMapping("/v1/orders/{orderId}")
    public ResponseEntity<ApiResponse<GetOneOrderResponse>> getOne(
            @PathVariable Long orderId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(orderService.getOne(orderId)));
    }

    // 상태변경
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','OPERATION')")
    @PatchMapping("/v1/orders/{orderId}/status")
    public ResponseEntity<ApiResponse<OrderResponse>> update(
            @PathVariable Long orderId,
            @Valid @RequestBody StatusUpdateRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(orderService.update(orderId, request)));
    }

    // 주문취소
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','OPERATION', 'CS_ADMIN')")
    @PatchMapping("/v1/orders/{orderId}/cancel")
    public ResponseEntity<ApiResponse<OrderResponse>> cancel(
            @PathVariable Long orderId,
            @Valid @RequestBody OrderCancelRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(orderService.cancel(orderId, request)));
    }
}


