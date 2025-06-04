package com.xrervip.super_ai_service.controller;

import com.xrervip.super_ai_service.constant.ApiRouterConsts;
import com.xrervip.super_ai_service.constant.ResponseResult;
import com.xrervip.super_ai_service.entity.ResultSetDTO;
import com.xrervip.super_ai_service.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import com.xrervip.super_ai_service.entity.DetectObjectDTO;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Author: frzquantum@gmail.com
 * DateTime: 2023-02-25 11:15
 */
@Tag(name = "ImagePredictController", description = "前台门户-图像推理模块")
@RestController // @RestController = @Controller + @ResponseBody (@ResponseBody: 标记该控制器中所有 @RequestMapping 处理方法的返回值将直接写入 HTTP 响应体中（而不是解析为视图名）。这对于构建 RESTful API 非常有用，因为你通常希望返回 JSON、XML 或其他数据格式，而不是 HTML 页面。）
@RequestMapping(ApiRouterConsts.API_FRONT_IMAGE_URL_PREFIX)
@Slf4j
@AllArgsConstructor
public class ImagePredictController {

    private final ImageService imageService;


    @PostMapping(path= "/Ocr",consumes = "multipart/form-data")
    @Operation(summary = "识别图片中的文字并返回OCR任务ID")
//    @Tag(name = "OCR", description = "识别图片中的文字并返回OCR任务ID")
    public ResponseResult<String>  ocr(HttpServletRequest request
                                                        , @Parameter(
                                                                description = "Image file to be uploaded and ocr.",
                                                                content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)
                                                        )@NotNull @RequestPart MultipartFile file) throws  IOException {
//        HttpSession session =  request.getSession();
//        String sessionID = session.getId();

        // 1. 为本次 OCR 请求生成一个唯一的任务 ID
        String taskID = UUID.randomUUID().toString();
        String taskType = "ocr";

        if (isZipFile(file)) {
            // 如果是 ZIP 文件，解压并处理每个图像
            imageService.sendOcrJobFromZip(taskID, taskType, file.getInputStream());
        } else {
            // 如果是单张图片，直接处理
            imageService.sendOcrJob(taskID, taskType, file.getOriginalFilename(), file.getInputStream());
        }
        return ResponseResult.success(taskID);
    }

    private boolean isZipFile(MultipartFile file) {
        return file.getOriginalFilename().toLowerCase().endsWith(".zip");
    }

    @GetMapping(path= "/result")
    @Operation(summary = "通过任务ID获取结果")
//    @Tag(name = "result", description = "获取对应的结果")
    public ResponseResult<List<ResultSetDTO>>  result(@Parameter(
                                                            description = "taskID.",allowEmptyValue = false
                                                        ) String taskID) throws  IOException {
        List<ResultSetDTO> list = imageService.getOcrResult(taskID);
        return ResponseResult.success(list);
    }


}
