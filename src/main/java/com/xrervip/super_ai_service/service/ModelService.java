package com.xrervip.super_ai_service.service;

import ai.djl.repository.zoo.ZooModel;
import com.xrervip.super_ai_service.entity.ModelInfoDTO;
import com.xrervip.super_ai_service.validator.ValidModel;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: 模型服务，管理模型的添加修改和加载
 *
 * @Author: frzquantum@gmail.com
 * DateTime: 2023-02-25 10:50
 */
@Validated
public interface ModelService {

    /**
     * 向服务器提交模型
     *
     * @param modelInfoDto 模型描述信息dto
     * @param bytes        模型的byte
     */
    void addModel(@Valid @ValidModel ModelInfoDTO modelInfoDto, byte[] bytes);

    /**
     * 查询所有模型名（用于下拉选择）
     * @return 模型名列表
     */
    List<String> getAllModelNames();

    /**
     * 将模型加载到内存中
     *
     * @param modelName name
     */
    void loadModel(String modelName);

    /**
     * 根据模型名称从持久层获取模型，如果模型未加载返回值可能为空
     * @param modelName
     * @return
     */
    ZooModel getModel(String modelName);


    /**
     * 根据模型名称从持久层获取模型，如果模型未加载便加载模型后返回
     * @param modelName name
     * @return model
     */
    ZooModel getOrLoadModel(String modelName);

    /**
     * 获取模型文件
     * @param modelName name
     * @return
     */
    byte[] getModelFile(String modelName);

    /**
     * 判断模型是否加载
     *
     * @param modelName
     * @return
     */
    boolean checkModel(String modelName);

}
