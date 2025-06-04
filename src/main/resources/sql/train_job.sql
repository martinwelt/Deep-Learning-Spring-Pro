DROP TABLE IF EXISTS `train_job`;
CREATE TABLE `train_job`  (
                          `job_id` int NOT NULL AUTO_INCREMENT COMMENT '训练 id',
                          `model_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL UNIQUE COMMENT '训练模型名称',
                          `status` varchar(100) NOT NULL COMMENT '训练任务状态',

                          PRIMARY KEY (`job_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;
