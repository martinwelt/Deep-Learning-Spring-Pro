package com.xrervip.super_ai_service.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:api的返回状态
 *
 * @Author: frzquantum@gmail.com
 * DateTime: 2022-12-15 18:20
 */
@Getter
@AllArgsConstructor
public enum ResponseStatus  {
    SUCCESS("200", "success"),
    FAIL("500", "failed"),
    NOT_FOUND("404","not found"),
    BAD_REQUEST("400","bad requests"),

    HTTP_STATUS_200("200", "ok"),
    HTTP_STATUS_400("400", "bad requests"),
    HTTP_STATUS_401("401", "no authentication"),
    HTTP_STATUS_403("403", "no authorities"),
    HTTP_STATUS_404("404", "Not Found"),
    HTTP_STATUS_500("500", "server error");

    public static final List<ResponseStatus > HTTP_STATUS_ALL = Collections.unmodifiableList(
            Arrays.asList(HTTP_STATUS_200, HTTP_STATUS_400, HTTP_STATUS_401, HTTP_STATUS_403,HTTP_STATUS_404, HTTP_STATUS_500
            ));

    /**
     * response code
     */
    private final String responseCode;

    /**
     * description.
     */
    private final String description;

}
