package com.seventeamproject.admin.repository;

import com.seventeamproject.admin.entity.Admin;
import com.seventeamproject.admin.enums.AdminRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    boolean existsByEmail(String email);
    boolean existsByRole(AdminRoleEnum adminRoleEnum);
    Optional<Admin> findByEmail(String email);
}
