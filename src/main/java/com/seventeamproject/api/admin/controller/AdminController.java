package com.seventeamproject.api.admin.controller;

import com.seventeamproject.api.admin.dto.ChangePasswordRequest;
import com.seventeamproject.api.admin.dto.RejectRequest;
import com.seventeamproject.api.admin.dto.UpdateMeRequest;
import com.seventeamproject.api.admin.service.AdminService;
import com.seventeamproject.common.dto.ApiResponse;
import com.seventeamproject.common.security.principal.PrincipalUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<ApiResponse> me(Authentication authentication) {
        PrincipalUser principal = (PrincipalUser) authentication.getPrincipal();
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(adminService.getMe(principal.getId())));
    }

    // 관리자 내 프로필 수정
    @PutMapping ("/v1/admin/me")
    public ResponseEntity<ApiResponse> updateMe(
            Authentication authentication,
            @Valid @RequestBody UpdateMeRequest request
    ) {
        PrincipalUser principal = (PrincipalUser) authentication.getPrincipal();
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(adminService.updateMe(principal.getId(), request)));
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
    public ResponseEntity<ApiResponse> approve(@PathVariable Long adminId) {
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
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success("거부 완료"));
    }


//    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
//    @GetMapping("/admin/info")
//    public ResponseEntity<String> getAdminInfo(Authentication authentication) {
//        PrincipalUser user = (PrincipalUser) authentication.getPrincipal();
//        String userId = user.getEmail();
//
//        return ResponseEntity.ok("현재 사용자: " + userId);
//    }
//
//    @PreAuthorize("hasRole('SUPER_ADMIN')")
//    @GetMapping("/admin/super/info")
//    public ResponseEntity<String> getSuperAdminInfo(Authentication authentication) {
//        PrincipalUser user = (PrincipalUser) authentication.getPrincipal();
//        String userId = user.getEmail();
//
//        return ResponseEntity.ok("현재 사용자: " + userId);
//    }
}
