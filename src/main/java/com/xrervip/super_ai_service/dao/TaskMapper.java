package com.xrervip.super_ai_service.dao;

import com.xrervip.super_ai_service.entity.TaskDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param; // 引入 Param
import java.util.Optional;

@Mapper // 标记为 MyBatis Mapper 接口
public interface TaskMapper {

    /**
     * 插入一条新的任务记录
     * @param taskDO 任务对象
     * @return 影响的行数
     */
    int insert(TaskDO taskDO);

    /**
     * 根据任务 ID 查询任务
     * @param taskId 任务 ID
     * @return 任务对象，如果不存在则为 Optional.empty()
     */
    Optional<TaskDO> findById(@Param("taskId") String taskId); // 使用 @Param 注解

    /**
     * 更新任务信息 (通常用于更新状态等)
     * @param taskDO 包含更新信息的任务对象 (taskId 必须存在)
     * @return 影响的行数
     */
    int update(TaskDO taskDO);

    // 如果需要，可以添加其他查询方法，例如：
    // List<TaskDO> findByStatus(@Param("status") String status);
}