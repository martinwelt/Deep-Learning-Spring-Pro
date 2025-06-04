package com.xrervip.super_ai_service.redis;


import com.xrervip.super_ai_service.common.Constants;

public class ModelKey extends BasePrefix{
    private ModelKey(int expire,String prefix){
        super(expire,prefix);
    }
    public static ModelKey modelKey = new ModelKey(Constants.MODEL_EXPIRE,"Model: ");
}
