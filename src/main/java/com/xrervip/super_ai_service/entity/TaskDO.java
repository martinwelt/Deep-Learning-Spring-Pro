package com.xrervip.super_ai_service.entity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor; // 可选，方便创建对象
import java.time.Instant; // 或者 java.util.Date / LocalDateTime

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDO {

    private String taskId;
    private String taskType;
    private String status;
    private Instant createdAt; // 数据库中是 TIMESTAMP
    private Instant updatedAt; // 数据库中是 TIMESTAMP
    private Long originalTimestamp;

    // 如果需要，可以添加一个有参构造函数，不包含自动生成的时间戳
    public TaskDO(String taskId, String taskType, String status, Long originalTimestamp) {
        this.taskId = taskId;
        this.taskType = taskType;
        this.status = status;
        this.originalTimestamp = originalTimestamp;
    }
}