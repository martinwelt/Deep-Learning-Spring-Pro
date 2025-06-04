package com.xrervip.super_ai_service.common;

import ai.djl.paddlepaddle.zoo.cv.imageclassification.PpWordRotateTranslator;
import ai.djl.paddlepaddle.zoo.cv.objectdetection.PpWordDetectionTranslator;
import ai.djl.paddlepaddle.zoo.cv.wordrecognition.PpWordRecognitionTranslator;
import ai.djl.translate.Translator;
import com.xrervip.super_ai_service.exception.ModelException;
import lombok.experimental.UtilityClass;

import java.util.concurrent.ConcurrentHashMap;

@UtilityClass
public class TranslatorTable {


    public Translator getTranslator(String input){
        return switch (input) {
            case "PpWordDetectionTranslator(ConcurrentHashMap())" -> new PpWordDetectionTranslator(new ConcurrentHashMap());
            case "PpWordRecognitionTranslator" -> new PpWordRecognitionTranslator();
            case "PpWordRotateTranslator" -> new PpWordRotateTranslator();
            default -> throw new ModelException("Translator not exist.");
        };
    }


}
