package com.seventeamproject.api.customer.repository;


import com.seventeamproject.api.customer.dto.GetCustomerResponse;
import com.seventeamproject.api.customer.entity.Customer;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long>, CustomerRepositoryCustom {
    Optional<Customer> findByEmail(@NotBlank String email);
    Optional<Customer> findByPhone(@NotBlank String phone);
    boolean existsByEmailAndIdNot(String email,Long id);
    boolean existsByPhoneAndIdNot(String phone, Long id);
    //해당 id 제외하고 탐색
}
