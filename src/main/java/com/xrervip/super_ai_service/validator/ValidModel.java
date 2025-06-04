package com.xrervip.super_ai_service.validator;

import ai.djl.translate.Translator;
import com.xrervip.super_ai_service.common.TranslatorTable;
import com.xrervip.super_ai_service.entity.ModelInfoDTO;
import com.xrervip.super_ai_service.exception.ModelException;
import jakarta.validation.*;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 模型验证器注解
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {ValidModel.ModelValidator.class})
public @interface ValidModel {
    String message() default "Model parameter validation failed, please check the input parameters.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    @Slf4j
    class ModelValidator implements ConstraintValidator<ValidModel, ModelInfoDTO> {
        @Override
        public void initialize(ValidModel constraint) {
            // ...
        }
        @Override
        public boolean isValid(ModelInfoDTO modelInfoDto, ConstraintValidatorContext context) {
            if(modelInfoDto.getModelName().isBlank()){
                return false;
            }
            if(!("PaddlePaddle".equals(modelInfoDto.getModelEngine())|| "OnnxRuntime".equals(modelInfoDto.getModelEngine())|| "PyTorch".equals(modelInfoDto.getModelEngine()))){
                return false;
            }
            try {
                //判断inputClass和outputClass是否都是可以加载的类
                Class<?> inputClass = Class.forName(modelInfoDto.getInputClassName());
                Class<?> outputClass = Class.forName(modelInfoDto.getOutputClassName());
            } catch (ClassNotFoundException e) {
                return false;
            }
            try {
                // 判断是否可以正确地加载Translator
                Translator translator = TranslatorTable.getTranslator(modelInfoDto.getModelTranslator());
            }catch (ModelException modelException){
                return false;
            }
            return true;

        }

    }
}
