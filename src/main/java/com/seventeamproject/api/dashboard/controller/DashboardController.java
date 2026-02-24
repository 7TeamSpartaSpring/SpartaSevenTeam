package com.seventeamproject.api.dashboard.controller;

import com.seventeamproject.api.dashboard.service.DashboardService;
import com.seventeamproject.common.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dashboard")
public class DashboardController {
    private final DashboardService dashboardService;
    @GetMapping("/v1/dashboards")
    public ResponseEntity<ApiResponse> getDashboard(Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).
                body(ApiResponse.success(dashboardService.getDashboard()));
    }
}
