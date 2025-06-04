package com.xrervip.super_ai_service.constant;

public class RedisKeyConsts {
    // Redis Key 分隔符
    public static final String DELIMITER = "::";

    // 4 个字段顺序定义
    public static final int FIELD_COUNT = 4;

    // 字段索引定义，便于解析时使用
    public static final int INDEX_DATA_TYPE = 0;
    public static final int INDEX_TASK_ID   = 1;
    public static final int INDEX_FILE_NAME = 2;
    public static final int INDEX_TASK_TYPE  = 3;

    // 示例说明
    public static final String KEY_FORMAT_EXAMPLE = "ocr::user123::session456::task789";

    private RedisKeyConsts() {
        // 工具类禁止实例化
    }
}

