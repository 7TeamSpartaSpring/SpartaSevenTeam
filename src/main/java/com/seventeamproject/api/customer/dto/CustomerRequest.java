package com.seventeamproject.api.customer.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CustomerRequest(
        @NotBlank(message = "이름은 필수입니다.")
        String name,
        @NotBlank(message = "이메일은 필수입니다.")
        @Email
        String email,
        @NotBlank(message = "전화번호는 필수입니다.")
        @Pattern(
                regexp = "^(\\+\\d{1,3}-)?\\d{1,4}-\\d{3,4}-\\d{4}$", //전화번호 양식, 국가번호는 선택
                message = "전화번호 양식이 올바르지 않습니다."
        )
        String phone
) {
}
