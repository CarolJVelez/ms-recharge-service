package com.puntored.ms.recharge.service.domain.exception;

import com.puntored.ms.recharge.service.domain.utils.ErrorCode;

public class CustomException extends RuntimeException{

    private final String code;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

    public String getCode() {
        return code;
    }
}
