package com.xrervip.super_ai_service.rabbitmq;

import java.io.IOException;

import com.xrervip.super_ai_service.service.ImageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class MQConsumer {

    private final ImageService imageService;

    @RabbitListener(queues=MQConfig.queueName)
    public void receiveMessage(MQMessage message) throws IOException {
        log.info("MQConsumer-- Receive message session:{}",message.taskID);
        imageService.handleOcrJob(message);
    }


}