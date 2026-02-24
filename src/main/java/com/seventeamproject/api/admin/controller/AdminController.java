package com.seventeamproject.api.admin.controller;

import com.seventeamproject.api.admin.dto.*;
import com.seventeamproject.api.admin.service.AdminService;
import com.seventeamproject.common.dto.ApiResponse;
import com.seventeamproject.common.dto.PageResponse;
import com.seventeamproject.common.security.principal.PrincipalUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    // 관리자 내 프로필 조회
    @GetMapping("/v1/admin/me")
    public ResponseEntity<ApiResponse<AdminResponse>> me(Authentication authentication) {
        PrincipalUser principal = (PrincipalUser) authentication.getPrincipal();
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(adminService.getMe(principal.getId())));
    }

    // 관리자 내 프로필 수정
    @PutMapping ("/v1/admin/me")
    public ResponseEntity<ApiResponse<AdminResponse>> updateMe(
            Authentication authentication,
            @Valid @RequestBody UpdateMeRequest request
    ) {
        PrincipalUser principal = (PrincipalUser) authentication.getPrincipal();
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(adminService.updateMe(principal.getId(), request)));
    }

    // 관리자 내 비밀번호 변경
    @PatchMapping("/v1/admin/me/password")
    public ResponseEntity<ApiResponse<String>> changePassword(
            Authentication authentication,
            @Valid @RequestBody ChangePasswordRequest request
    ) {
        PrincipalUser principal = (PrincipalUser) authentication.getPrincipal();

        adminService.changePassword(principal.getId(), request);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("비밀번호 변경 완료"));
    }

    // 관리자 승인 (SUPER_ADMIN만 가능)
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PatchMapping("/v1/admin/{adminId}/approve")
    public ResponseEntity<ApiResponse<String>> approve(@PathVariable Long adminId) {
        adminService.approve(adminId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("승인 완료"));
    }

    // 관리자 거부 (SUPER_ADMIN만 가능)
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PatchMapping("/v1/admin/{adminId}/reject")
    public ResponseEntity<ApiResponse<String>> reject(
            @PathVariable Long adminId,
            @Valid @RequestBody RejectRequest request
    ) {
        adminService.reject(adminId, request.reason());
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("거부 완료"));
    }

    // 관리자 관리용 - 관리자 목록 조회 (SUPER_ADMIN만 가능)
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @GetMapping("/v1/admin")
    public ResponseEntity<ApiResponse<PageResponse<AdminResponse>>> getAdmin(
            Pageable pageable,
            @ModelAttribute AdminSearchRequest request
    ) {
        PageResponse<AdminResponse> result = adminService.getAdmin(pageable, request);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(result));
    }

    // 관리자 관리용 - 관리자 목록 상세 조회 (SUPER_ADMIN만 가능)
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @GetMapping("/v1/admin/{adminId}")
    public ResponseEntity<ApiResponse<AdminResponse>> getAdminDetail(
            @PathVariable Long adminId
    ) {
        AdminResponse result = adminService.getAdminDetail(adminId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(result));
    }

    // 관리자 관리용 - 관리자 정보 수정 (SUPER_ADMIN만 가능)
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PutMapping("/v1/admin/{adminId}")
    public ResponseEntity<ApiResponse<AdminResponse>> updateAdmin(
            @PathVariable Long adminId,
            @Valid @RequestBody UpdateAdminRequest request
    ) {
        AdminResponse result = adminService.updateAdmin(adminId, request);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(result));
    }

    // 관리자 관리용 - 관리자 상태 변경 (SUPER_ADMIN만 가능)
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PatchMapping("/v1/admin/{adminId}/status")
    public ResponseEntity<ApiResponse<String>> changeStatus(
            @PathVariable Long adminId,
            @Valid @RequestBody ChangeStatusRequest request
    ) {
        adminService.changeStatus(adminId, request.status());
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("상태 변경 완료"));
    }

    // 관리자 관리용 - 관리자 역할 변경 (SUPER_ADMIN만 가능)
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PatchMapping("/v1/admin/{adminId}/role")
    public ResponseEntity<ApiResponse<String>> changeRole(
            Authentication authentication,
            @PathVariable Long adminId,
            @Valid @RequestBody ChangeRoleRequest request
    ) {
        PrincipalUser principal = (PrincipalUser) authentication.getPrincipal();
        adminService.changeRole(principal.getId(), adminId, request.role());
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success("역할 변경 완료"));
    }

    // 관리자 관리용 - 관리자 삭제 (SUPER_ADMIN만 가능)
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @DeleteMapping("/v1/admin/{adminId}")
    public ResponseEntity<ApiResponse<String>> deleteAdmin(
            Authentication authentication,
            @PathVariable Long adminId
    ) {
        PrincipalUser principal = (PrincipalUser) authentication.getPrincipal();
        adminService.deleteAdmin(principal.getId(), adminId);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("삭제 완료"));
    }
}
