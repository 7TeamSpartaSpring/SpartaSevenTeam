//package com.seventeamproject.api.customer.service;
//
//import com.seventeamproject.api.customer.dto.CustomerRequest;
//import com.seventeamproject.api.customer.dto.CustomerResponse;
//import com.seventeamproject.api.customer.dto.CustomersResponse;
//import com.seventeamproject.api.customer.repository.CustomerRepository;
//import com.seventeamproject.common.dto.PageResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Service
//@RequiredArgsConstructor
//@Transactional(readOnly = true)
//public class CustomerService {
//    private final CustomerRepository customerRepository;
//
//    //전체 조회
//    public PageResponse<CustomersResponse> getAll(Pageable pageable, String title) {
//        return new PageResponse<>(customerRepository.search(pageable, title));
//    }
//
//    //단건 조회
//    public CustomerResponse getOne(Pageable pageable, Long id) {
//        return new CustomerResponse(
//                customerRepository.findById(id).orElseThrow(() -> new IllegalStateException("적절한 에러 메세지"))
////                manyService.getAll(pageable, id, null))
//        );
//    }
//
//    //정보 수정
//    @Transactional
//    public CustomerResponse update(Long id, CustomerRequest request) {
//        return new CustomerResponse(customerRepository.findById(id).orElseThrow(
//                () -> new IllegalStateException("적절한 에러 메세지")).update(request.name(), request.email(), request.phone())
//        );
//    }
//
//    //상태 수정
//    @Transactional
//    public CustomerResponse updateStatus(Long id, CustomerRequest request) {
//        return new CustomerResponse(customerRepository.findById(id).orElseThrow(
//                () -> new IllegalStateException("적절한 에러 메세지")).updateStatus(request.status())
//        );
//    }
//
//    //단건삭제
//    @Transactional
//    public void delete(Long customerId, Long userId) {
//        customerRepository.findById(customerId).orElseThrow(
//                () -> new IllegalStateException("적절한 에러 메세지")
//        ).delete(userId);
//    }
//}
