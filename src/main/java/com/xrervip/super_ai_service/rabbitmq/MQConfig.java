package com.xrervip.super_ai_service.rabbitmq;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;

/**
 * 消息队列配置
 */
@Configuration
@Slf4j
public class MQConfig {
    public static final String topicExchangeName = "gas-exchange";
    public static final String queueName = "queue";

    @Resource
    ConnectionFactory connectionFactory;

    @Bean
    Queue queue() {
        return new Queue(queueName, false);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(topicExchangeName);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("topic.#");
    }

    @Bean
    SimpleMessageListenerContainer container(MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(MQConsumer receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

//    @Bean
//    public RabbitTemplate rabbitTemplate() {
//        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//        rabbitTemplate.setConfirmCallback(confirmCallback());
//        return rabbitTemplate;
//    }

//    @Bean
//    public RabbitTemplate.ConfirmCallback confirmCallback() {
//        return (correlationData, ack, cause) -> {
//            if (ack) {
//                // 消息已经成功发送到RabbitMQ服务器
//                log.info("消息发送成功：" + correlationData);
//            } else {
//                // 消息发送失败
//                log.info("消息发送失败：" + correlationData + "，原因：" + cause);
//            }
//        };
//    }






}
