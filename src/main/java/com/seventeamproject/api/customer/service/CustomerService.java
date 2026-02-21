package com.seventeamproject.api.customer.service;

import com.seventeamproject.api.customer.dto.CustomerRequest;
import com.seventeamproject.api.customer.dto.CustomerResponse;
import com.seventeamproject.api.customer.dto.CustomersResponse;
import com.seventeamproject.api.customer.entity.CustomerStatus;
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
@Transactional(readOnly = true)
public class CustomerService {
    private final CustomerRepository customerRepository;

    //전체 조회
    public PageResponse<CustomersResponse> getAll(
            String keyword, int page, int size,String sortBy, String direction, String stat
    ) {
        Sort sort = createSort(sortBy, direction);

        Pageable pageable = PageRequest.of(
                page - 1,   // 1페이지 → 0
                size,
                sort
        );

        CustomerStatus customerStatus =
                stat != null ? CustomerStatus.fromStat(stat) : null;
        return new PageResponse<>(customerRepository.search(pageable, keyword, customerStatus));
    }

    //단건 조회
    public CustomerResponse getOne(Pageable pageable, Long id) {
        return new CustomerResponse(
                customerRepository.findById(id).orElseThrow(
                        () -> new MemberException(ErrorCode.CUSTOMER_NOT_FOUND)
                )
        );
    }

    //정보 수정
    @Transactional
    public CustomerResponse update(Long id, CustomerRequest request) {
        if(customerRepository.existsByEmail(request.email())){
            throw new MemberException(ErrorCode.DUPLICATE_EMAIL);
        }
        if(customerRepository.existsByPhone(request.phone())){
            throw new MemberException(ErrorCode.DUPLICATE_PHONE);
        }
        return new CustomerResponse(customerRepository.findById(id).orElseThrow(
                () -> new MemberException(ErrorCode.CUSTOMER_NOT_FOUND)
        ).update(request.name(), request.email(), request.phone()));
    }

    //상태 수정
    @Transactional
    public CustomerResponse updateStatus(Long id, CustomerRequest request) {
        return new CustomerResponse(customerRepository.findById(id).orElseThrow(
                () -> new MemberException(ErrorCode.CUSTOMER_NOT_FOUND)
        ).updateStatus(CustomerStatus.fromStat(request.status())));
    }

    //단건삭제
    @Transactional
    public void delete(Long customerId, Long userId) {
        customerRepository.findById(customerId).orElseThrow(
                () -> new MemberException(ErrorCode.CUSTOMER_NOT_FOUND)
        ).delete(userId);
    }


    // 전체 조회 pageable변환용 화이트 리스트
    private Sort createSort(String sortBy, String direction) {
        String property;

        switch (sortBy) {
            case "name":
                property = "name";
                break;
            case "email":
                property = "email";
                break;
            case "phone":
                property = "phone";
                break;
            case "createdAt":
                property = "createdAt";
                break;
            default:
                property = "createdAt"; // 기본값
        }

        return direction.equalsIgnoreCase("asc")
                ? Sort.by(property).ascending()
                : Sort.by(property).descending();
    }
}
