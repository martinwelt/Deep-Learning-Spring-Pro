package com.xrervip.super_ai_service.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Author: frzquantum@gmail.com
 * DateTime: 2023-03-03 20:38
 */
@Data
@Schema
public class ModelInfoDTO {

    @NotNull
    private String modelName;

    @NotNull
    private String inputClassName;

    @NotNull
    private String outputClassName;

    @NotNull
    private String modelEngine;

    @NotNull
    private String modelTranslator;


}
