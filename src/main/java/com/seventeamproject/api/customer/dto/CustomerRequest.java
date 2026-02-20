package com.seventeamproject.api.customer.dto;


import jakarta.validation.constraints.NotBlank;

public record CustomerRequest(
        @NotBlank(message = "이름은 필수입니다.")
        String name,
        @NotBlank(message = "이메일은 필수입니다.")
        String email,
        @NotBlank(message = "전화번호는 필수입니다.")
        String phone,
        String status
) {
}
