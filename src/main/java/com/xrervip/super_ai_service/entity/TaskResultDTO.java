//package com.xrervip.super_ai_service.entity;
//
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.experimental.Accessors;
//
//import java.io.Serializable;
//import java.util.HashMap;
//import java.util.Map;
//
//@Data
//@Accessors(chain = true)
//@NoArgsConstructor
//public class TaskResultDTO implements Serializable {
//
//    private String taskType;        // 如 "ocr", "detection", "text_gen"
//    private String label;           // 类别、文字、标题等
//    private Double probability;     // 置信度（可为空）
//
//    private Double x;               // 检测框 X
//    private Double y;               // 检测框 Y
//    private Double width;           // 检测框宽
//    private Double height;          // 检测框高
//
//    private String text;            // 文本结果（如 OCR 内容、生成的文本）
//
//    private Map<String, Object> data; // 扩展字段
//
//    public Map<String, Object> getData() {
//        if (data == null) {
//            data = new HashMap<>();
//        }
//        return data;
//    }
//}
