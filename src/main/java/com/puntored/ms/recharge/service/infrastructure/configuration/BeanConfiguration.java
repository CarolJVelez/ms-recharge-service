package com.puntored.ms.recharge.service.infrastructure.configuration;

import com.puntored.ms.recharge.service.domain.api.IRechargeServicePort;
import com.puntored.ms.recharge.service.domain.api.IUserServicePort;
import com.puntored.ms.recharge.service.domain.spi.EventBus;
import com.puntored.ms.recharge.service.domain.spi.IRechargePersistencePort;
import com.puntored.ms.recharge.service.domain.spi.IUserPersistencePort;
import com.puntored.ms.recharge.service.domain.usecase.RechargeUseCase;
import com.puntored.ms.recharge.service.domain.usecase.UserUseCase;
import com.puntored.ms.recharge.service.infrastructure.out.jpa.adapter.RechargeJpaAdapter;
import com.puntored.ms.recharge.service.infrastructure.out.jpa.adapter.UserJpaAdapter;
import com.puntored.ms.recharge.service.infrastructure.out.jpa.mapper.IRechargeEntityMapper;
import com.puntored.ms.recharge.service.infrastructure.out.jpa.mapper.IUserEntityMapper;
import com.puntored.ms.recharge.service.infrastructure.out.jpa.repository.IRechargeRepository;
import com.puntored.ms.recharge.service.infrastructure.out.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;

    private final IRechargeRepository rechargeRepository;
    private final IRechargeEntityMapper rechargeEntityMapper;

    private final EventBus eventBus;

    @Bean
    public IUserPersistencePort userPersistencePort() {
        return new UserJpaAdapter(userRepository, userEntityMapper);
    }

    @Bean
    public IUserServicePort userServicePort() {
        return new UserUseCase(userPersistencePort());
    }


    @Bean
    public IRechargePersistencePort rechargePersistencePort() {
        return new RechargeJpaAdapter(rechargeRepository, rechargeEntityMapper);
    }

    @Bean
    public IRechargeServicePort rechargeServicePort() {
        return new RechargeUseCase(rechargePersistencePort(),eventBus);
    }

}