package com.seventeamproject.api.customer.service;

import com.seventeamproject.api.customer.entity.Customer;
import com.seventeamproject.api.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomerReader {
    private final CustomerRepository customerRepository;

    public Customer getEntity(Long id){
        return customerRepository.findById(id).orElseThrow(() -> new IllegalStateException("존재하지않는 고객입니다."));
    }
}
