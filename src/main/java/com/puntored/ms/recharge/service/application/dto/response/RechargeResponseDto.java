package com.puntored.ms.recharge.service.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RechargeResponseDto {

    private String id;
    private String phoneNumber;
    private long amount;
    private long userId;
    private LocalDateTime createdAt;
}
