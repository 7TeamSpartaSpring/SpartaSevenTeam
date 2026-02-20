package com.seventeamproject.common.init;

import com.seventeamproject.api.admin.entity.Admin;
import com.seventeamproject.api.admin.enums.AdminRoleEnum;
import com.seventeamproject.api.admin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitRunner implements CommandLineRunner {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        // 슈퍼 관리자
        if (!adminRepository.existsByEmailAndDeletedAtIsNull("admin@sparta.com")) {
            Admin superAdmin = Admin.seed(
                    "admin@sparta.com",
                    passwordEncoder.encode("sparta1234"),
                    "admin",
                    "010-0000-0000",
                    AdminRoleEnum.SUPER_ADMIN
            );
            adminRepository.save(superAdmin);
        }

        // 운영 관리자
        if (!adminRepository.existsByEmailAndDeletedAtIsNull("operation@sparta.com")) {
            Admin operationAdmin = Admin.seed(
                    "operation@sparta.com",
                    passwordEncoder.encode("sparta1234"),
                    "김운영",
                    "010-1111-1111",
                    AdminRoleEnum.OPERATION_ADMIN
            );
            adminRepository.save(operationAdmin);
        }

        // CS 관리자
        if (!adminRepository.existsByEmailAndDeletedAtIsNull("cs@sparta.com")) {
            Admin csAdmin = Admin.seed(
                    "cs@sparta.com",
                    passwordEncoder.encode("password123"),
                    "이고객",
                    "010-2222-2222",
                    AdminRoleEnum.CS_ADMIN
            );
            adminRepository.save(csAdmin);
        }
    }
}