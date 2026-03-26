package com.puntored.ms.recharge.service.domain.spi;

public interface EventBus {
    void publish(Object event);
}