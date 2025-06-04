package com.xrervip.super_ai_service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Author: frzquantum@gmail.com
 * DateTime: 2023-02-26 20:07
 */
@Data
@AllArgsConstructor
public class LinearDTO implements Serializable {
    List<Double> array;

    @Serial
    private static final long serialVersionUID = -114514;


}
