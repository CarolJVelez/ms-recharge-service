package com.puntored.ms.recharge.service.domain.spi;

public interface LoggerRepository {
    void info(String message, Object... args);
    void warn(String message, Object... args);
    void error(String message, Object... args);
}
