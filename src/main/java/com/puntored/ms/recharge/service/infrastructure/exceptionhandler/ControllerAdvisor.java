package com.puntored.ms.recharge.service.infrastructure.exceptionhandler;

import com.puntored.ms.recharge.service.domain.exception.BadRequestException;

import com.puntored.ms.recharge.service.domain.exception.CustomException;
import com.puntored.ms.recharge.service.domain.exception.IllegalArgumentCustomException;
import com.puntored.ms.recharge.service.domain.exception.ForbiddenException;
import com.puntored.ms.recharge.service.domain.exception.NoDataFoundException;
import com.puntored.ms.recharge.service.domain.utils.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor {

    private final Logger logger = LoggerFactory.getLogger(ControllerAdvisor.class);

   @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        // Tomamos el primer error de la lista
        String field = ex.getBindingResult().getFieldError().getField();

        // Mapeamos el campo a tu ErrorCode
        ErrorCode errorCode = mapFieldToErrorCode(field);

        Map<String, Object> body = Map.of(
                "code", errorCode.getCode(),
                "message", errorCode.getMessage()
        );

        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(IllegalArgumentCustomException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentCustomException(IllegalArgumentCustomException ex) {
        String msg = ex.getMessage() == null ? "Solicitud inválida" : ex.getMessage();
        logger.warn("Bad Request: {}", msg);
        logger.warn("IllegalArgumentCustomException handled: {}", msg);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("code", ex.getCode(),
                        "message", ex.getMessage()));
    }

    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNoDataFoundException(NoDataFoundException ex) {
        String msg = ex.getMessage() == null ? "Recurso no encontrado" : ex.getMessage();
        logger.warn("No encontrado: {}", msg);
        logger.warn("NoDataFound: {}", msg);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("code", ex.getCode(),
                        "message", ex.getMessage()));
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Map<String, Object>> handleThrowable(Throwable ex) {
        logger.error("Error interno: {}", ex.getMessage());
        logger.error("Unhandled exception: ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("code", ErrorCode.INTERNAL_ERROR.getCode(),
                        "message", ErrorCode.INTERNAL_ERROR.getMessage()));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Map<String,Object>> handleBadRequest(BadRequestException ex) {
        String msg = ex.getMessage() == null ? "Solicitud inválida" : ex.getMessage();
        logger.warn("BadRequest: {}", msg);
        logger.warn("BadRequestException handled: {}", msg);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("code", ex.getCode(),
                        "message", ex.getMessage()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleAccessDenied(AccessDeniedException ex) {
         logger.warn("Access denied: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(Map.of(
                        "code", ErrorCode.UNAUTHORIZED.getCode(),
                        "message", ErrorCode.UNAUTHORIZED.getMessage()
                ));
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<Map<String, Object>> handleForbiddenException(ForbiddenException ex) {
        logger.warn("Access denied (Forbidden): {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(Map.of(
                        "code", ErrorCode.UNAUTHORIZED.getCode(),
                        "message",  ErrorCode.UNAUTHORIZED.getMessage()
                ));
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Map<String, Object>> handleCustomException(CustomException ex) {
        logger.warn("Error: {} - {}", ex.getCode(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        //"status", HttpStatus.BAD_REQUEST.value(),
                        "code", ex.getCode(),
                        "message", ex.getMessage()
                ));
    }

    // Mapeo de campos del DTO a tus códigos
    private ErrorCode mapFieldToErrorCode(String field) {
        switch (field) {
            case "name":
            case "lastName":
                return ErrorCode.EMPTY_USER_DATA;
            case "phone":
                return ErrorCode.INVALID_PHONE;
            case "email":
                return ErrorCode.INVALID_EMAIL;
            case "passwordHash":
            case "password":
                return ErrorCode.PASSWORD_REQUIRED;

                default:
                return ErrorCode.BAD_REQUEST;
        }
    }


}