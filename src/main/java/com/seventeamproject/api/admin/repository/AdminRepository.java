package com.seventeamproject.api.admin.repository;

import com.seventeamproject.api.admin.entity.Admin;
import com.seventeamproject.api.admin.enums.AdminRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    boolean existsByEmailAndDeletedAtIsNull(String email);
    boolean existsByRole(AdminRoleEnum adminRoleEnum);
    Optional<Admin> findByEmail(String email);
    Optional<Admin> findByEmailAndDeletedAtIsNull(String email);
    Optional<Admin> findByIdAndDeletedAtIsNull(Long id);
}
