package com.xrervip.super_ai_service.redis;


import com.xrervip.super_ai_service.common.Constants;

public class ImageKey extends BasePrefix{
    private ImageKey(int expire, String prefix){
        super(expire,prefix);
    }
    public static ImageKey imageKey = new ImageKey(Constants.IMAGE_EXPIRE,"Image: ");
}
