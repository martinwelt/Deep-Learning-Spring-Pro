package com.xrervip.super_ai_service.common;

import lombok.experimental.UtilityClass;

/**
 * Created with IntelliJ IDEA.
 * Description: 常量
 *
 * @Author: frzquantum@gmail.com
 * DateTime: 2023-03-02 21:21
 */
@UtilityClass
public class Constants {
    /**
     * OCR推理引擎使用百度飞浆
     */
    public static final String ENGINE_PADDLE = "PaddlePaddle";
    public static final String ENGINE_ONNX = "OnnxRuntime";
    public static final String ENGINE_PYTORCH = "PyTorch";


    /**
     * Redis键的 前缀
     */
    public static final String REDIS_KEY_PREFIX = "general_ai_service";

    /**
     * redis中模型超时时间
     */
    public static final int MODEL_EXPIRE = 3600*24 *2;

    /**
     * redis中图片文件超时时间
     */
    public static final int IMAGE_EXPIRE = 1800;


}