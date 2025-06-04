package com.xrervip.super_ai_service.rabbitmq;

import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MQProducer {
    private final RabbitTemplate rabbitTemplate;

    public void sendTopic(Object message){
        rabbitTemplate.convertAndSend(MQConfig.topicExchangeName, "topic.#", message);
//        rabbitTemplate.waitForConfirmsOrDie(5000);
    }

}
