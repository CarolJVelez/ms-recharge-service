package com.puntored.ms.recharge.service.domain.exception;

import com.puntored.ms.recharge.service.domain.utils.ErrorCode;

public class ForbiddenException extends CustomException {
    public ForbiddenException(ErrorCode errorCode) {
        super(errorCode);
    }
}
