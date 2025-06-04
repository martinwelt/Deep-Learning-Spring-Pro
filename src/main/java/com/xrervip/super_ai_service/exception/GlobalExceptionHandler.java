package com.xrervip.super_ai_service.exception;

import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.xrervip.super_ai_service.constant.ResponseStatus;
import com.xrervip.super_ai_service.constant.ResponseResult;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Author: frzquantum@gmail.com
 * DateTime: 2023-01-27 14:38
 */

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * handle business exception.
     *
     * @param modelException business exception
     * @return ResponseResult
     */
    @ResponseBody
    @ExceptionHandler(ModelException.class)
    public ResponseResult<ModelException> processModelException(ModelException modelException) {
        log.error(modelException.getLocalizedMessage());
        return ResponseResult.fail(null, modelException.getLocalizedMessage()==null
                ? ResponseStatus.HTTP_STATUS_500.getDescription()
                : modelException.getLocalizedMessage());
    }

    @ResponseBody
    @ExceptionHandler(BindException.class)
    public ResponseResult<BindException> processModelException(BindException bindException) {
        log.error(bindException.getLocalizedMessage());
        return ResponseResult.badRequest(null, "Parameter could not be NULL");
    }

    @ResponseBody
    @ExceptionHandler(ValidationException.class)
    public ResponseResult<ValidationException> processModelException(ValidationException bindException) {
        log.error(bindException.getLocalizedMessage());
        return ResponseResult.badRequest(null, "Bad parameters");
    }

    @ResponseBody
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseResult<ResourceNotFoundException> processModelException(ResourceNotFoundException notExistException) {
        log.error(notExistException.getLocalizedMessage());
        return ResponseResult.notFound(null, "Target resource not exist.");
    }

    /**
     * handle other exception.
     *
     * @param exception exception
     * @return ResponseResult
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseResult<Exception> processException(Exception exception) {
        log.error(exception.getLocalizedMessage(), exception);
        return ResponseResult.fail(null, ResponseStatus.HTTP_STATUS_500.getDescription());
    }

}
