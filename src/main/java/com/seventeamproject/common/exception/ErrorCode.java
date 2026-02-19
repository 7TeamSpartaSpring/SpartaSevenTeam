package com.seventeamproject.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;

/**
 * 애플리케이션에서 발생할 수 있는 에러들을 정의한 Enum 클래스입니다.
 * 에러 코드, 상태 코드, 메시지를 한 곳에서 관리하여 일관된 에러 응답을 보장합니다.
 */
@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // Member 관련 에러 코드 (M으로 시작)
    // BAD_REQUEST(400): 클라이언트가 잘못된 요청을 보냄
    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "M001", "이미 존재하는 이메일입니다."),
    DUPLICATE_USERNAME(HttpStatus.BAD_REQUEST, "M002", "이미 존재하는 사용자 이름입니다."),
    DUPLICATE_PHONE(HttpStatus.BAD_REQUEST, "M003", "이미 존재하는 전화번호입니다."),
    // NOT_FOUND(404): 리소스를 찾을 수 없음
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "M004", "회원을 찾을 수 없습니다."),
    // UNAUTHORIZED(401): 인증 실패 (비밀번호 틀림 등)
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "M005", "비밀번호가 일치하지 않습니다."),
    INVALID_PROFILE(HttpStatus.BAD_REQUEST, "P007", "사용자가 일치하지 않습니다."),
    STATE_NOT_LOGIN(HttpStatus.BAD_REQUEST,"P008","로그인되어있지않습니다."),

    // Common (공통) 에러 코드 (C로 시작)
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C001", "잘못된 입력값입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C002", "서버 내부 오류가 발생했습니다.");

    private final HttpStatus status; // HTTP 상태 코드 (예: 400, 404, 500)
    private final String code;       // 우리가 정의한 고유 에러 코드 (예: M001)
    private final String message;    // 사용자에게 보여줄 에러 메시지
}