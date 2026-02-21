package com.seventeamproject.api.customer.repository;


import com.seventeamproject.api.customer.entity.Customer;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long>, CustomerRepositoryCustom {
    Optional<Customer> findByEmail(@NotBlank String email);
    Optional<Customer> findByPhone(@NotBlank String phone);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
}
