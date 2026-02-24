package com.seventeamproject.common.exception;

import lombok.Getter;

@Getter
public class CustomerException extends RuntimeException {

    private final ErrorCode errorCode;

    public CustomerException(ErrorCode errorCode) {
        super(errorCode.getMessage()); // 부모(RuntimeException)에게 메시지 전달
        this.errorCode = errorCode;
    }
}
