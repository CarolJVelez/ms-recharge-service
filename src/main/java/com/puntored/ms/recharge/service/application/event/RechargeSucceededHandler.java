package com.puntored.ms.recharge.service.application.event;

import com.puntored.ms.recharge.service.domain.model.RechargeSucceededEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RechargeSucceededHandler {

    @KafkaListener(
            topics = "${app.kafka.recharge-topic}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void handle(RechargeSucceededEvent event) {
        log.info("Evento Kafka recibido: recarga exitosa -> {}", event.getRechargeId());
    }
}