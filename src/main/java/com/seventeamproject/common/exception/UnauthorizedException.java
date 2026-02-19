package com.seventeamproject.common.exception;

// 인증이 필요한 요청에서 세션이 없을 때 발생시키는 예외
public class UnauthorizedException extends RuntimeException{
    public UnauthorizedException(String message) {
        super(message);
    }
}