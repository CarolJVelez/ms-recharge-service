package com.puntored.ms.recharge.service.application.handler;

import com.puntored.ms.recharge.service.application.dto.request.AuthRequestDto;
import com.puntored.ms.recharge.service.application.dto.response.AuthResponseDto;
import com.puntored.ms.recharge.service.application.handler.impl.AuthHandler;
import com.puntored.ms.recharge.service.domain.api.IUserServicePort;
import com.puntored.ms.recharge.service.domain.exception.IllegalArgumentCustomException;
import com.puntored.ms.recharge.service.domain.model.UserModel;
import com.puntored.ms.recharge.service.infrastructure.security.jwt.JwtProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthHandlerTest {

    @Mock
    private IUserServicePort userServicePort;

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthHandler authHandler;

    @Test
    void shouldLoginSuccessfully() {
        AuthRequestDto request = AuthRequestDto.builder()
                .email("caroljvelez@gmail.com")
                .password("Caroljvelez1234")
                .build();

        UserModel user = new UserModel();
        user.setUserId(2L);
        user.setName("Carol");
        user.setEmail("caroljvelez@gmail.com");
        user.setPasswordHash("encoded-password");

        when(userServicePort.findByEmail(request.getEmail())).thenReturn(user);
        when(passwordEncoder.matches(request.getPassword(), user.getPasswordHash())).thenReturn(true);
        when(jwtProvider.generateToken(user.getEmail(), user.getUserId(), user.getName())).thenReturn("fake-jwt");
        when(jwtProvider.getExpirationSeconds()).thenReturn(3600L);

        AuthResponseDto response = authHandler.login(request);

        assertThat(response.getAccessToken()).isEqualTo("fake-jwt");
        assertThat(response.getAccessTokenType()).isEqualTo("Bearer");
        assertThat(response.getExpiresIn()).isEqualTo(3600L);
    }

    @Test
    void shouldFailWhenPasswordIsInvalid() {
        AuthRequestDto request = AuthRequestDto.builder()
                .email("caroljvelez@gmail.com")
                .password("wrong-password")
                .build();

        UserModel user = new UserModel();
        user.setUserId(2L);
        user.setName("Carol");
        user.setEmail("caroljvelez@gmail.com");
        user.setPasswordHash("encoded-password");

        when(userServicePort.findByEmail(request.getEmail())).thenReturn(user);
        when(passwordEncoder.matches(request.getPassword(), user.getPasswordHash())).thenReturn(false);

        assertThrows(IllegalArgumentCustomException.class, () -> authHandler.login(request));
    }
}
