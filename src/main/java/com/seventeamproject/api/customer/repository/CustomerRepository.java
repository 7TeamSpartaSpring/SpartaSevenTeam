package com.seventeamproject.api.customer.repository;

import com.seventeamproject.api.customer.dto.CustomersResponse;
import com.seventeamproject.api.customer.entity.Customer;
import com.seventeamproject.api.customer.entity.CustomerStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long>, CustomerRepositoryCustom {
//    Page<CustomersResponse> search(Pageable pageable, String keyword, int page, int size, String sortBy, String direction, CustomerStatus status);
}
