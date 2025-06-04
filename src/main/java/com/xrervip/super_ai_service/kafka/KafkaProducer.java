package com.xrervip.super_ai_service.kafka;


import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, KafkaTaskMessage> kafkaTemplate;

    public void backupToKafka(KafkaTaskMessage message) {
        kafkaTemplate.send("redis-backup-topic", message.getTaskId(), message);
    }

}
