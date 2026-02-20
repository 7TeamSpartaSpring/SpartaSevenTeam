package com.seventeamproject.api.auth.dto;

public record SignupRequest(String email, String password, String name) {
}
