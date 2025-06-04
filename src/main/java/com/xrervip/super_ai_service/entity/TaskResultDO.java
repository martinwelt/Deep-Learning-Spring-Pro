 package com.xrervip.super_ai_service.entity;

 import lombok.Data;
 import lombok.NoArgsConstructor;
 import lombok.AllArgsConstructor;
 import java.time.Instant;

 @Data
 @NoArgsConstructor
 @AllArgsConstructor
 public class TaskResultDO {

     private Long resultId; // 对应自增主键
     private String taskId; // 外键
     private String resultData; // 存储 JSON 字符串 (对应 TEXT 或 JSON 类型)
     private String metadata;   // 存储 JSON 字符串 (对应 TEXT 或 JSON 类型)
     private Instant storedAt;  // 数据库中是 TIMESTAMP

     // 构造函数，不包含自增 ID 和自动生成的时间戳
     public TaskResultDO(String taskId, String resultData, String metadata) {
         this.taskId = taskId;
         this.resultData = resultData;
         this.metadata = metadata;
     }
 }