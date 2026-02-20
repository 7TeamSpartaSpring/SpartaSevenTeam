package com.seventeamproject.api.customer.repository;

import com.seventeamproject.api.customer.dto.CustomersResponse;
import com.seventeamproject.api.customer.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long>, CustomerRespositoryCustom {
//    Page<CustomersResponse> search(Pageable pageable, String title);
}
