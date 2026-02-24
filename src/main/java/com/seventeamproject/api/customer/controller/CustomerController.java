package com.seventeamproject.api.customer.controller;

import com.seventeamproject.api.customer.dto.CustomerRequest;
import com.seventeamproject.api.customer.dto.CustomerSearchRequest;
import com.seventeamproject.api.customer.dto.CustomerStatusRequest;
import com.seventeamproject.api.customer.service.CustomerService;
import com.seventeamproject.common.dto.ApiResponse;
import com.seventeamproject.common.security.principal.PrincipalUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customer")
public class CustomerController {
    private final CustomerService customerService;

    //전체 조회
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'CS_ADMIN')")
    @GetMapping("/v1/customers")
    public ResponseEntity<ApiResponse> search(Authentication authentication,
                                              @PageableDefault(page = 0, size = 6, sort = "id", direction = Sort.Direction.ASC)
                                              Pageable pageable,
                                              @ModelAttribute CustomerSearchRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse
                .success(customerService.search(pageable, request))
        );
    }

    // 상세조회
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'CS_ADMIN')")
    @GetMapping("/v1/customers/{customerId}")
    public ResponseEntity<ApiResponse> get(Authentication authentication,
                                           @PathVariable Long customerId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse
                .success(customerService.getOne(customerId))
        );
    }

    // 정보 수정
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'CS_ADMIN')")
    @PutMapping("/v1/customers/{customerId}")
    public ResponseEntity<ApiResponse> update(Authentication authentication,
                                              @PathVariable Long customerId,
                                              @Valid @RequestBody CustomerRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse
                .success(customerService.update(customerId, request))
        );
    }

    //상태 수정
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'CS_ADMIN')")
    @PatchMapping("/v1/customers/{customerId}")
    public ResponseEntity<ApiResponse> updateStatus(Authentication authentication,
                                              @PathVariable Long customerId,
                                              @Valid @RequestBody CustomerStatusRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse
                .success(customerService.updateStatus(customerId, request))
        );
    }

    //삭제
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @DeleteMapping("/v1/customers/{customerId}")
    public ResponseEntity<Void> delete(Authentication authentication,
                                       @PathVariable Long customerId
    ) {
        customerService.delete(customerId, ((PrincipalUser) authentication.getPrincipal()).getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
