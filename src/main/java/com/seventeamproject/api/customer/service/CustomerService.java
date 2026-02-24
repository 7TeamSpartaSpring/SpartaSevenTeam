package com.seventeamproject.api.customer.service;

import com.seventeamproject.api.customer.dto.*;
import com.seventeamproject.api.customer.enums.CustomerStatus;
import com.seventeamproject.api.customer.repository.CustomerRepository;
import com.seventeamproject.common.dto.PageResponse;
import com.seventeamproject.common.exception.ErrorCode;
import com.seventeamproject.common.exception.MemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerService {
    private final CustomerRepository customerRepository;

    //전체 조회
    @Transactional(readOnly = true)
    public PageResponse<CustomersResponse> search(
            Pageable pageable,
            CustomerSearchRequest request
    ) {
        CustomerStatus customerStatus = //비어있지않으면 enum에서 값을 받아옴
                request.stat() != null ? CustomerStatus.fromStat(request.stat()) : null;
        return new PageResponse<>(customerRepository.search(pageable, request.keyword(), customerStatus));
    }

    //단건 조회
    @Transactional(readOnly = true)
    public GetCustomerResponse getOne(Long id) {
        GetCustomerResponse response = customerRepository.searchOne(id);

        if(response == null){
            throw new MemberException(ErrorCode.CUSTOMER_NOT_FOUND);
        } //고객을 찾았지만 없음
        return response;
    }

    //정보 수정
    @Transactional
    public CustomerResponse update(Long id, CustomerRequest request) {
        if(customerRepository.existsByEmailAndIdNot(request.email(), id)){
            throw new MemberException(ErrorCode.DUPLICATE_EMAIL);
        }// 이메일은 같고 id는 다를때
        if(customerRepository.existsByPhoneAndIdNot(request.phone(),id)){
            throw new MemberException(ErrorCode.DUPLICATE_PHONE);
        }//전화번호는 같고 id는 다를때
        return new CustomerResponse(customerRepository.findById(id).orElseThrow(
                () -> new MemberException(ErrorCode.CUSTOMER_NOT_FOUND)
        ).update(request.name(), request.email(), request.phone())); //db 업데이트
    }

    //상태 수정
    @Transactional
    public CustomerResponse updateStatus(Long id, CustomerStatusRequest request) {
        return new CustomerResponse(customerRepository.findById(id).orElseThrow(
                () -> new MemberException(ErrorCode.CUSTOMER_NOT_FOUND)
        ).updateStatus(CustomerStatus.fromStat(request.status()))); // db 업데이트
    }

    //단건삭제
    @Transactional
    public void delete(Long customerId, Long userId) {
        customerRepository.findById(customerId).orElseThrow(
                () -> new MemberException(ErrorCode.CUSTOMER_NOT_FOUND)
        ).delete(userId);
    }
}
