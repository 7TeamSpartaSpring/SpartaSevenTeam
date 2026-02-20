package com.seventeamproject.api.customer.repository;

import com.seventeamproject.api.customer.dto.CustomersResponse;
import com.seventeamproject.api.customer.entity.CustomerStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerRepositoryCustom {
    Page<CustomersResponse> search(Pageable pageable, String keyword, CustomerStatus status);
}
