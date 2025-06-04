-- （存储任务）的基本信息
DROP TABLE IF EXISTS `tasks`;
CREATE TABLE `tasks`  (
    `task_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务ID',
    `task_type` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务类型',
    `status` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态',
    `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `original_timestamp` bigint NULL DEFAULT NULL COMMENT '原始时间戳',
    PRIMARY KEY (`task_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;
-- （存储任务）的具体结果
DROP TABLE IF EXISTS `task_results`;
CREATE TABLE `task_results`  (
    `result_id` bigint NOT NULL AUTO_INCREMENT COMMENT '结果ID',
    `task_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务ID (外键)',
    `result_data` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '结果数据 (TEXT或JSON)',
    `metadata` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '元数据 (TEXT或JSON)',
    `stored_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '存储时间',
    PRIMARY KEY (`result_id`) USING BTREE,
    INDEX `idx_task_results_task_id`(`task_id`) USING BTREE COMMENT '任务ID索引',
    CONSTRAINT `fk_task_results_task_id` FOREIGN KEY (`task_id`) REFERENCES `tasks` (`task_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;