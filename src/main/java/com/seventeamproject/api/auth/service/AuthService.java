package com.seventeamproject.api.auth.service;

import com.seventeamproject.api.admin.entity.Admin;
import com.seventeamproject.api.admin.enums.AdminStatusEnum;
import com.seventeamproject.api.admin.repository.AdminRepository;
import com.seventeamproject.api.auth.dto.LoginRequest;
import com.seventeamproject.api.auth.dto.LoginResponse;
import com.seventeamproject.api.auth.dto.SignupRequest;
import com.seventeamproject.api.auth.dto.SignupResponse;
import com.seventeamproject.common.security.jwt.JwtProvider;
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

    // 회원가입 신청 로직
    @Transactional
    public SignupResponse signup(SignupRequest request) {

        if (adminRepository.existsByEmailAndDeletedAtIsNull(request.email())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다");
        }

        if (adminRepository.existsByPhoneAndDeletedAtIsNull(request.phone())) {
            throw new IllegalArgumentException("이미 사용 중인 전화번호입니다");
        }

        Admin admin = new Admin(
                request.email(),
                passwordEncoder.encode(request.password()),
                request.name(),
                request.phone(),
                request.role()
        );

        return SignupResponse.from(adminRepository.save(admin));
    }

    // 로그인 로직
    @Transactional
    public LoginResponse login(LoginRequest request) {

        Admin admin = adminRepository.findByEmailAndDeletedAtIsNull(request.email()).orElseThrow(
                () -> new IllegalArgumentException("이메일 또는 비밀번호가 올바르지 않습니다")
        );

        if (!passwordEncoder.matches(request.password(), admin.getPassword())) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 올바르지 않습니다");
        }

        if (admin.getStatus() != AdminStatusEnum.ACTIVE || admin.isDeleted()) {
            throw new IllegalStateException("로그인 할 수 없는 계정 상태입니다: " + admin.getStatus());
        }

        String token = jwtProvider.createToken(admin.getId(), admin.getEmail(), admin.getRole());
        return new LoginResponse(token);
    }
}
