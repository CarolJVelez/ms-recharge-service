package com.puntored.ms.recharge.service.application.handler.impl;

import com.puntored.ms.recharge.service.application.dto.request.AuthRequestDto;
import com.puntored.ms.recharge.service.application.dto.response.AuthResponseDto;
import com.puntored.ms.recharge.service.application.handler.IAuthHandler;
import com.puntored.ms.recharge.service.domain.api.IUserServicePort;
import com.puntored.ms.recharge.service.domain.exception.IllegalArgumentCustomException;
import com.puntored.ms.recharge.service.domain.model.UserModel;
import com.puntored.ms.recharge.service.domain.utils.ErrorCode;
import com.puntored.ms.recharge.service.infrastructure.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthHandler implements IAuthHandler {

    private final IUserServicePort userServicePort;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponseDto login(AuthRequestDto authRequestDto) {
        UserModel user = userServicePort.findByEmail(authRequestDto.getEmail());
        if (user == null) {
            throw new IllegalArgumentCustomException(ErrorCode.USER_NOT_FOUND);
        }
        if (!passwordEncoder.matches(authRequestDto.getPassword(), user.getPasswordHash())) {
            throw new IllegalArgumentCustomException(ErrorCode.INVALID_CREDENTIALS);
        }

        String token = jwtProvider.generateToken(user.getEmail(), user.getUserId(), user.getName());
        long expiresInSec = jwtProvider.getExpirationSeconds();
        return new AuthResponseDto(token, "Bearer", expiresInSec);
    }

}
