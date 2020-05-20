package com.eddy.health.aide.exception;

/**
 * @Author PuaChen
 * @Create 2018-11-11 11:11
 */
public class InvalidParamsException extends RuntimeException {

    private int code = 401;

    public InvalidParamsException(String message, int code) {
        super(message);
        this.code = code;
    }

    public InvalidParamsException(String message) {
        super(message);
    }

    public int getCode() {
        return code;
    }
}
