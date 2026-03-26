package com.puntored.ms.recharge.service.domain.spi;

import com.puntored.ms.recharge.service.domain.model.UserModel;

public interface IUserPersistencePort {

    UserModel findByEmail(String email);
}
