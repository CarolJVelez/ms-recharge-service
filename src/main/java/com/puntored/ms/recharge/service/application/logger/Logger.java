package com.puntored.ms.recharge.service.application.logger;

import com.puntored.ms.recharge.service.domain.spi.LoggerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Logger implements LoggerRepository {

    @Override
    public void info(String message, Object... args) {
        log.info(message, args);
    }

    @Override
    public void warn(String message, Object... args) {
        log.warn(message, args);
    }

    @Override
    public void error(String message, Object... args) {
        log.error(message, args);
    }
}
