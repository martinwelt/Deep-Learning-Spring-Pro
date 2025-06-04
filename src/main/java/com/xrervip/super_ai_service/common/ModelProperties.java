package com.xrervip.super_ai_service.common;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Author: frzquantum@gmail.com
 * DateTime: 2023-03-02 21:18
 */
@Data
@ConfigurationProperties(prefix = "model")
@Configuration
public class ModelProperties {
    private String deviceType = "gpu";

}
