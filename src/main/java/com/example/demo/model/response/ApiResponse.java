package com.example.demo.model.response;

import com.google.gson.Gson;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: rich
 * @date: 2023/4/4 12:01
 * @description:
 */

@Slf4j
@Data
public class ApiResponse {
    Object result;
    boolean success;
    ApiError error;

    private static final transient Gson gson = new Gson();

    public ApiResponse(Object result, boolean success, ApiError error) {
        this.result = result;
        this.success = success;
        this.error = error;
    }

    public static ApiResponse error(String message) {
        ApiResponse resp = new ApiResponse(null, false, new ApiError(message, null));
        if (log.isInfoEnabled()) {
            log.info("API Response: \n{}", gson.toJson(resp));
        }
        return resp;
    }

    public static ApiResponse success(Object result) {
        ApiResponse resp = new ApiResponse(result, true, null);
        if (log.isInfoEnabled()) {
            log.info("API Response: \n{}", gson.toJson(resp));
        }
        return resp;
    }
}
