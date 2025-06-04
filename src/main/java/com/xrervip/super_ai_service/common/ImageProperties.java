package com.xrervip.super_ai_service.common;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * OCR配置
 *
 * @author 别动我
 * @since 2022/3/16 22:07
 */
@Data
@ConfigurationProperties(prefix = "image.ocr")
@Configuration
public class ImageProperties {

    /**
     * 旋转判断阈值，default=0.8
     */
    private double rotateThreshold = 0.8;




}

