package com.seventeamproject.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse<T>(
        boolean success,       // 성공 여부 (true/false)
        T data,               // 성공 시 실제 데이터 (제네릭)
        ErrorResponse error   // 실패 시 에러 정보
) {
    // 1. 성공 응답 생성 (data만 넣으면 됨)
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, data, null);
    }

    // 2. 실패 응답 생성 (ErrorResponse 통째로 넣기)
    public static ApiResponse<Void> fail(ErrorResponse errorResponse) {
        return new ApiResponse<>(false, null, errorResponse);
    }

    // 3. 실패 응답 생성 (코드와 메시지만 넣기)
    public static ApiResponse<Void> fail(String code, String message) {
        return new ApiResponse<>(false, null, ErrorResponse.builder()
                .message(message)
                .code(code)
                .build());
    }
}