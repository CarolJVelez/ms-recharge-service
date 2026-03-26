package com.puntored.ms.recharge.service.domain.exception;

import com.puntored.ms.recharge.service.domain.utils.ErrorCode;

public class AccessDeniedException extends CustomException {
    public AccessDeniedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
