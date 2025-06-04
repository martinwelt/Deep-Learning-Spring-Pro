package com.xrervip.super_ai_service.dao;
import com.xrervip.super_ai_service.entity.ModelDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:模型mapper
 *
 * @Author: frzquantum@gmail.com
 * DateTime: 2023-02-28 19:59
 */
@Mapper
public interface ModelMapper {
    int insert(ModelDO record);

    ModelDO selectByModelName(@Param("modelName") String modelName);

    List<String> selectAllModelNames();
}
