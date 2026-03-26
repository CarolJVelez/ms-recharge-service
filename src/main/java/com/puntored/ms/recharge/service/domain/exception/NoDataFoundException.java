package com.puntored.ms.recharge.service.domain.exception;

import com.puntored.ms.recharge.service.domain.utils.ErrorCode;

public class NoDataFoundException extends CustomException {
    public NoDataFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
