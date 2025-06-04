package com.xrervip.super_ai_service.controller;

import com.xrervip.super_ai_service.constant.ApiRouterConsts;
import com.xrervip.super_ai_service.constant.ResponseResult;
import com.xrervip.super_ai_service.entity.ModelInfoDTO;
import com.xrervip.super_ai_service.service.ModelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Author: frzquantum@gmail.com
 * DateTime: 2023-03-03 20:34
 */
@Tag(name = "ModelController", description = "前台门户-模型模块")
@Slf4j
@RestController
@RequestMapping(ApiRouterConsts.API_FRONT_MODEL_URL_PREFIX)
@RequiredArgsConstructor
@Validated
public class ModelController {


    private final ModelService modelService;

//    @PostMapping(path = "/add",consumes = "multipart/form-data")
//    @Tag(name = "add model", description = "add model to the server ")
//    public ResponseResult<String> add(@RequestParam(value = "modelDto") String contentParam,
//                                      @Parameter(
//                                              description = "Files to be uploaded",
//                                              content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)
//                                      )
//                                      @RequestPart  (value = "files", required = true) MultipartFile file) throws IOException {
//        Gson gson = new Gson();
//        @ValidModel ModelDto modelDto = gson.fromJson(contentParam, ModelDto.class);
//
//        log.info("Add or update model : {}",modelDto.getModelName());
//        byte [] byteArr=file.getBytes();
//        modelService.addModel(modelDto,byteArr);
//        return ResponseResult.success("add success");
//    }

    @PostMapping(path = "/add",consumes = {"multipart/form-data"})
    @Operation(summary = "上传模型")
//    @Tag(name = "add model", description = "上传模型")
    public ResponseResult<String> add(ModelInfoDTO modelInfoDto,
                                      @Parameter(
                                              description = "Files to be uploaded",
                                              content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)
                                      )
                                      @NotNull @RequestPart  (value = "files", required = true) MultipartFile file) throws IOException {
        log.info("Add model : {}", modelInfoDto.getModelName());
        byte [] byteArr=file.getBytes();
        modelService.addModel(modelInfoDto,byteArr);
        return ResponseResult.success("add success");
    }

    @GetMapping("/get")
    @Operation(summary = "下载模型")
//    @Tag(name = "get model file", description = "下载模型")
    public void get(String modelName,HttpServletResponse response) throws IOException{

        byte[] model = modelService.getModelFile(modelName);
        response.reset();
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition",
                "attachment;filename=modelFile_" + modelName + ".zip");

        response.getOutputStream().write(model, 0, model.length);

    }

    @GetMapping("/model-names")
    @Operation(summary = "获取所有可用模型名")
//    @Tag(name = "get model names", description = "获取所有可用模型名")
    public ResponseResult<List<String>> getAllModelNames() {
        List<String> names = modelService.getAllModelNames();
        return ResponseResult.success(names);
    }
    @PostMapping(path = "/load")
    @Operation(summary = "加载模型到服务器")
//    @Tag(name = "load model", description = "向服务器加载模型")
    public ResponseResult<String> add(String modelName) throws IOException {
        modelService.loadModel(modelName);
        return ResponseResult.success("load successful !");
    }
}
