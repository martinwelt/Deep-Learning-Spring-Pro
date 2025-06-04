package com.xrervip.super_ai_service.rabbitmq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MQMessage implements Serializable {
    public String taskID;
    public String taskType;                // 任务类型，如 "ocr", "detection", "text_gen"
    public byte[] imageInBytes;           // 图像数据（可为空）
    public byte[] textInBytes;              // 文本数据（可为空）
    public Map<String, String> metadata;  // 可扩展的元信息，如语言、用户ID等
}
