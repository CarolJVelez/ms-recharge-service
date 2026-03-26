package com.puntored.ms.recharge.service.infrastructure.out.event.adapter;

import com.puntored.ms.recharge.service.domain.spi.EventBus;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaEventBus implements EventBus {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${app.kafka.recharge-topic}")
    private String rechargeTopic;

    @Override
    public void publish(Object event) {
        kafkaTemplate.send(rechargeTopic, event);
    }
}