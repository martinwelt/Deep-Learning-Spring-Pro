package com.xrervip.super_ai_service.service.impl;

import com.xrervip.super_ai_service.dao.TrainJobMapper;
import com.xrervip.super_ai_service.entity.TrainJob;
import com.xrervip.super_ai_service.service.TrainService;
import com.xrervip.super_ai_service.utils.TrainStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Service
@Slf4j
public class TrainServiceImpl implements TrainService {

    private Map<String, TrainJob> jobs = new HashMap<>();

    private final TrainJobMapper mapper;
    @Override
    public TrainJob create(String modelName) {
        TrainJob trainJob = new TrainJob();
        trainJob.setTrainModelName(modelName);
        trainJob.setStatus(TrainStatus.WAIT_DATA);
        jobs.put(modelName,trainJob);
        mapper.insert(trainJob.toTrainJobPO());
        return trainJob;
    }

    @Override
    public void ready(String modelName) {

    }

    @Override
    public void train(String modelName) {

    }

    @Override
    public void cancel(String modelName) {

    }
}
