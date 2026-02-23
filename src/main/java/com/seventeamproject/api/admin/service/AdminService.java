package com.seventeamproject.api.admin.service;

import com.seventeamproject.api.admin.dto.AdminResponse;
import com.seventeamproject.api.admin.dto.ChangePasswordRequest;
import com.seventeamproject.api.admin.dto.UpdateAdminRequest;
import com.seventeamproject.api.admin.dto.UpdateMeRequest;
import com.seventeamproject.api.admin.entity.Admin;
import com.seventeamproject.api.admin.enums.AdminRoleEnum;
import com.seventeamproject.api.admin.enums.AdminStatusEnum;
import com.seventeamproject.api.admin.repository.AdminRepository;
import com.seventeamproject.common.dto.PageResponse;
import com.seventeamproject.common.exception.ErrorCode;
import com.seventeamproject.common.exception.MemberException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
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
    public AdminResponse getMe(Long loginAdminId) {
        Admin admin = getAdminOrThrow(loginAdminId);
        return new AdminResponse(admin);
    }

    // 내 프로필 수정
    @Transactional
    public AdminResponse updateMe(Long loginAdminId, UpdateMeRequest request) {
        Admin admin = getAdminOrThrow(loginAdminId);

        if (!admin.getEmail().equals(request.email())
                && adminRepository.existsByEmailAndDeletedAtIsNull(request.email())) {
            throw new MemberException(ErrorCode.DUPLICATE_EMAIL);
        }

        if (!admin.getPhone().equals(request.phone())
                && adminRepository.existsByPhoneAndDeletedAtIsNull(request.phone())) {
            throw new MemberException(ErrorCode.DUPLICATE_PHONE);
        }
        admin.updateProfile(request.name(), request.email(), request.phone());
        return new AdminResponse(admin);
    }

    // 내 비밀번호 변경
    @Transactional
    public void changePassword(Long loginAdminId, ChangePasswordRequest request) {
        Admin admin = getAdminOrThrow(loginAdminId);

        if (!passwordEncoder.matches(request.currentPassword(), admin.getPassword())) {
            throw new MemberException(ErrorCode.INVALID_PASSWORD);
        }
        String encoded = passwordEncoder.encode(request.newPassword());
        admin.changePassword(encoded);
    }

    // 승인 (PENDING -> ACTIVE)
    @Transactional
    public void approve(Long adminId) {
        Admin admin = getAdminOrThrow(adminId);

        try {
            admin.approve();
        } catch (IllegalStateException e) {
            throw new MemberException(ErrorCode.INVALID_STATUS_CHANGE);
        }
    }

    // 거부 (PENDING -> REJECTED)
    @Transactional
    public void reject(Long adminId, String reason) {
        Admin admin = getAdminOrThrow(adminId);

        try {
            admin.reject(reason);
        } catch (IllegalStateException e) {
            throw new MemberException(ErrorCode.INVALID_STATUS_CHANGE);
        }
    }

    // 관리자 관리 목록 조회
    public PageResponse<AdminResponse> getAdmin(Pageable pageable, String keyword, AdminRoleEnum role, AdminStatusEnum status) {
        return new PageResponse<>(adminRepository.search(pageable, keyword, role, status));
    }

    // 관리자 상세 조회
    public AdminResponse getAdminDetail(Long adminId) {
        Admin admin = getAdminOrThrow(adminId);
        return new AdminResponse(admin);
    }

    // 관리자 정보 수정
    @Transactional
    public AdminResponse updateAdmin(Long adminId, UpdateAdminRequest request) {
        Admin admin = getAdminOrThrow(adminId);
        if (!admin.getEmail().equals(request.email())
                && adminRepository.existsByEmailAndDeletedAtIsNull(request.email())) {
            throw new MemberException(ErrorCode.DUPLICATE_EMAIL);
        }
        if (!admin.getPhone().equals(request.phone())
                && adminRepository.existsByPhoneAndDeletedAtIsNull(request.phone())) {
            throw new MemberException(ErrorCode.DUPLICATE_PHONE);
        }
        admin.updateProfile(
                request.name(),
                request.email(),
                request.phone()
        );
        return new AdminResponse(admin);
    }

    // 관리자 상태 변경
    @Transactional
    public void changeStatus(Long adminId, AdminStatusEnum status) {
        if (status == null) {
            throw new MemberException(ErrorCode.INVALID_INPUT_VALUE);
        }
        Admin admin = getAdminOrThrow(adminId);
        try {
            admin.changeStatus(status);
        } catch (IllegalStateException e) {
            throw new MemberException(ErrorCode.INVALID_STATUS_CHANGE);
        }
    }

    // 관리자 역할 변경
    @Transactional
    public void changeRole(Long loginAdminId, Long adminId, AdminRoleEnum role) {

        if (role == null) {
            throw new MemberException(ErrorCode.INVALID_INPUT_VALUE);
        }
        if (loginAdminId.equals(adminId)) {
            throw new MemberException(ErrorCode.INVALID_PROFILE);
        }
        Admin admin = getAdminOrThrow(adminId);
        admin.changeRole(role);
    }

    // 관리자 삭제
    @Transactional
    public void deleteAdmin(Long loginAdminId, Long adminId) {
        Admin admin = getAdminOrThrow(adminId);
        admin.delete(loginAdminId);
    }

    private Admin getAdminOrThrow(Long adminId) {
        return adminRepository.findByIdAndDeletedAtIsNull(adminId).orElseThrow(
                () -> new MemberException(ErrorCode.ADMIN_NOT_FOUND)
        );
    }
}
