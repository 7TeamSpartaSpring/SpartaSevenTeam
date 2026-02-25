package com.seventeamproject.api.auth.controller;


import com.seventeamproject.api.auth.dto.LoginRequest;
import com.seventeamproject.api.auth.dto.LoginResponse;
import com.seventeamproject.api.auth.dto.SignupRequest;
import com.seventeamproject.api.auth.dto.SignupResponse;
import com.seventeamproject.api.auth.service.AuthService;
import com.seventeamproject.common.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    // 관리자 회원가입 신청
    @PostMapping("/v1/admin/signup")
    public ResponseEntity<ApiResponse> adminSignup(
            @Valid @RequestBody SignupRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(authService.signup(request)));
    }

    // 관리자 로그인
    @PostMapping("/v1/admin/login")
    public ResponseEntity<ApiResponse> adminLogin(
            @Valid @RequestBody LoginRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(authService.login(request)));
    }

    // 로그아웃
    @PostMapping("/v1/admin/logout")
    public ResponseEntity<ApiResponse> logout() {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("로그아웃 완료"));
    }
}