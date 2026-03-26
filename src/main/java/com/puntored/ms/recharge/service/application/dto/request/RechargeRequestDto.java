package com.puntored.ms.recharge.service.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RechargeRequestDto {

    @NotBlank(message = "El número de teléfono es obligatorio")
    private String phoneNumber;

    @NotNull(message = "El monto es obligatorio")
    private Long amount;
}
