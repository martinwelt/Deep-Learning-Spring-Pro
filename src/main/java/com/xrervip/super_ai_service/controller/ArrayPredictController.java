package com.xrervip.super_ai_service.controller;

import com.xrervip.super_ai_service.constant.ResponseResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 * Description: todo
 *
 * @Author: frzquantum@gmail.com
 * DateTime: 2023-02-27 21:08
 */

@Tag(name = "hello", description = "get demo hello world")
@Slf4j
@RestController
@RequestMapping("/array")
public class ArrayPredictController {

    @GetMapping("hello")
    @Operation(summary = "打个招呼先")
    public ResponseResult<String> hello(){
        log.info("hello");
        return ResponseResult.success("hello");
    }

}
