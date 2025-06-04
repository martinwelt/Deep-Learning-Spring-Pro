package com.xrervip.super_ai_service.entity;

import com.xrervip.super_ai_service.utils.TrainStatus;
import lombok.Data;

@Data
public class TrainJob {

    private String trainModelName;
    private TrainStatus status;

    public TrainJobPO toTrainJobPO(){
        TrainJobPO trainJobPo = new TrainJobPO();
        trainJobPo.setStatus(status.name());
        trainJobPo.setTrainModelName(trainModelName);
        return trainJobPo;
    }

}
