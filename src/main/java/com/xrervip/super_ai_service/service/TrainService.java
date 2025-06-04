package com.xrervip.super_ai_service.service;

import com.xrervip.super_ai_service.entity.TrainJob;

/**
 * 训练服务 todo
 */
public interface TrainService {
    /**
     * 创建一个训练任务
     * @param modelName
     * @return
     */
    TrainJob create(String modelName);

    void ready(String modelName);

    void train(String modelName);

    void cancel(String modelName);


}
