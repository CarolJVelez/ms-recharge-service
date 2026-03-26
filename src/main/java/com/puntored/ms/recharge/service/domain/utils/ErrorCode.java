package com.puntored.ms.recharge.service.domain.utils;

import lombok.Getter;

@Getter
public enum ErrorCode {

    USER_NOT_FOUND("U001", "Usuario no encontrado"),
    INVALID_EMAIL("U003", "Correo electrónico inválido"),
    INVALID_DOCUMENT("U003", "Documento inválido"),
    INVALID_PHONE("U005", "Teléfono inválido"),
    USER_INACTIVE("U005", "Usuario inactivo"),
    UNAUTHORIZED("U008", "No autorizado"),
    PASSWORD_REQUIRED("U010", "La contraseña es obligatoria"),
    EMPTY_USER_DATA("U013", "Datos de usuario vacíos"),
    INVALID_ID("U014", "Identificador inválido"),
    INVALID_CREDENTIALS("U015", "Credenciales inválidas"),
    INVALID_TOKEN("U016", "Token inválido o ausente"),
    BAD_REQUEST("U017", "Error"),
    INTERNAL_ERROR("U999", "Error interno del servidor"),

    INVALID_AMOUNT("R001", "El monto debe estar entre 1,000 y 100,000"),
    INVALID_PHONE_FORMAT("R002", "El número debe iniciar en 3 y tener 10 dígitos"),
    RECHARGE_FAILED("R003", "No se pudo procesar la recarga");


    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
