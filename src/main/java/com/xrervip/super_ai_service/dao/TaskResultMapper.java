package com.xrervip.super_ai_service.dao;

import com.xrervip.super_ai_service.entity.TaskResultDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
// import java.util.Optional;
// import java.util.List;

@Mapper
public interface TaskResultMapper {

    /**
     * 插入一条新的任务结果记录
     * @param taskResultDO 任务结果对象
     * @return 影响的行数
     */
    int insert(TaskResultDO taskResultDO);

    /**
     * 根据任务 ID 查询其结果
     * 注意：一个任务 ID 可能理论上对应多个结果记录（如果设计如此），这里假设查找最新的或特定的一个
     * 如果严格一对一，可以返回 Optional<TaskResultDO>
     * 如果可能多个，返回 List<TaskResultDO>
     * 这里我们假设基于之前的设计，一个 TaskEntity 对应一个 TaskResultEntity (或最新一个)
     * @param taskId 任务 ID
     * @return 任务结果对象列表 (通常应该只有一个，或按时间排序取最新的)
     */
    // Optional<TaskResultDO> findByTaskId(@Param("taskId") String taskId); // 如果确定只有一个
    // List<TaskResultDO> findByTaskId(@Param("taskId") String taskId); // 如果可能有多个，按需取用
}