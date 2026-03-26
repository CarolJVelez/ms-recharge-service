package com.puntored.ms.recharge.service.domain.api;

import com.puntored.ms.recharge.service.domain.model.UserModel;

public interface IUserServicePort {

    UserModel findByEmail(String email);

}
