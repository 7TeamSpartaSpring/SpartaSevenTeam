package com.seventeamproject.common.exception;

import lombok.Getter;

@Getter
public class ProductException extends RuntimeException {

    private final ErrorCode errorCode;

    public ProductException( ErrorCode errorCode) {
        super(errorCode.getMessage()); // 부모(RuntimeException)에게 메시지 전달
        this.errorCode = errorCode;
    }
}
