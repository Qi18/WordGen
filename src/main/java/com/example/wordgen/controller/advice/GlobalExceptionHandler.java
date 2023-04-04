package com.example.wordgen.controller.advice;

import cn.hutool.core.convert.ConvertException;
import cn.hutool.http.HttpException;
import com.example.wordgen.exception.ServiceException;
import com.example.wordgen.model.response.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author: rich
 * @date: 2023/4/4 13:52
 * @description:
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = ServiceException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse serviceExceptionHandler(HttpServletRequest req, ServiceException e) {
        logger.error("发生业务异常！原因是：{}", e.getMessage());
        return ApiResponse.error(e.getMessage());
    }

    @ExceptionHandler(value = IOException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse ioExceptionHandler(HttpServletRequest req, IOException e) {
        logger.error("io failed：{}", e.getMessage());
        return ApiResponse.error(e.getMessage());
    }

    @ExceptionHandler(value = HttpException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse httpExceptionHandler(HttpServletRequest req, HttpException e) {
        logger.error("ask chatgpt failed：{}", e.getMessage());
        return ApiResponse.error(e.getMessage());
    }

    @ExceptionHandler(value = ConvertException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse convertExceptionHandler(HttpServletRequest req, ConvertException e) {
        logger.error("json change failed：{}", e.getMessage());
        return ApiResponse.error(e.getMessage());
    }

//    @ExceptionHandler(Exception.class)
//    @ResponseBody
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ApiResponseV2<String> defaultHandler(Exception ex) {
//        logger.error("Exception!", ex);
//        return ApiResponseV2.fail(ex.getMessage());
//    }


}
