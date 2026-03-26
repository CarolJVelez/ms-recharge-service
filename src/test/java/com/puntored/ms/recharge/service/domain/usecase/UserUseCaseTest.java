package com.puntored.ms.recharge.service.domain.usecase;

import com.puntored.ms.recharge.service.domain.exception.IllegalArgumentCustomException;
import com.puntored.ms.recharge.service.domain.exception.NoDataFoundException;
import com.puntored.ms.recharge.service.domain.model.UserModel;
import com.puntored.ms.recharge.service.domain.spi.IUserPersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserUseCaseTest {

    @Mock
    private IUserPersistencePort userPersistencePort;

    @InjectMocks
    private UserUseCase userUseCase;

    private UserModel buildValidUser() {
        UserModel u = new UserModel();
        u.setName("Carol");
        u.setEmail("carol@gmail.com");
        return u;
    }

    @Test
    void shouldThrowExceptionWhenEmailIsNull() {
        assertThrows(IllegalArgumentCustomException.class, () -> userUseCase.findByEmail(null));
    }

    @Test
    void shouldThrowExceptionWhenEmailIsBlank() {
        assertThrows(IllegalArgumentCustomException.class, () -> userUseCase.findByEmail(" "));
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        when(userPersistencePort.findByEmail("carol@gmail.com")).thenReturn(null);

        assertThrows(NoDataFoundException.class, () -> userUseCase.findByEmail("carol@gmail.com"));
    }

    @Test
    void shouldReturnUserWhenEmailIsValid() {
        UserModel user = buildValidUser();
        when(userPersistencePort.findByEmail("carol@gmail.com")).thenReturn(user);

        UserModel result = userUseCase.findByEmail("carol@gmail.com");

        assertNotNull(result);
        assertEquals("carol@gmail.com", result.getEmail());
        verify(userPersistencePort).findByEmail("carol@gmail.com");
    }
}
