package com.xrervip.super_ai_service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Author: frzquantum@gmail.com
 * DateTime: 2023-02-26 20:09
 */

@Data
@AllArgsConstructor
public class MatrixDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -1919810;

    double [][]Data;



}
