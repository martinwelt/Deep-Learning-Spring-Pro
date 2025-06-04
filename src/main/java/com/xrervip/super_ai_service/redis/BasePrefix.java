package com.xrervip.super_ai_service.redis;

import com.xrervip.super_ai_service.common.Constants;
import org.apache.tomcat.util.bcel.Const;

public abstract class BasePrefix implements KeyPrefix{
    private int expireSeconds;

    private String prefix;

    public BasePrefix(String prefix){
        this(0, prefix);//默认0代表永不过期
    }

    public BasePrefix(int expireSeconds, String prefix){
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

    @Override
    public int expireSeconds() {
        return expireSeconds;
    }

    @Override
    public String getPrefix() {
        String className = getClass().getSimpleName();//拿到参数类类名
        StringBuilder sb = new StringBuilder();
        sb.append(Constants.REDIS_KEY_PREFIX).append("::").append(className).append(":").append(prefix);
        return sb.toString();
    }
}
