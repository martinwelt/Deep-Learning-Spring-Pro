package com.xrervip.super_ai_service.service;

import com.xrervip.super_ai_service.entity.ResultSetDTO;
import com.xrervip.super_ai_service.rabbitmq.MQMessage;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: 图片服务
 *
 * @Author: frzquantum@gmail.com
 * DateTime: 2023-02-28 16:17
 */
public interface ImageService {


    /**
     * 生产者向 mq里提交OCR任务
     *
     * @param taskID           taskID
     * @param originalFilename
     * @param inputStream      image 输入流
     * @throws IOException IOException
     */
    void sendOcrJob(String taskID, String taskType, String originalFilename, InputStream inputStream) throws IOException;

    void sendOcrJobFromZip(String taskID, String taskType, InputStream zipInputStream) throws IOException;

    /**
     * 消费者从mq里获取OCR任务
     * @param MQMessage ocr任务
     * @throws IOException
     */
    void handleOcrJob(MQMessage MQMessage) throws IOException;

    /**
     * 根据taskID获取ocr 结果
     * @param taskID taskID
     * @return dto
     */
    public List<ResultSetDTO> getOcrResult(String taskID);
}
