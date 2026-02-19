package com.seventeamproject.common.security.principal;

import com.seventeamproject.admin.entity.Admin;
import com.seventeamproject.admin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalUserService {

    private final AdminRepository adminRepository;

    public PrincipalUser getUserByEmail(String email){
        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("admin not found"));

        return new PrincipalUser(admin);
    }
    public PrincipalUser getUserById(Long id){
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("admin not found"));

        return new PrincipalUser(admin);
    }
}