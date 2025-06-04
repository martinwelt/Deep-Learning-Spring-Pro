package com.xrervip.super_ai_service.redis;

public interface KeyPrefix {
    /**
     * 有效期
     * @return
     */
    public int expireSeconds();

    /**
     * 前缀
     * @return
     */
    public String getPrefix();
}
