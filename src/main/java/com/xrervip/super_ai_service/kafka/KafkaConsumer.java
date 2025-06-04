package com.xrervip.super_ai_service.kafka;

import com.xrervip.super_ai_service.kafka.KafkaTaskMessage;
import com.xrervip.super_ai_service.service.TaskHandlerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaConsumer {

    private final TaskHandlerService handlerService;

    @KafkaListener(topics = "${kafka.topic.tasks}", groupId = "${kafka.group.id}")
    public void listen(ConsumerRecord<String, KafkaTaskMessage> record) {
        KafkaTaskMessage message = record.value();
        log.info("Received task: {}", message.getTaskId());
        handlerService.handleTask(message);
    }
}