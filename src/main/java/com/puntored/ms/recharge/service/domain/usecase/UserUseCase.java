package com.puntored.ms.recharge.service.domain.usecase;

import com.puntored.ms.recharge.service.domain.api.IUserServicePort;
import com.puntored.ms.recharge.service.domain.exception.IllegalArgumentCustomException;
import com.puntored.ms.recharge.service.domain.exception.NoDataFoundException;
import com.puntored.ms.recharge.service.domain.model.UserModel;
import com.puntored.ms.recharge.service.domain.spi.IUserPersistencePort;
import com.puntored.ms.recharge.service.domain.utils.ErrorCode;

public class UserUseCase implements IUserServicePort {

    private final IUserPersistencePort iUserPersistencePort;

    public UserUseCase(IUserPersistencePort iUserPersistencePort) {
        this.iUserPersistencePort = iUserPersistencePort;
    }

    @Override
    public UserModel findByEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentCustomException(ErrorCode.INVALID_EMAIL);
        }
        UserModel user = iUserPersistencePort.findByEmail(email);
        if (user == null) {
            throw new NoDataFoundException(ErrorCode.USER_NOT_FOUND);
        }
        return user;
    }
}
