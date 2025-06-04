package com.xrervip.super_ai_service.entity;

import java.util.Set;

public class ResultSetDTO {
    private String imageName; // 图像名称
    private Set<DetectObjectDTO> ocrResults; // 对应的 OCR 结果

    // 构造函数
    public ResultSetDTO(String imageName, Set<DetectObjectDTO> ocrResults) {
        this.imageName = imageName;
        this.ocrResults = ocrResults;
    }

    // Getters 和 Setters
    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Set<DetectObjectDTO> getOcrResults() {
        return ocrResults;
    }

    public void setOcrResults(Set<DetectObjectDTO> ocrResults) {
        this.ocrResults = ocrResults;
    }
}
