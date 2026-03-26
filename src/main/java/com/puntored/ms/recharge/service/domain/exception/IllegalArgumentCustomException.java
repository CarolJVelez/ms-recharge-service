package com.puntored.ms.recharge.service.domain.exception;

import com.puntored.ms.recharge.service.domain.utils.ErrorCode;

public class IllegalArgumentCustomException extends CustomException {
    public IllegalArgumentCustomException(ErrorCode errorCode) {
        super(errorCode);
    }
}
