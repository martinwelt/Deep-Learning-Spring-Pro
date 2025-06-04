package com.xrervip.super_ai_service.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xrervip.super_ai_service.constant.RedisKeyConsts;
import com.xrervip.super_ai_service.kafka.KafkaProducer;
import com.xrervip.super_ai_service.kafka.KafkaTaskMessage;
import com.xrervip.super_ai_service.utils.KeyParserUtil;
import jakarta.annotation.Resource;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class RedisService {
    @Resource
    private RedisTemplate redisTemplate;
    @Getter
    private final KafkaProducer kafkaProducer;

    // 默认令牌桶大小和速率（可根据实际业务调整）
    private final int maxTokens = 10000;
    private final int tokenGenerationRate = 100;  // 每秒生成 1 个令牌

    @Scheduled(fixedRate = 60000) // 每分钟执行一次
    public <T> void scanExpiringRedisData() {
        // 使用 SCAN 命令扫描所有匹配的键
        ScanOptions options = ScanOptions.scanOptions().match("*").count(100).build();
        Cursor<byte[]> cursor = redisTemplate.getConnectionFactory().getConnection().scan(options);

        // 扫描结果
        while (cursor.hasNext()) {
            byte[] foundKey = cursor.next();
            String fullKey = new String(foundKey, StandardCharsets.UTF_8);
            Long ttl = redisTemplate.getExpire(fullKey, TimeUnit.SECONDS);
            if (ttl <= 60) {
                Set<T> result = redisTemplate.opsForSet().members(fullKey);

                if (result != null && !result.isEmpty()) {
                    KafkaTaskMessage message = convertToMessage(fullKey, result);
                    kafkaProducer.backupToKafka(message);
                    log.info("Backed up key [{}] to Kafka", fullKey);
                }
            }
        }
    }

    private <T> KafkaTaskMessage convertToMessage(String key, Set<T> resultSet) {
        // 期望格式：taskType::taskId
        if (!KeyParserUtil.isValidKey(key, RedisKeyConsts.FIELD_COUNT)) {
            throw new IllegalArgumentException(
                    "Invalid Redis key format: expected format 'dataType::taskID::fileName::taskType', but got: " + key);
        }

        String taskType = KeyParserUtil.getPart(key, RedisKeyConsts.INDEX_TASK_TYPE);
        KafkaTaskMessage message = new KafkaTaskMessage();
        message.setTaskId(key);
        message.setTaskType(taskType);


        // 根据需要设置 imageInBytes 或 textInBytes
        message.setMetadata(Map.of("redisKey", key, "source", "redis_expiring"));
        message.setTimestamp(System.currentTimeMillis()); // 或者结果在 Redis 中存储时的时间戳
        message.setStatus("COMPLETED_IN_REDIS"); // 标记为在 Redis 中已完成

        try {
            byte[] textData = new ObjectMapper().writeValueAsBytes(resultSet);
            message.setTextInBytes(textData);
        } catch (Exception e) {
            log.error("Failed to serialize resultSet for key: " + key, e);
            // 即使序列化失败，也应该发送一个包含 taskId 和错误信息的 message，或者不发送
            message.setStatus("SERIALIZATION_FAILED_FOR_PERSISTENCE");
        }

        return message;
    }

    public RedisService(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    // 设置 Redis 缓存键值对
    public <T> void set(KeyPrefix prefix, String key, T value) {
        String realKey = prefix.getPrefix() + "::" + key;
        redisTemplate.opsForValue().set(realKey, value, prefix.expireSeconds(), TimeUnit.SECONDS);
    }

    // 获取 Redis 缓存键值对
    public <T> T get(KeyPrefix prefix, String key) {
        String realKey = prefix.getPrefix() + "::" + key;
        return (T) redisTemplate.opsForValue().get(realKey);
    }

    // 删除缓存
    public void delete(KeyPrefix prefix, String key) {
        String realKey = prefix.getPrefix() + "::" + key;
        redisTemplate.delete(realKey);
    }

    // 设置过期时间
    public boolean expire(KeyPrefix prefix, String key, long time) {
        String realKey = prefix.getPrefix() + "::" + key;
        return redisTemplate.expire(realKey, time, TimeUnit.SECONDS);
    }

    // 获取缓存的过期时间
    public Long getExpire(KeyPrefix prefix, String key) {
        String realKey = prefix.getPrefix() + "::" + key;
        return redisTemplate.getExpire(realKey, TimeUnit.SECONDS);
    }

    // 判断缓存中是否存在某个键
    public boolean hasKey(KeyPrefix prefix, String key) {
        String realKey = prefix.getPrefix() + "::" + key;
        return redisTemplate.hasKey(realKey);
    }

    // 增加 Redis 键的值
    public Long increment(KeyPrefix prefix, String key, long delta) {
        String realKey = prefix.getPrefix() + "::" + key;
        return redisTemplate.opsForValue().increment(realKey, delta);
    }

    // 减少 Redis 键的值
    public Long decrement(KeyPrefix prefix, String key, long delta) {
        String realKey = prefix.getPrefix() + "::" + key;
        return redisTemplate.opsForValue().increment(realKey, -delta);
    }

    // 向 Set 添加元素
    public <T> void addSet(KeyPrefix prefix, String key, String type, T value) {
        String realKey = prefix.getPrefix() + "::" + key + "::" + type;
        redisTemplate.opsForSet().add(realKey, value);
        redisTemplate.expire(realKey, prefix.expireSeconds(), TimeUnit.SECONDS);
    }

    // 获取 Set 中的所有元素
    public <T> Map<String, Set<T>> getMapSet(KeyPrefix prefix, String key) {
        String realKey = prefix.getPrefix() + "::" + key;

        // 用于存储匹配的图像名称和 OCR 结果
        Map<String, Set<T>> resultMap = new HashMap<>();

        // 使用 SCAN 命令扫描所有匹配的键
        ScanOptions options = ScanOptions.scanOptions().match(realKey + "*").count(100).build();
        Cursor<byte[]> cursor = redisTemplate.getConnectionFactory().getConnection().scan(options);

        // 扫描结果
        while (cursor.hasNext()) {
            byte[] foundKey = cursor.next();
            String fullKey = new String(foundKey, StandardCharsets.UTF_8);

            // 去掉 realKey 后缀部分，得到图像名称
            String imageName = fullKey.replace(realKey + "::", "");

            // 通过扫描到的每个key获取对应的集合
            Set<T> resultSet = redisTemplate.opsForSet().members(fullKey);

            if (resultSet != null && !resultSet.isEmpty()) {
                resultMap.put(imageName, resultSet); // 将结果加入 Map 中
            }
        }

        // 关闭游标
        cursor.close();

        return resultMap;

//        return redisTemplate.opsForSet().members(realKey);
    }

//    public String generateDownloadLinkForOcrResults(KeyPrefix prefix, String key) throws IOException {
//        String realKey = prefix.getPrefix() + "::" + key;
//
//        // 创建 CSV 文件
//        String tempFileName = "ocr_results_" + key + ".csv";
//        Path tempFile = Files.createTempFile(tempFileName, ".csv");
//
//        try (BufferedWriter writer = Files.newBufferedWriter(tempFile, StandardCharsets.UTF_8)) {
//            // 写入 CSV 文件的表头
//            writer.write("ImageName, OCRResult\n");
//
//            // 使用 SCAN 命令扫描所有匹配的键
//            ScanOptions options = ScanOptions.scanOptions().match(realKey + "*").count(100).build();
//            Cursor<byte[]> cursor = redisTemplate.executeWithStickyConnection(connection -> connection.scan(options));
//
//            // 扫描结果
//            while (cursor.hasNext()) {
//                byte[] foundKey = cursor.next();
//                String fullKey = new String(foundKey, StandardCharsets.UTF_8);
//
//                // 去掉 realKey 后缀部分，得到图像名称
//                String imageName = fullKey.replace(realKey + "::", "");
//
//                // 获取 OCR 结果集合
//                Set<String> ocrResultSet = redisTemplate.opsForSet().members(fullKey);
//
//                if (ocrResultSet != null && !ocrResultSet.isEmpty()) {
//                    // 将结果写入 CSV 文件
//                    for (String result : ocrResultSet) {
//                        writer.write(String.format("%s, %s\n", imageName, result));
//                    }
//                }
//            }
//        }
//
//        // 生成下载链接（例如，你可以将文件上传到某个服务器或云存储，并生成一个公共链接）
//        String downloadUrl = "/download/ocr_results/" + tempFile.getFileName().toString(); // 示例 URL
//        return downloadUrl;
//    }

    // 从 Set 中删除元素
    public <T> void deleteSet(KeyPrefix prefix, String key, T value) {
        String realKey = prefix.getPrefix() + "::" + key;
        redisTemplate.opsForSet().remove(realKey, value);
    }

//    // 令牌桶限流
//    public boolean tryAcquire(String taskID) {
//        // Redis 中每个 taskID 使用一个独立的令牌桶
//        String key = "rate_limit:" + taskID;
//
//        // 获取当前时间戳
//        long currentTime = System.currentTimeMillis();
//
//        // 获取当前的令牌数
//        Object tokensObject = redisTemplate.opsForValue().get(key);
//        Long tokens = tokensObject != null ? Long.parseLong(tokensObject.toString()) : 0L;
//
//        if (tokens < maxTokens) {
//            // 如果令牌不足，则尝试增加令牌（速率限制：每秒生成一定数量的令牌）
//            redisTemplate.opsForValue().increment(key, tokenGenerationRate);
//            return true;
//        }
//
//        // 如果令牌桶满了，拒绝请求
//        return false;
//    }

}
