package com.xrervip.super_ai_service.service.impl;

import ai.djl.Device;
import ai.djl.MalformedModelException;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.repository.zoo.ZooModel;
import ai.djl.translate.Translator;
import com.xrervip.super_ai_service.common.ModelProperties;
import com.xrervip.super_ai_service.common.ModelUrlUtils;
import com.xrervip.super_ai_service.common.TranslatorTable;
import com.xrervip.super_ai_service.dao.ModelMapper;
import com.xrervip.super_ai_service.entity.ModelInfoDTO;
import com.xrervip.super_ai_service.entity.ModelDO;
import com.xrervip.super_ai_service.exception.ModelException;
import com.xrervip.super_ai_service.exception.ResourceNotFoundException;
import com.xrervip.super_ai_service.redis.ModelKey;
import com.xrervip.super_ai_service.redis.RedisService;
import com.xrervip.super_ai_service.service.ModelService;
import com.xrervip.super_ai_service.validator.ValidModel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * Description: 模型服务 提供加载模型
 *
 * @Author: frzquantum@gmail.com
 * DateTime: 2023-02-28 19:56
 */
@Service
@Slf4j
@RequiredArgsConstructor
@Validated
public class ModelServiceImpl implements ModelService {
    private final ModelProperties prop;
    private final RedisService redisService;
    private final ModelMapper modelMapper;
    private final Map<String,ZooModel> models = new ConcurrentHashMap<>();

    @Override
    public void addModel(@Valid @ValidModel ModelInfoDTO modelInfoDto, byte[] bytes) {
        ModelDO modelDO = new ModelDO(modelInfoDto.getModelName(), modelInfoDto.getInputClassName(), modelInfoDto.getOutputClassName(), modelInfoDto.getModelEngine(),bytes, modelInfoDto.getModelTranslator());
        modelMapper.insert(modelDO);
    }
    @Override
    public List<String> getAllModelNames() {
        return modelMapper.selectAllModelNames();
    }
//    @Override
//    public void loadModel(String modelName) {
//        Long startTime = System.currentTimeMillis();
//        if(this.checkModel(modelName)) return;
//        ModelDO modelDO = modelMapper.selectByModelName(modelName);
//        if(modelDO ==null) throw new ResourceNotFoundException("Model: "+ modelName+" not exist");
//
//        Device device = Device.Type.CPU.equalsIgnoreCase(prop.getDeviceType()) ? Device.cpu() : Device.gpu();
//
//        try {
//            Class inputClass = Class.forName(modelDO.getInputClassName());
//            Class outputClass = Class.forName(modelDO.getOutputClassName());
//
//            String engine = modelDO.getModelEngine();
//            byte[] modelBlob = modelDO.getModelBlob();
//
//            String path = ModelUrlUtils.saveTmpModel(modelName,modelBlob);
//
//            Translator translator = TranslatorTable.getTranslator(modelDO.getModelTranslator());
//
//            ZooModel model  = Criteria.builder()
//                    .optEngine(engine)
//                    .setTypes(inputClass, outputClass)
//                    .optModelUrls(path)
//                    .optTranslator(translator)
//                    .optDevice(device)
//                    .build()
//                    .loadModel();
//
//            models.put(modelName,model);
//            Long endTime = System.currentTimeMillis();
//
//            log.info("Load model : {} successful in {} ms ",modelName,endTime-startTime);
//        } catch (ClassNotFoundException | ModelNotFoundException | MalformedModelException | IOException e) {
//            throw new ModelException(e);
//        }
//    }
    @Override
    public void loadModel(String modelName) {
        // 使用 computeIfAbsent 保证原子性，只在模型未加载时加载
        models.computeIfAbsent(modelName, name -> {
            long startTime = System.currentTimeMillis();
            ModelDO modelDO = modelMapper.selectByModelName(name);
            if (modelDO == null) {
                throw new ResourceNotFoundException("Model: " + name + " not exist");
            }

            Device device = Device.Type.CPU.equalsIgnoreCase(prop.getDeviceType()) ? Device.cpu() : Device.gpu();

            try {
                Class inputClass = Class.forName(modelDO.getInputClassName());
                Class outputClass = Class.forName(modelDO.getOutputClassName());

                String engine = modelDO.getModelEngine();
                byte[] modelBlob = modelDO.getModelBlob();

                String path = ModelUrlUtils.saveTmpModel(modelName,modelBlob);

                Translator translator = TranslatorTable.getTranslator(modelDO.getModelTranslator());

                ZooModel model  = Criteria.builder()
                        .optEngine(engine)
                        .setTypes(inputClass, outputClass)
                        .optModelUrls(path)
                        .optTranslator(translator)
                        .optDevice(device)
                        .build()
                        .loadModel();

                long endTime = System.currentTimeMillis();
                log.info("Load model: {} successful in {} ms", name, endTime - startTime);
                return model;
            } catch (ClassNotFoundException | ModelNotFoundException | MalformedModelException | IOException e) {
                throw new ModelException(e);
            }
        });
    }


    @Override
    public ZooModel getModel(String modelName) {
        return this.models.get(modelName);
    }

    @Override
    public ZooModel getOrLoadModel(String modelName) {
        if(!checkModel(modelName)){
            loadModel(modelName);
        }
        return getModel(modelName);
    }

    @Override
    public byte[] getModelFile(String modelName) {
        if(redisService.hasKey(ModelKey.modelKey,modelName)){
            log.info("get Model File: {} From Redis .",modelName);
            Object value =redisService.get(ModelKey.modelKey,modelName);
            return ((String)value).getBytes();
        }
        ModelDO modelDO = modelMapper.selectByModelName(modelName);
        if(modelDO ==null) throw new ResourceNotFoundException("Model:"+ modelName+" not exist.");
        byte[] modelBlob = modelDO.getModelBlob();
        redisService.set(ModelKey.modelKey,modelName,modelBlob);
        return modelBlob;
    }

    @Override
    public boolean checkModel(String modelName) {
        return models.containsKey(modelName);
    }
}
