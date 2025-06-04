package com.xrervip.super_ai_service.entity;

import com.xrervip.super_ai_service.utils.TrainStatus;
import lombok.Data;

@Data
public class TrainJobPO {

    private String trainModelName;
    private String status;

    public TrainJob toTrainJob(){
        TrainJob trainJob = new TrainJob();
        trainJob.setTrainModelName(this.trainModelName);
        trainJob.setStatus(TrainStatus.valueOf(this.status));
        return trainJob;
    }
}
