package com.xrervip.super_ai_service.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KafkaTaskMessage implements Serializable {
    private String taskId;                  // 唯一任务ID
    private String taskType;                // 任务类型，如 "ocr", "detection", "text_gen"
    private byte[] imageInBytes;           // 图像数据（可为空）
    private byte[] textInBytes;              // 文本数据（可为空）
    private Map<String, String> metadata;  // 可扩展的元信息，如语言、用户ID等
    private String status; // Optional: "COMPLETED", "FAILED", etc. from original processing
    private Long timestamp; // Optional: Original result timestamp
}