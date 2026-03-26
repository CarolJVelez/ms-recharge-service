package com.puntored.ms.recharge.service.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequestDto {

    @Email(message = "El correo electronico es inválido")
    @NotBlank(message = "El correo electronico es obligatorio")
    private String email;
    @NotBlank(message = "La Contraseña es obligatoria")
    @Size(min = 6, max = 100)
    private String password;
}
