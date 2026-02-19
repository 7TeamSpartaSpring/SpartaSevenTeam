package com.seventeamproject.common.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
// 에러가 발생했을 때 클라이언트에게 전송할 구체적인 에러내용들을 담은 JSON
public class ErrorResponse {
    private final LocalDateTime timestamp; // 에러가 발생한 시간
    private final int status;  // 에러 상태코드
    private final String error; // 에러 이름
    private final String code;             // 기획 요구사항 및 클라이언트 식별을 위해 정의한 커스텀 에러 코드 (예: M001)
    private final String message; // 에러 원인 메시지
    private final String path;   // 에러가 발생한 경로
}