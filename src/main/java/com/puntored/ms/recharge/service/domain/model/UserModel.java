package com.puntored.ms.recharge.service.domain.model;

import com.puntored.ms.recharge.service.domain.exception.BadRequestException;
import com.puntored.ms.recharge.service.domain.utils.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.regex.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {

    private Long userId;
    private String name;
    private String lastName;
    private String document;
    private String phone;
    private LocalDate birthDate;
    private String email;
    private String passwordHash;
    private boolean active = true;

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    private static final Pattern PHONE_PATTERN =
            Pattern.compile("^3\\d{9}$");

    public void validate() {

        if (this == null) {
            throw new BadRequestException(ErrorCode.EMPTY_USER_DATA);
        }

        validateDocument();
        validatePhone();
        validateEmail();
        validatePassword();
        validateActiveUser();
    }

    private void validateDocument() {
        if (isBlank(document) || !document.matches("^\\d+$")) {
            throw new BadRequestException(ErrorCode.INVALID_DOCUMENT);
        }
    }

    private void validatePhone() {
        if (isBlank(phone) || !PHONE_PATTERN.matcher(phone).matches()) {
            throw new BadRequestException(ErrorCode.INVALID_PHONE);
        }
    }

    private void validateEmail() {
        if (isBlank(email) || !EMAIL_PATTERN.matcher(email).matches()) {
            throw new BadRequestException(ErrorCode.INVALID_EMAIL);
        }
    }

    private void validatePassword() {
        if (isBlank(passwordHash)) {
            throw new BadRequestException(ErrorCode.PASSWORD_REQUIRED);
        }
    }

    private void validateActiveUser() {
        if (!active) {
            throw new BadRequestException(ErrorCode.USER_INACTIVE);
        }
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
