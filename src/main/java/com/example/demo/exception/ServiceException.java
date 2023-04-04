package com.example.demo.exception;

import java.io.Serializable;

/**
 * @author: rich
 * @date: 2023/4/4 13:59
 * @description:
 */
public class ServiceException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 1L;

    public ServiceException(String message) {
        super(message);
    }
}
