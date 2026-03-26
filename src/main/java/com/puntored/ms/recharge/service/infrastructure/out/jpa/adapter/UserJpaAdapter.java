package com.puntored.ms.recharge.service.infrastructure.out.jpa.adapter;

import com.puntored.ms.recharge.service.domain.model.UserModel;
import com.puntored.ms.recharge.service.domain.spi.IUserPersistencePort;
import com.puntored.ms.recharge.service.infrastructure.out.jpa.mapper.IUserEntityMapper;
import com.puntored.ms.recharge.service.infrastructure.out.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class UserJpaAdapter implements IUserPersistencePort {

    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;

    @Override
    public UserModel findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userEntityMapper::toModel)
                .orElse(null);
    }
}
