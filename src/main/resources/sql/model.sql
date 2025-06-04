DROP TABLE IF EXISTS `model`;
CREATE TABLE `model`  (
    `model_id` int NOT NULL AUTO_INCREMENT COMMENT '模型 id',
    `model_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL UNIQUE COMMENT '模型名称',
    `model_blob` LongBlob NOT NULL COMMENT '模型数据',
    `input_classname` varchar(100) NOT NULL COMMENT '模型输入类名',
    `output_classname` varchar(100) NOT NULL COMMENT '模型输出类名',
    `model_engine` varchar(50) NOT NULL COMMENT '模型引擎',
    `model_translator` varchar(100) NOT NULL COMMENT '模型翻译器',

    PRIMARY KEY (`model_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;
