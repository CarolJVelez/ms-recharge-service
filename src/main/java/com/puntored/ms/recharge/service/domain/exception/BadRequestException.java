package com.puntored.ms.recharge.service.domain.exception;

import com.puntored.ms.recharge.service.domain.utils.ErrorCode;

public class BadRequestException extends CustomException {
    public BadRequestException(ErrorCode errorCode) {
        super(errorCode);
    }
}
