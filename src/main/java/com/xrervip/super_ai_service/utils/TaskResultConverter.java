//package com.xrervip.super_ai_service.utils;
//
//import com.xrervip.super_ai_service.entity.DetectObjectDTO;
//import com.xrervip.super_ai_service.entity.TaskResultDTO;
//
//import java.util.*;
//
//public class TaskResultConverter {
//
//    // 将 DetectObjectDTO 列表转换为 TaskResultDTO
//    public static TaskResultDTO fromDetectObjects(String taskId, List<DetectObjectDTO> detectList) {
//        List<Map<String, Object>> dataList = new ArrayList<>();
//        for (DetectObjectDTO dto : detectList) {
//            Map<String, Object> data = new HashMap<>();
//            data.put("className", dto.getClassName());
//            data.put("probability", dto.getProbability());
//            data.put("x", dto.getX());
//            data.put("y", dto.getY());
//            data.put("width", dto.getWidth());
//            data.put("height", dto.getHeight());
//            if (dto.getData() != null) {
//                data.putAll(dto.getData());
//            }
//            dataList.add(data);
//        }
//
//        TaskResultDTO result = new TaskResultDTO();
//        result.setTaskId(taskId);
//        result.setTaskType("detection");
//        result.setData(Collections.singletonMap("results", dataList));
//        return result;
//    }
//
//    // 将 TaskResultDTO 转换为 DetectObjectDTO 列表
//    public static List<DetectObjectDTO> toDetectObjects(TaskResultDTO taskResult) {
//        List<DetectObjectDTO> list = new ArrayList<>();
//        Object raw = taskResult.getData().get("results");
//        if (raw instanceof List<?>) {
//            for (Object obj : (List<?>) raw) {
//                if (obj instanceof Map<?, ?>) {
//                    Map<String, Object> data = (Map<String, Object>) obj;
//                    DetectObjectDTO dto = new DetectObjectDTO()
//                            .setClassName((String) data.get("className"))
//                            .setProbability(asDouble(data.get("probability")))
//                            .setX(asDouble(data.get("x")))
//                            .setY(asDouble(data.get("y")))
//                            .setWidth(asDouble(data.get("width")))
//                            .setHeight(asDouble(data.get("height")))
//                            .setData(data);
//                    list.add(dto);
//                }
//            }
//        }
//        return list;
//    }
//
//    private static Double asDouble(Object value) {
//        if (value instanceof Number) {
//            return ((Number) value).doubleValue();
//        } else if (value instanceof String) {
//            try {
//                return Double.parseDouble((String) value);
//            } catch (NumberFormatException e) {
//                return null;
//            }
//        }
//        return null;
//    }
//}
