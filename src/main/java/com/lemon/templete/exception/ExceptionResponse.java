package com.lemon.templete.exception;

import com.lemon.templete.enums.ErrorInfoEnum;

/**
 * 描述：异常错误响应实体类
 * @author 汤中流
 * @date 2019/08/28
 */
public class ExceptionResponse {

    // 错误码
    private String code;
    // 错误信息
    private String message;

    private ExceptionResponse() {

    }

    public ExceptionResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ExceptionResponse getInstance(ErrorInfoEnum errorInfoEnum) {
        return new ExceptionResponse(errorInfoEnum.getCode(), errorInfoEnum.getMessage());
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
