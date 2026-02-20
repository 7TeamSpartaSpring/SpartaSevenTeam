package com.seventeamproject.api.auth.service;

import com.seventeamproject.api.admin.entity.Admin;
import com.seventeamproject.api.admin.repository.AdminRepository;
import com.seventeamproject.api.auth.dto.SigninRequest;
import com.seventeamproject.api.auth.dto.SigninResponse;
import com.seventeamproject.api.auth.dto.SignupRequest;
import com.seventeamproject.api.auth.dto.SignupResponse;
import com.seventeamproject.common.security.jwt.JwtProvider;
import com.seventeamproject.api.admin.enums.AdminRoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Transactional
    public SignupResponse signup(SignupRequest request) {
//        return SignupResponse.from(adminRepository.save(new Admin(
//                request.email(),
//                passwordEncoder.encode(request.password()),
//                request.name(),
//                AdminRoleEnum.ADMIN,
//                true
//        )));
        return null;
    }

    @Transactional
    public SigninResponse signin(SigninRequest request) {
//        Admin admin = adminRepository.findByEmail(request.email())
//                .orElseThrow(() -> new RuntimeException("유저 없음"));
//        if (!passwordEncoder.matches(request.password(), admin.getPassword())) {
//            throw new RuntimeException("비밀번호 불일치");
//        }
//        return new SigninResponse(jwtProvider.createToken(admin.getId(), admin.getEmail(), admin.getRole()));
        return null;
    }
}
