package com.seventeamproject.common.init;

import com.seventeamproject.admin.entity.Admin;
import com.seventeamproject.admin.enums.AdminRoleEnum;
import com.seventeamproject.admin.repository.AdminRepository;
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
        if (!adminRepository.existsByRole(AdminRoleEnum.SUPER_ADMIN)) {
            Admin superAdmin = new Admin(
                    "admin@sparta.com",
                    passwordEncoder.encode("sparta1234"),
                    "admin",
                    AdminRoleEnum.SUPER_ADMIN,
                    true
            );
            adminRepository.save(superAdmin);

        }
    }
}