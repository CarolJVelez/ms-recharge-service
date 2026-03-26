package com.puntored.ms.recharge.service.domain.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RechargeSucceededEvent {

    private String rechargeId;
    private Long userId;
    private Long amount;
    private LocalDateTime occurredAt;

}