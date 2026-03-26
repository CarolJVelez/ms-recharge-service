package com.puntored.ms.recharge.service.domain.model;

import com.puntored.ms.recharge.service.domain.exception.BadRequestException;
import com.puntored.ms.recharge.service.domain.utils.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.regex.Pattern;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RechargeModel {

    private String id;
    private String phoneNumber;
    private long amount;
    private long userId;
    private LocalDateTime createdAt;

    private static final Pattern PHONE_PATTERN =
            Pattern.compile("^3\\d{9}$");

    public void validate() {

        validatePhone();
        validateAmount();
    }

    private void validatePhone() {
        if (isBlank(phoneNumber) || !PHONE_PATTERN.matcher(phoneNumber).matches()) {
            throw new BadRequestException(ErrorCode.INVALID_PHONE);
        }
    }

    private void validateAmount() {
        if (amount < 1000 || amount > 100000) {
            throw new BadRequestException(ErrorCode.INVALID_AMOUNT);
        }
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
