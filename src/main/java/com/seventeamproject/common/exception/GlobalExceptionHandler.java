package com.seventeamproject.common.exception;

import com.seventeamproject.common.dto.ApiResponse;
import com.seventeamproject.common.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

/**
 * 전역 예외 처리기 (Global Exception Handler)
 * 컨트롤러 전역에서 발생하는 예외를 감지하고, 적절한 ErrorResponse를 반환하는 역할을 합니다.
 * @RestControllerAdvice: 모든 @RestController에서 발생한 예외를 잡아서 처리합니다.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 비즈니스 로직 실행 중 발생하는 MemberException을 처리합니다.
     * 예: 중복된 이메일 가입 시도 등
     */
    @ExceptionHandler(MemberException.class)
    public ResponseEntity<ApiResponse<Void>> handleMemberException(MemberException e, HttpServletRequest request) {
        log.warn("MemberException : {}", e.getMessage()); // 서버 로그에 에러 기록
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity
                .status(errorCode.getStatus()) // ErrorCode에 정의된 HTTP 상태 코드 사용
                .body(ApiResponse.fail(buildErrorResponse(errorCode, e.getMessage(), request.getRequestURI())));
    }

    /**
     * 400: 잘못된 요청을 공통 에러 응답으로 반환
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleIllegalArgumentException(IllegalArgumentException e, HttpServletRequest request) {
        log.warn("IllegalArgumentException : {}", e.getMessage());
        return ResponseEntity
                .badRequest() // 400 Bad Request
                .body(ApiResponse.fail(buildErrorResponse(ErrorCode.INVALID_INPUT_VALUE, e.getMessage(), request.getRequestURI())));
    }

    /**
     * 404: 리소스가 존재하지 않는 경우 공통 에러 응답으로 반환
     */
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiResponse<Void>> handleIllegalStateException(IllegalStateException e, HttpServletRequest request) {
        log.warn("IllegalStateException : {}", e.getMessage());
        return ResponseEntity
                .badRequest() // 404 Not Found
                .body(ApiResponse.fail(buildErrorResponse(ErrorCode.MEMBER_NOT_FOUND, e.getMessage(), request.getRequestURI())));
    }

    /**
     * @Valid 어노테이션을 통한 입력값 검증 실패 시 발생하는 예외를 처리합니다.
     * 예: 비밀번호가 정규표현식에 맞지 않거나, 필수값이 누락된 경우
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        log.warn("MethodArgumentNotValidException : {}", e.getMessage());
        // 검증 실패 메시지 중 첫 번째 메시지를 가져옵니다.
        String errorMessage = e.getBindingResult().getFieldError().getDefaultMessage();
        return ResponseEntity
                .badRequest() // 400 Bad Request
                .body(ApiResponse.fail(buildErrorResponse(ErrorCode.INVALID_INPUT_VALUE, errorMessage, request.getRequestURI())));
    }

    /**
     * 400: QueryParam/pathVariable Bean Validation 실패(@Min/@Max) 시
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Void>> handleConstraintViolationException(ConstraintViolationException e, HttpServletRequest request) {
        log.warn("ConstraintViolationException : {}", e.getMessage());
        // 검증 실패 메시지 중 첫 번째 메시지를 가져옵니다.
        String errorMessage = e.getConstraintViolations()
                .stream()
                .findFirst()
                .map(v -> v.getMessage())
                .orElse("요청 값이 유효하지 않음");
        return ResponseEntity
                .badRequest() // 400 Bad Request
                .body(ApiResponse.fail(buildErrorResponse(ErrorCode.INVALID_INPUT_VALUE, errorMessage, request.getRequestURI())));
    }

    /**
     * 401: 인증되지 않은 요청(세션이 없을 때 등)에 대해 로그인 필요 응답 반환
     */
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiResponse<Void>> handleUnauthorizedException(UnauthorizedException e, HttpServletRequest request) {
        log.warn("UnauthorizedException : {}", e.getMessage());
        return ResponseEntity
                .badRequest() // 400 Bad Request
                .body(ApiResponse.fail(buildErrorResponse(ErrorCode.INVALID_PASSWORD, e.getMessage(), request.getRequestURI())));
    }

    /**
     * 위에서 처리하지 못한 나머지 모든 예외(Exception)를 처리합니다.
     * 예상치 못한 서버 에러(NullPointerException 등)가 여기에 해당합니다.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception e, HttpServletRequest request) {
        log.error("Exception : {}", e.getMessage());
        // 보안을 위해 내부 에러 메시지는 숨기고, "서버 내부 오류"라는 일반적인 메시지를 반환합니다.
        return ResponseEntity
                .internalServerError() // 500 Internal Server Error
                .body(ApiResponse.fail(buildErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR, "알 수 없는 에러가 발생했습니다.", request.getRequestURI())));
    }

    /**
     * ErrorResponse 객체를 생성하는 유틸리티 메서드입니다.
     */
    private ErrorResponse buildErrorResponse(ErrorCode errorCode, String message, String path) {
        return ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(errorCode.getStatus().value())
                .error(errorCode.getStatus().name())
                .code(errorCode.getCode())
                .message(message)
                .path(path)
                .build();
    }
}