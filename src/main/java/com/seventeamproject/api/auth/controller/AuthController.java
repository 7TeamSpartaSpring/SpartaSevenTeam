package com.seventeamproject.api.auth.controller;


import com.seventeamproject.api.auth.dto.SigninRequest;
import com.seventeamproject.api.auth.dto.SignupRequest;
import com.seventeamproject.api.auth.service.AuthService;
import com.seventeamproject.common.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/auth/signup")
    public ResponseEntity<ApiResponse> adminSignup(@RequestBody SignupRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(authService.signup(request)));
    }

    @PostMapping("/auth/signin")
    public ResponseEntity<ApiResponse> adminSignin(@RequestBody SigninRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(authService.signin(request)));
    }
}