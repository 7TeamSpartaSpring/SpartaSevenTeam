//package com.seventeamproject.api.customer.controller;
//
//import com.seventeamproject.api.customer.dto.CustomerRequest;
//import com.seventeamproject.api.customer.service.CustomerService;
//import com.seventeamproject.common.dto.ApiResponse;
//import com.seventeamproject.common.security.principal.PrincipalUser;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/customer")
//public class CustomerController {
//    private final CustomerService customerService;
//
//    //전체 조회
//    @GetMapping("/v1/customers")
//    public ResponseEntity<ApiResponse> getAll(Authentication authentication,
//                                              Pageable pageable,
//                                              @RequestParam(required = false) String title) {
//        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(customerService.getAll(pageable, title)));
//    }
//
//    // 상세조회
//    @GetMapping("/v1/customers/{customerId}")
//    public ResponseEntity<ApiResponse> get(Authentication authentication,
//                                           Pageable pageable,
//                                           @PathVariable Long customerId) {
//        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(customerService.getOne(pageable, customerId)));
//    }
//
//    // 정보 수정
//    @PutMapping("/v1/customers/{customerId}")
//    public ResponseEntity<ApiResponse> update(Authentication authentication,
//                                              @PathVariable Long customerId,
//                                              @RequestBody CustomerRequest request) {
//        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(customerService.update(customerId, request)));
//    }
//
//    //상태 수정
//    @PatchMapping("/v1/customers/{customerId}")
//    public ResponseEntity<ApiResponse> updateStatus(Authentication authentication,
//                                              @PathVariable Long customerId,
//                                              @RequestBody CustomerRequest request) {
//        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(customerService.update(customerId, request)));
//    }
//
//    //삭제
//    @DeleteMapping("/v1/customers/{customerId}")
//    public ResponseEntity<Void> delete(Authentication authentication,
//                                       @PathVariable Long customerId) {
//        customerService.delete(customerId, ((PrincipalUser) authentication.getPrincipal()).getId());
//        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//    }
//}
