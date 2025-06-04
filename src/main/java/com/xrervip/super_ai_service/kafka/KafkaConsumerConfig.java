package com.xrervip.super_ai_service.kafka;

import com.xrervip.super_ai_service.kafka.KafkaTaskMessage;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer; // Spring Kafka 提供的 JSON 反序列化器

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id}") // 从 application.properties 读取
    private String groupId;

    @Bean
    public ConsumerFactory<String, KafkaTaskMessage> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId); // 消费者组ID
        // props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); // 或 "latest"

        // 使用 Spring Kafka 提供的 JsonDeserializer
        JsonDeserializer<KafkaTaskMessage> deserializer = new JsonDeserializer<>(KafkaTaskMessage.class);
        deserializer.setRemoveTypeHeaders(false); // 或者 true，取决于生产者是否发送类型头
        deserializer.addTrustedPackages("*"); // 信任所有包，或者指定你的 KafkaTaskMessage 所在的包
        deserializer.setUseTypeMapperForKey(false);

        return new DefaultKafkaConsumerFactory<>(
                props,
                new StringDeserializer(), // Key deserializer
                deserializer            // Value deserializer
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, KafkaTaskMessage> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, KafkaTaskMessage> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        // 在这里可以配置其他属性，例如并发数 factory.setConcurrency(3);
        // 错误处理等
        // factory.setErrorHandler(...)
        // factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.RECORD); // 根据需要设置 AckMode
        return factory;
    }
}