package com.xrervip.super_ai_service.dao;

import com.xrervip.super_ai_service.entity.TrainJobPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * TrainJob mapper
 */
@Mapper
public interface TrainJobMapper {

    int insert(TrainJobPO record);

    int update(TrainJobPO record);

    TrainJobPO selectByModelName(@Param("trainModelName") String modelName);

}
