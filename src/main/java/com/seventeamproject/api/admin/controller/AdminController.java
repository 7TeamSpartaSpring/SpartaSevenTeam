package com.seventeamproject.api.admin.controller;

import com.seventeamproject.common.security.principal.PrincipalUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminController {
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @GetMapping("/admin/info")
    public ResponseEntity<String> getAdminInfo(Authentication authentication) {
        PrincipalUser user = (PrincipalUser) authentication.getPrincipal();
        String userId = user.getEmail();

        return ResponseEntity.ok("현재 사용자: " + userId);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @GetMapping("/admin/super/info")
    public ResponseEntity<String> getSuperAdminInfo(Authentication authentication) {
        PrincipalUser user = (PrincipalUser) authentication.getPrincipal();
        String userId = user.getEmail();

        return ResponseEntity.ok("현재 사용자: " + userId);
    }
}
