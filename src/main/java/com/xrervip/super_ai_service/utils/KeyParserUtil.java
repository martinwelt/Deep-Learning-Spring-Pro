package com.xrervip.super_ai_service.utils;

import java.util.Arrays;
import java.util.List;

public class KeyParserUtil {

    private static final String DELIMITER = "::";

    /**
     * 拼接多个字段生成统一格式的 key
     */
    public static String buildKey(String... parts) {
        return String.join(DELIMITER, parts);
    }

    /**
     * 拆分 key 为所有字段
     */
    public static List<String> parseKey(String key) {
        return Arrays.asList(key.split(DELIMITER));
    }

    /**
     * 获取 key 中的最后一个字段
     */
    public static String getLastPart(String key) {
        String[] parts = key.split(DELIMITER);
        return parts.length > 0 ? parts[parts.length - 1] : "";
    }

    /**
     * 获取 key 中的第 N 个字段（从 0 开始）
     */
    public static String getPart(String key, int index) {
        String[] parts = key.split(DELIMITER);
        return (index >= 0 && index < parts.length) ? parts[index] : null;
    }

    /**
     * 判断 key 是否符合预期的段数结构
     */
    public static boolean isValidKey(String key, int expectedParts) {
        return key.split(DELIMITER).length == expectedParts;
    }
}
