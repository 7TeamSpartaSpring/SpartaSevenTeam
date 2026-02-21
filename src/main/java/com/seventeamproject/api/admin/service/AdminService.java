package com.seventeamproject.api.admin.service;

import com.seventeamproject.api.admin.dto.AdminMeResponse;
import com.seventeamproject.api.admin.dto.ChangePasswordRequest;
import com.seventeamproject.api.admin.dto.UpdateMeRequest;
import com.seventeamproject.api.admin.entity.Admin;
import com.seventeamproject.api.admin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    // 내 프로필 조회
    public AdminMeResponse getMe(Long loginAdminId) {
        Admin admin = getAdminOrThrow(loginAdminId);
        return AdminMeResponse.from(admin);
    }

    // 내 프로필 수정
    @Transactional
    public AdminMeResponse updateMe(Long loginAdminId, UpdateMeRequest request) {
        Admin admin = getAdminOrThrow(loginAdminId);

        if (!admin.getEmail().equals(request.email())
                && adminRepository.existsByEmailAndDeletedAtIsNull(request.email())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다");
        }

        if (!admin.getPhone().equals(request.phone())
                && adminRepository.existsByPhoneAndDeletedAtIsNull(request.phone())) {
            throw new IllegalArgumentException("이미 사용 중인 전화번호입니다");
        }
        admin.updateProfile(request.name(), request.email(), request.phone());
        return AdminMeResponse.from(admin);
    }

    // 내 비밀번호 변경
    @Transactional
    public void changePassword(Long loginAdminId, ChangePasswordRequest request) {
        Admin admin = getAdminOrThrow(loginAdminId);

        if (!passwordEncoder.matches(request.currentPassword(), admin.getPassword())) {
            throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다");
        }

        admin.changePassword(passwordEncoder.encode(request.newPassword()));
    }

    // 승인 (PENDING -> ACTIVE)
    @Transactional
    public void approve(Long adminId) {
        Admin admin = getAdminOrThrow(adminId);
        admin.approve();
    }

    // 거부 (PENDING -> REJECTED)
    @Transactional
    public void reject(Long adminId, String reason) {
        Admin admin = getAdminOrThrow(adminId);
        admin.reject(reason);
    }

    private Admin getAdminOrThrow(Long adminId) {
        return adminRepository.findById(adminId).orElseThrow(
                () -> new IllegalStateException("관리자를 찾을 수 없습니다")
        );
    }
}
